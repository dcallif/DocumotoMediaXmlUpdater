import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.bind.Binder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

public class UpdateXml {
    // Writes sent in XML object to XML file pretty printed
    <Type> boolean writeToFileWithXmlTransformer(Type instance, String fullFileNamePath) {
        boolean isSaved = false;
        JAXBContext jaxBContent;
        Marshaller marshaller;
        StringWriter stringWriter = new StringWriter();

        try {
            // Create new marshaller and set pretty print formatting
            jaxBContent = JAXBContext.newInstance(instance.getClass());
            marshaller = jaxBContent.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(instance, stringWriter);

            // Transform Object to File
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new StreamSource(new StringReader(stringWriter.toString()))
                ,new StreamResult(new File(fullFileNamePath)));

            isSaved = true;
        } catch(JAXBException jaxBException) {
            System.out.println("JAXBException happened!");
            jaxBException.printStackTrace();
        } catch(Exception exception) {
            System.out.println("Exception happened!");
            exception.printStackTrace();
        }
        return isSaved;
    }

    // Removes ALL Parts from EVERY Page under current Chapter
    private static void removePartsRecursively(Chapter c, String tenantKey) {
        // Check if Chapter has Pages
        if (c.getPages() != null) {
            for (Page pg : c.getPages()) {
                // Update Page HashKey
                pg.setHashKey(tenantKey + "-" + pg.getHashKey());
                pg.getParts().removeAll(pg.getParts());
            }
        }

        // Check if Chapter has child Chapters
        if (c.getChapters() != null) {
            for (Chapter c1 : c.getChapters()) {
                removePartsRecursively(c1, tenantKey);
            }
        }
    }

    // Removes ALL Parts from EVERY Page in Media
    void removePartsFromMedia(Media m, String tenantKey) {
        // Check for root-level Chapters
        for (Chapter c : m.getChapters()) {
            removePartsRecursively(c, tenantKey);
        }

        // Check for Book-level Pages
        if (m.getPages() != null) {
            for (Page pg : m.getPages()) {
                // Update Page HashKey
                pg.setHashKey(tenantKey + "-" + pg.getHashKey());
                pg.getParts().removeAll(pg.getParts());
            }
        }
    }

    // Updates ALL Part SupplierKeys
    private static void updatePartSuppliersRecursively(Chapter c, String supplierKey) {
        // Check if Chapter has Pages
        if (c.getPages() != null) {
            for (Page pg : c.getPages()) {
                for (Part prt : pg.getParts()) {
                    prt.setSupplierKey(supplierKey);
                }
            }
        }

        // Check if Chapter has child Chapters
        if (c.getChapters() != null) {
            for (Chapter c1: c.getChapters()) {
                updatePartSuppliersRecursively(c1, supplierKey);
            }
        }
    }

    // Update Part Supplier Keys
    private void updatePartSuppliers(Media m, String supplierKey, String tenantKey) {
        // Check for root-level Chapters
        for (Chapter c : m.getChapters()) {
            updatePartSuppliersRecursively(c, supplierKey);
        }

        // Check for Book-level Pages
        if (m.getPages() != null) {
            for (Page pg : m.getPages()) {
                // Update Page HashKey
                pg.setHashKey(tenantKey + "-" + pg.getHashKey());
                for (Part prt : pg.getParts()) {
                    prt.setSupplierKey(supplierKey);
                }
            }
        }
    }

    // Updates Part Supplier Keys
    void updatePartSuppliers(Page pg, String supplierKey, String tenantKey) {
        // Update Page HashKey
        pg.setHashKey(tenantKey + "-" + pg.getHashKey());

        // Check for Parts
        for (Part prt : pg.getParts()) {
            prt.setSupplierKey(supplierKey);
        }
    }

    // Updates ALL Attachment userNames
    private void updateAttachmentUserRecursively(Chapter c, String userName) {
        for (Chapter c1 : c.getChapters()) {
            updateAttachmentUserRecursively(c1, userName);
        }

        if (c.getPages() != null) {
            for (Page pg : c.getPages()) {
                updateAttachmentUser(pg, userName);
            }
        }
    }

    // Updates Attachment username
    void updateAttachmentUser(Page p, String userName) {
        if (p.getAttachment() != null) {
            for (Attachment a : p.getAttachment()) {
                a.setUserName(userName);
            }
        }
    }

    // Updates Attachment userNames
    void updateAttachmentUser(Media m, String userName) {
        // Check for Media-level Attachments
        if (m.getAttachments() != null) {
            for (Attachment a : m.getAttachments()) {
                a.setUserName(userName);
            }
        }

        // Check for Book-level Pages
        if (m.getPages() != null) {
            for (Page pg : m.getPages()) {
                updateAttachmentUser(pg, userName);
            }
        }

        // Update everything under Chapters
        for (Chapter c : m.getChapters()) {
            updateAttachmentUserRecursively(c, userName);
        }
    }

    // Converts XML file to a Media Object
    Media xmlFileToMediaObject(String fullFileNamePath) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;

        // Convert XML File to Media Object
        try {
            docBuilder = dbf.newDocumentBuilder();
            Document document = docBuilder.parse(fullFileNamePath);

            JAXBContext jc = JAXBContext.newInstance(Media.class);
            Binder<Node> binder = jc.createBinder();
            // Pretty print output
            binder.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            Node xmlNode = document.getDocumentElement();

            return (Media) binder.updateJAXB(xmlNode);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Converts XML file to a Page Object
    Page xmlFileToPageObject(String fullFileNamePath) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;

        // Convert XML File to Page Object
        try {
            docBuilder = dbf.newDocumentBuilder();
            Document document = docBuilder.parse(fullFileNamePath);

            JAXBContext jc = JAXBContext.newInstance(Page.class);
            Binder<Node> binder = jc.createBinder();
            // Pretty print output
            binder.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            Node xmlNode = document.getDocumentElement();

            return (Page) binder.updateJAXB(xmlNode);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String XML_PATH = "src/main/xmls/";
        
        UpdateXml test = new UpdateXml();
        Media m = test.xmlFileToMediaObject(XML_PATH + "test-book.xml");
        m.setXmlns("http://digabit.com/documoto/media/1.4");
        test.removePartsFromMedia(m, "CALLIFTEST");
        test.updatePartSuppliers(m, "CALLIFTEST", "CALLIFTEST");

        if (test.writeToFileWithXmlTransformer(m, XML_PATH + "out.xml")) {
            System.out.println("Wrote XML");
        } else {
            System.out.println("Failed to write XML");
        }
    }
}