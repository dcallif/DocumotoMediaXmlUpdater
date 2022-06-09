import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MoveContentBetweenTenants {
    private String newTenantKey;
    private String newSupplierKey;
    private String newAttachmentUser;

    private static final String RESOURCE_PATH = "src/main/resources/";

    private static final String MEDIA_XSD_VERSION = "documoto_media1.4.xsd";
    private static final String MEDIA_XMLNS = "http://digabit.com/documoto/media/1.4";
    private static final String PAGE_XSD_VERSION = "documoto_partslist1.6.xsd";
    private static final String PAGE_XMLNS = "http://digabit.com/documoto/partslist/1.6";

    private MoveContentBetweenTenants(String tenantKey, String supplierKey, String attachmentUser) {
        this.newTenantKey = tenantKey;
        this.newSupplierKey = supplierKey;
        this.newAttachmentUser = attachmentUser;
    }

    // Updates Part SupplierKeys
    private void updatePartSuppliersAndAttachmentUsers(String xmlLocation, String newXmlLocation) {
        UpdateXml partUpdater = new UpdateXml();
        // Convert XML file to Page Object
        Page pg = partUpdater.xmlFileToPageObject(xmlLocation);
        pg.setXmlns(PAGE_XMLNS);
        pg.setTenantKey(newTenantKey);

        partUpdater.updatePartSuppliers(pg, newSupplierKey, newTenantKey);
        partUpdater.updateAttachmentUser(pg, newAttachmentUser);

        if (partUpdater.writeToFileWithXmlTransformer(pg, newXmlLocation)) {
            System.out.println("Wrote updated XML: " + newXmlLocation.substring(newXmlLocation.lastIndexOf(File.separator) + 1));
        } else {
            System.out.println("Failed to write XML:" + newXmlLocation.lastIndexOf(File.separator) + 1);
        }
    }

    private void updatePartsAndAttachmentsFromPageXmlsInDirectory(String xmlsLocation, String newXmlsLocation) {
        List<String> xmlFiles = new ArrayList<>();

        File folder = new File(xmlsLocation);
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            // Only look at regular files
            if (!fileEntry.isDirectory() && !fileEntry.isHidden()) {
                // Check if XML is valid
                ValidateXml validateXml = new ValidateXml();
                if (validateXml.isXmlValid(RESOURCE_PATH + PAGE_XSD_VERSION, fileEntry.getAbsolutePath())) {
                    xmlFiles.add(fileEntry.getAbsolutePath());
                } else {
                    System.out.println(String.format("File: %s, is not a valid Page XML...ignoring for Page Update.", fileEntry.getName()));
                }
            }
        }

        if (xmlFiles.size() > 0) {
            for (String s : xmlFiles) {
                String newXmlFilename = s.substring(s.lastIndexOf("/") + 1);
                newXmlFilename = newXmlFilename.substring(0, newXmlFilename.lastIndexOf(".")) + "_editted.xml";
                updatePartSuppliersAndAttachmentUsers(s, newXmlsLocation + newXmlFilename);
            }
        }
    }

    // Deletes every Part from XML
    private void deletePartsAndUpdateAttachmentsFromMediaXml(String xmlLocation, String newXmlLocation) {
        UpdateXml partDeletor = new UpdateXml();
        // Convert XML file to Media Object
        Media m = partDeletor.xmlFileToMediaObject(xmlLocation);
        m.setXmlns(MEDIA_XMLNS);
        m.setTenantKey(newTenantKey);

        partDeletor.removePartsFromMedia(m, newTenantKey);
        partDeletor.updateAttachmentUser(m, newAttachmentUser);

        if (partDeletor.writeToFileWithXmlTransformer(m, newXmlLocation)) {
            System.out.println("Wrote updated XML: " + newXmlLocation.substring(newXmlLocation.lastIndexOf(File.separator) + 1));
        } else {
            System.out.println("Failed to write XML");
        }
    }

    // Updates include: TenantKeys/SupplierKeys/Usernames/HashKeys
    private void updateDirectoryOfMdzs(String mdzsLocation, String newSaveLocation) {
        File folder = new File(mdzsLocation);
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            // Only look at regular files and .mdz extension
            if (!fileEntry.isDirectory() && !fileEntry.isHidden() && fileEntry.getName().substring(fileEntry.getName().lastIndexOf(".") + 1).equalsIgnoreCase("mdz")) {
                System.out.println("Working on MDZ: " + fileEntry.getName());

                String mdzFolder = fileEntry.getName().substring(0, fileEntry.getName().lastIndexOf("."));
                // Unzip MDZ file
                try {
                    ZipUtil.unzip(fileEntry.getAbsolutePath(), newSaveLocation + mdzFolder);
                } catch (IOException e) {
                    System.out.println(String.format("Failed to unzip file: %s, reason: %s", fileEntry.getName(), e.getMessage()));
                }

                // Look for all PLZs to unzip
                for (final File mdzFile : Objects.requireNonNull(new File(newSaveLocation + mdzFolder).listFiles())) {
                    if (!mdzFile.isDirectory() && !mdzFile.isHidden() && mdzFile.getName().substring(mdzFile.getName().lastIndexOf(".") + 1).equalsIgnoreCase("plz")) {
                        System.out.println("Working on PLZ: " + mdzFile.getName());

                        String plzFolder = mdzFile.getName().substring(0, mdzFile.getName().lastIndexOf("."));
                        // Unzip PLZ file
                        try {
                            // Deletes PLZ once unzipped
                            mdzFile.deleteOnExit();
                            ZipUtil.unzip(mdzFile.getAbsolutePath(), newSaveLocation + mdzFolder + File.separator + plzFolder + File.separator);

                            // Look for Page XML to update
                            for (final File plzFile : Objects.requireNonNull(new File(newSaveLocation + mdzFolder + File.separator + plzFolder + File.separator).listFiles())) {
                                if (!plzFile.isDirectory() && !plzFile.isHidden() && plzFile.getName().substring(plzFile.getName().lastIndexOf(".") + 1).equalsIgnoreCase("xml")) {
                                    // Update Page XML
                                    updatePartSuppliersAndAttachmentUsers(plzFile.getAbsolutePath(), plzFile.getAbsolutePath());
                                }
                            }

                        } catch (IOException e) {
                            System.out.println(String.format("Failed to unzip file: %s, reason: %s", mdzFile.getName(), e.getMessage()));
                        }
                    } else if (!mdzFile.isDirectory() && !mdzFile.isHidden() && mdzFile.getName().substring(mdzFile.getName().lastIndexOf(".") + 1).equalsIgnoreCase("xml")) {
                        // Update Media XML
                        deletePartsAndUpdateAttachmentsFromMediaXml(mdzFile.getAbsolutePath(), mdzFile.getAbsolutePath());
                    }
                }
            }
        }
        System.out.println("MDZ Update Complete!");
    }

    // Deletes every Part from every Media XML in directory
    private void deletePartsAndUpdateAttachmentsFromMediaXmlsInDirectory(String xmlsLocation, String newXmlsLocation) {
        List<String> xmlFiles = new ArrayList<>();

        File folder = new File(xmlsLocation);
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            // Only look at regular files
            if (!fileEntry.isDirectory() && !fileEntry.isHidden()) {
                // Check if XML is valid
                ValidateXml validateXml = new ValidateXml();
                if (validateXml.isXmlValid(RESOURCE_PATH + MEDIA_XSD_VERSION, fileEntry.getAbsolutePath())) {
                    xmlFiles.add(fileEntry.getAbsolutePath());
                } else {
                    System.out.println(String.format("File: %s, is not a valid Media XML...ignoring for Media Update.", fileEntry.getName()));
                }
            }
        }

        if (xmlFiles.size() > 0) {
            for (String s : xmlFiles) {
                String newXmlFilename = s.substring(s.lastIndexOf("/") + 1);
                newXmlFilename = newXmlFilename.substring(0, newXmlFilename.lastIndexOf(".")) + "_editted.xml";
                deletePartsAndUpdateAttachmentsFromMediaXml(s, newXmlsLocation + newXmlFilename);
            }
        }
    }

    public static void main(String[] args) {
        String XML_PATH = "src/main/xmls/";
        String MDZ_PATH = "src/main/mdzs/";

        MoveContentBetweenTenants test = new MoveContentBetweenTenants("D1", "DD11", "test@documototesting.com");
        // Delete Parts from 1 Book
        //test.deletePartsAndUpdateAttachmentsFromMediaXml(XML_PATH + "large-book.xml", XML_PATH + "out.xml");
        // Delete Parts from directory of Media XMLs
        //test.deletePartsAndUpdateAttachmentsFromMediaXmlsInDirectory(XML_PATH, XML_PATH + "updated/");

        // Update Part Suppliers for 1 Page
        //test.updatePartSuppliersAndAttachmentUsers(XML_PATH + "page.xml", XML_PATH + "updated/page_editted.xml");
        // Update Part Suppliers for directory of Page XMLs
        //test.updatePartsAndAttachmentsFromPageXmlsInDirectory(XML_PATH, XML_PATH + "updated/");

        // Update ALL MDZs in directory
        test.updateDirectoryOfMdzs(MDZ_PATH, MDZ_PATH + "updated/");
    }
}
