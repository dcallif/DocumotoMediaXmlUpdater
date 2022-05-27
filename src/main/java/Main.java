import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String RESOURCE_PATH = "src/main/resources/";
    private static final String XML_PATH = "src/main/xmls/";

    private static final String MEDIA_XSD_VERSION = "documoto_media1.4.xsd";
    private static final String MEDIA_XMLNS = "http://digabit.com/documoto/media/1.4";
    private static final String PAGE_XSD_VERSION = "documoto_partslist1.6.xsd";
    private static final String PAGE_XMLNS = "http://digabit.com/documoto/partslist/1.6";

    private void deletePartsFromMediaXml(String xsdLocation, String xmlLocation, String newXmlLocation) {
        ValidateXml validator = new ValidateXml();
        // Check if XML is valid first
        boolean validXml = validator.isXmlValid(xsdLocation, xmlLocation);
        if (validXml) {
            UpdateXml partDeletor = new UpdateXml();
            // Convert XML file to Media Object
            Media m = partDeletor.xmlFileToMediaObject(xmlLocation);
            m.setXmlns(MEDIA_XMLNS);
            partDeletor.removePartsFromMedia(m);

            if (partDeletor.writeToFileWithXmlTransformer(m, newXmlLocation)) {
                System.out.println("Wrote updated XML");
            } else {
                System.out.println("Failed to write XML");
            }
        } else {
            // do something else
        }
    }

    private void deletePartsFromMediaXmlsInDirectory(String xsdLocation, String xmlsLocation, String newXmlsLocation) {
        List<String> xmlFiles = new ArrayList<>();

        File folder = new File(xmlsLocation);
        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory() && !fileEntry.isHidden()) {
                xmlFiles.add(fileEntry.getAbsolutePath());
            }
        }

        if (xmlFiles.size() > 0) {
            for (String s : xmlFiles) {
                String newXmlFilename = s.substring(s.lastIndexOf("/") + 1);
                newXmlFilename = newXmlFilename.substring(0, newXmlFilename.lastIndexOf(".")) + "_editted.xml";
                deletePartsFromMediaXml(xsdLocation, s, newXmlsLocation + newXmlFilename);
            }
        }
    }

    public static void main(String[] args) {
        Main test = new Main();
        // Delete Parts from 1 Book
        //test.deletePartsFromMediaXml(RESOURCE_PATH + MEDIA_XSD_VERSION, XML_PATH + "large-book.xml", XML_PATH + "out.xml");
        // Delete Parts from directory of Media XMLs
        test.deletePartsFromMediaXmlsInDirectory(RESOURCE_PATH + MEDIA_XSD_VERSION,
                "/Users/Daniel/Downloads/test/editted/books-only/",
                "/Users/Daniel/Downloads/test/editted/books-only/removedParts/");
    }
}