import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.bind.Binder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringWriter;
import java.io.StringReader;
import java.io.File;

public class UpdateXml {
    private static final String XML_PATH = "src/main/xmls/";
    private static final String MEDIA_XMLNS = "http://digabit.com/documoto/media/1.4";

    /**
     * Writes sent in XML object to XML file pretty printed
     * @param instance
     * @param fullFileNamePath
     * @param <Type>
     * @return
     * @throws JAXBException
     */
    public <Type> boolean writeToFileWithXmlTransformer(Type instance, String fullFileNamePath) {
        boolean isSaved = false;
        JAXBContext jaxBContent = null;
        Marshaller marshaller = null;
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
    static void removePartsRecursively(Chapter c) {
        // Check if Chapter has Pages
        if (c.getPages() != null) {
            for (Page pg : c.getPages()) {
                pg.getParts().removeAll(pg.getParts());
            }
        }

        // Check if Chapter has child Chapters
        if (c.getChapters() == null) {
            return;
        } else {
            for (Chapter c1 : c.getChapters()) {
                removePartsRecursively(c1);
            }
        }
    }

    // Removes ALL Parts from EVERY Page in Media
    void removePartsFromMedia(Media m) {
        // Check for root-level Chapters
        for (Chapter c : m.getChapters()) {
            removePartsRecursively(c);
        }

        // Check for Book-level Pages
        if (m.getPages() != null) {
            for (Page pg : m.getPages()) {
                pg.getParts().removeAll(pg.getParts());
            }
        }
    }

    // Updates ALL Part SupplierKeys
    static void updatePartSuppliersRecursively(Chapter c, String supplierKey) {
        // Check if Chapter has Pages
        if (c.getPages() != null) {
            for (Page pg : c.getPages()) {
                for (Part prt : pg.getParts()) {
                    prt.setSupplierKey(supplierKey);
                }
            }
        }

        // Check if Chapter has child Chapters
        if (c.getChapters() == null) {
            return;
        } else {
            for (Chapter c1: c.getChapters()) {
                updatePartSuppliersRecursively(c1, supplierKey);
            }
        }
    }

    // Update Part Supplier Keys
    void updatePartSuppliers(Media m, String supplierKey) {
        // Check for root-level Chapters
        for (Chapter c : m.getChapters()) {
            updatePartSuppliersRecursively(c, supplierKey);
        }

        // Check for Book-level Pages
        if (m.getPages() != null) {
            for (Page pg : m.getPages()) {
                for (Part prt : pg.getParts()) {
                    prt.setSupplierKey(supplierKey);
                }
            }
        }
    }

    // Converts XML file to a Media Object
    public Media xmlFileToMediaObject(String fullFileNamePath) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;

        // Convert XML File to Media Object
        try {
            docBuilder = dbf.newDocumentBuilder();
            Document document = docBuilder.parse(fullFileNamePath);

            JAXBContext jc = JAXBContext.newInstance(Media.class);
            Binder<Node> binder = jc.createBinder();
            // Pretty print output
            binder.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            Node xmlNode = document.getDocumentElement();
            Media m = (Media) binder.updateJAXB(xmlNode);

            return m;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        UpdateXml test = new UpdateXml();
        Media m = test.xmlFileToMediaObject(XML_PATH + "test-book.xml");
        m.setXmlns(MEDIA_XMLNS);
        //test.removePartsFromMediaRecursively(m);
        test.updatePartSuppliers(m, "CALLIFTEST");

        if (test.writeToFileWithXmlTransformer(m, XML_PATH + "out.xml")) {
            System.out.println("Wrote XML");
        } else {
            System.out.println("Failed to write XML");
        }
    }
}