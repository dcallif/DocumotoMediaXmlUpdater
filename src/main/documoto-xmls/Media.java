import javax.xml.bind.annotation.*;
import java.util.List;

@XmlType(name="",propOrder={"tenantKey", "mediaType", "identifier", "xmlns", "translations", "tags", "attachments", "chapters", "pages"})
@XmlRootElement(name="Media")
@XmlAccessorType(XmlAccessType.FIELD)
public class Media
{
	@XmlAttribute
	private String mediaType;
	@XmlAttribute
	private String tenantKey;
	@XmlAttribute
	private String identifier;
	@XmlAttribute
	private String xmlns;

	@XmlElement(name="Translation")
	private List<Translation> translations;
	@XmlElement(name="Tag")
	private List<Tag> tags;
	@XmlElement(name="Page")
	private List<Page> pages;
	@XmlElement(name="Chapter")
	private List<Chapter> chapters;
	@XmlElement(name="Attachment")
	private List<Attachment> attachments;

	public Media () { }

	public Media (String identifier, String tenantKey, String mediaType)
	{
		this.setIdentifier(identifier);
		this.setTenantKey(tenantKey);
		this.setMediaType(mediaType);
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getTenantKey() {
		return tenantKey;
	}

	public void setTenantKey(String tenantKey) {
		this.tenantKey = tenantKey;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public List<Page> getPages() {
		return pages;
	}

	public void setPages(List<Page> pages) {
		this.pages = pages;
	}

	public List<Chapter> getChapters() {
		return chapters;
	}

	public void setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
	}

	public List<Translation> getTranslations() {
		return translations;
	}

	public void setTranslations(List<Translation> translations) {
		this.translations = translations;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public String getXmlns() {
		return xmlns;
	}

	public void setXmlns(String xmlns) {
		this.xmlns = xmlns;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
}
