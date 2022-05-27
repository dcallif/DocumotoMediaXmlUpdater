import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by dcallif on 3/12/18.
 */
@XmlType(name="",propOrder={"displayOrder", "fileName"})
@XmlRootElement(name="SecondaryPageFile")
public class SecondaryPageFile {
    private String displayOrder;
    private String fileName;

    public String getDisplayOrder() {
        return displayOrder;
    }
    @XmlAttribute(name="displayOrder")
    public void setDisplayOrder(String displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getFileName() {
        return fileName;
    }
    @XmlAttribute(name="fileName")
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
