import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(name="",propOrder={"tenantKey", "hashKey",  "pageFile", "processingInstructions", "autoHotpointRequest", "translations", "secondaryPages", "tags", "attachment", "parts"})
@XmlRootElement(name="Page")
public class Page {
private String pageFile;
private String tenantKey;
private String xmlns;
private String xmlns_xs;
private String hashKey;

private ProcessingInstructions processingInstructions;
private List<Attachment> attachment;
private List<Translation> translations = new ArrayList<Translation>();
private List<Part> parts = new ArrayList<Part>();
private AutoHotpointRequest autoHotpointRequest;
private List<Tag> tags = new ArrayList<Tag>();

private List<SecondaryPageFile> secondaryPages = new ArrayList<SecondaryPageFile>();

public Page () {  }

public Page(String pageFile, String tenantKey, String xmlns, String xmlns_xs)
{
    this.setPageFile(pageFile);
    this.setTenantKey(tenantKey);
    this.setXmlns(xmlns);
    this.setXmlns_xs(xmlns_xs);
}

@XmlAttribute(name="pageFile")
public void setPageFile( String pageFile )
{
    this.pageFile = pageFile;
}

public String getPageFile()
{
    return this.pageFile;
}

@XmlAttribute(name="tenantKey")
public void setTenantKey( String tenantKey )
{
    this.tenantKey = tenantKey;
}

public String getTenantKey()
{
    return this.tenantKey;
}

public String getXmlns()
{
    return xmlns;
}

@XmlAttribute(name="xmlns")
public void setXmlns(String xmlns)
{
    this.xmlns = xmlns;
}

public String getXmlns_xs()
{
    return xmlns_xs;
}

@XmlAttribute(name="xmlns:xs")
public void setXmlns_xs(String xmlns_xs)
{
    this.xmlns_xs = xmlns_xs;
}

public List<Attachment> getAttachment() {
    return attachment;
}

@XmlElement(name="Attachment")
public void setAttachment(List<Attachment> attachment) {
    this.attachment = attachment;
}

public List<Translation> getTranslations() {
    return translations;
}

@XmlElement(name="Translation")
public void setTranslations(List<Translation> translations) {
    this.translations = translations;
}

public List<Part> getParts() {
    return parts;
}

@XmlElement(name="Part")
public void setParts(List<Part> parts) {
    this.parts = parts;
}

public String getHashKey() {
    return hashKey;
}

@XmlAttribute(name="hashKey")
public void setHashKey(String hashKey) {
    this.hashKey = hashKey;
}

public AutoHotpointRequest getAutoHotpointRequest() {
    return autoHotpointRequest;
}

@XmlElement(name="AutoHotpointRequest")
public void setAutoHotpointRequest(AutoHotpointRequest autoHotpointRequest) {
    this.autoHotpointRequest = autoHotpointRequest;
}

public ProcessingInstructions getProcessingInstructions() {
    return processingInstructions;
}

@XmlElement(name="ProcessingInstructions")
public void setProcessingInstructions(ProcessingInstructions processingInstructions) {
    this.processingInstructions = processingInstructions;
}

public List<Tag> getTags() {
    return tags;
}

@XmlElement(name="Tag")
public void setTags(List<Tag> tags) {
    this.tags = tags;
}

public List<SecondaryPageFile> getSecondaryPages() {
    return secondaryPages;
}
@XmlElement(name="SecondaryPageFile")
public void setSecondaryPages(List<SecondaryPageFile> secondaryPages) {
    this.secondaryPages = secondaryPages;
}
}
