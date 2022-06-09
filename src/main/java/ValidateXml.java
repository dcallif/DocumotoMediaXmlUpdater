import java.io.File;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

/**
 * @author Daniel
 */
public class ValidateXml {
    boolean isXmlValid(String xsdLocation, String xmlLocation) {
        File schemaFile = new File(xsdLocation);
        File xmlFile = new File(xmlLocation);
        Source xmlSource = new StreamSource(xmlFile);
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = null;

        // Check if XML is valid against XSD
        try {
            schema = schemaFactory.newSchema(schemaFile);

            Validator validator = schema.newValidator();
            validator.validate(xmlSource);
            //System.out.println(String.format("XML: %s, is valid", xmlFile.getName()));
            return true;
        } catch (SAXException | IOException e) {
            //System.out.println(String.format("Invalid XML: %s, Reason: %s", xmlFile.getName(), e.getMessage()));
            return false;
        }
    }

    public static void main(String[] args) {
        String RESOURCE_PATH = "src/main/resources/";
        String XML_PATH = "src/main/xmls/";

        ValidateXml validate = new ValidateXml();
        validate.isXmlValid( RESOURCE_PATH + "documoto_media1.4.xsd", XML_PATH + "test-book.xml");
        validate.isXmlValid( RESOURCE_PATH + "documoto_partslist1.6.xsd", XML_PATH + "page.xml");
    }
}