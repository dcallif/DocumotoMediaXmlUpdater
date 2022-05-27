import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(name="",propOrder={"pageFile", "translations", "tags", "pages", "chapters"})
@XmlRootElement(name="Chapter")
public class Chapter
{
	private List<Page> pages = new ArrayList<Page>();
	private List<Chapter> chapters = new ArrayList<Chapter>();
	private List<Translation> translations = new ArrayList<Translation>();
	private List<Tag> tags = new ArrayList<Tag>();
	private String pageFile;

	public List<Page> getPages() {
		return pages;
	}

	@XmlElement(name="Page")
	public void setPages(List<Page> pages) {
		this.pages = pages;
	}

	public List<Chapter> getChapters() {
		return chapters;
	}

	@XmlElement(name="Chapter")
	public void setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
	}

	public List<Translation> getTranslations() {
		return translations;
	}

	@XmlElement(name="Translation")
	public void setTranslations(List<Translation> translations) {
		this.translations = translations;
	}

	public List<Tag> getTags() {
		return tags;
	}

	@XmlElement(name="Tag")
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	@XmlAttribute
	public String getPageFile() {return pageFile;}

	public void setPageFile(String pageFile) {this.pageFile = pageFile;}
}
