import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.List;

@XmlRootElement(name="Translation")
public class Translation
{
	private String locale;
	private String name;
	private String description;

	public Translation () {  }

	public Translation(String locale, String name, String description)
	{
		this.setLocale(locale);
		this.setName(name);
		this.setDescription(description);
	}

	public String getLocale() {
		return locale;
	}

	@XmlAttribute(name="locale")
	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getName() {
		return name;
	}

	@XmlAttribute(name="name")
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	@XmlAttribute(name="description")
	public void setDescription(String description) {
		this.description = description;
	}

	public static List<Translation> createTranslations(String locale, String name, String description) {
		return Collections.singletonList(new Translation(locale,name,description));
	}
}
