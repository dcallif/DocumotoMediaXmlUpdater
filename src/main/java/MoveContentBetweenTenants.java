import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MoveContentBetweenTenants {
    private String newTenantKey;
    private String newSupplierKey;
    private String newAttachmentUser;

    private static final String RESOURCE_PATH = "src/main/resources/";
    private static final String XML_PATH = "src/main/xmls/";

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
    private void updatePartSupplierKeys(String xmlLocation, String newXmlLocation) {
        ValidateXml validator = new ValidateXml();

        UpdateXml partUpdater = new UpdateXml();
        // Convert XML file to Page Object
        Page pg = partUpdater.xmlFileToPageObject(xmlLocation);
        pg.setXmlns(PAGE_XMLNS);
        pg.setTenantKey(newTenantKey);
        partUpdater.updatePartSuppliers(pg, newSupplierKey);

        if (partUpdater.writeToFileWithXmlTransformer(pg, newXmlLocation)) {
            System.out.println("Wrote updated XML: " + newXmlLocation.substring(newXmlLocation.lastIndexOf(File.separator) + 1));
        } else {
            System.out.println("Failed to write XML:" + newXmlLocation.lastIndexOf(File.separator) + 1);
        }
    }

    private void updatePartsFromPageXmlsInDirectory(String xmlsLocation, String newXmlsLocation) {
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
                updatePartSupplierKeys(s, newXmlsLocation + newXmlFilename);
            }
        }
    }

    // Deletes every Part from XML
    private void deletePartsFromMediaXml(String xmlLocation, String newXmlLocation) {
        ValidateXml validator = new ValidateXml();
        UpdateXml partDeletor = new UpdateXml();
        // Convert XML file to Media Object
        Media m = partDeletor.xmlFileToMediaObject(xmlLocation);
        m.setXmlns(MEDIA_XMLNS);
        m.setTenantKey(newTenantKey);
        partDeletor.removePartsFromMedia(m);

        if (partDeletor.writeToFileWithXmlTransformer(m, newXmlLocation)) {
            System.out.println("Wrote updated XML: " + newXmlLocation.substring(newXmlLocation.lastIndexOf(File.separator) + 1));
        } else {
            System.out.println("Failed to write XML");
        }
    }

    // Deletes every Part from every Media XML in directory
    private void deletePartsFromMediaXmlsInDirectory(String xmlsLocation, String newXmlsLocation) {
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
                deletePartsFromMediaXml(s, newXmlsLocation + newXmlFilename);
            }
        }
    }

    public static void main(String[] args) {
        MoveContentBetweenTenants test = new MoveContentBetweenTenants("D1", "DD11", "test@documototesting.com");
        // Delete Parts from 1 Book
        //test.deletePartsFromMediaXml(RESOURCE_PATH + MEDIA_XSD_VERSION, XML_PATH + "large-book.xml", XML_PATH + "out.xml");
        // Delete Parts from directory of Media XMLs
        test.deletePartsFromMediaXmlsInDirectory(XML_PATH, XML_PATH + "updated/");

        // Update Part Suppliers for 1 Page
        //test.updatePartSupplierKeys("DOCUMOTO-TEST", XML_PATH + "page.xml", XML_PATH + "updated/page_editted.xml");
        // Update Part Suppliers for directory of Page XMLs
        test.updatePartsFromPageXmlsInDirectory(XML_PATH, XML_PATH + "updated/");
    }
}
