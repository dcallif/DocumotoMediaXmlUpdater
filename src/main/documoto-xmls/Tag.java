import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Tag")
public class Tag
{
	private String isLocalToPage;
	private String name;
	private String value;

	public Tag () {  }

	public Tag(String isLocalToPage, String name, String value)
	{
		this.setIsLocalToPage(isLocalToPage);
		this.setName(name);
		this.setValue(value);
	}

	public String getIsLocalToPage() {
		return isLocalToPage;
	}

	@XmlAttribute()
	public void setIsLocalToPage(String isLocalToPage) {
		this.isLocalToPage = isLocalToPage;
	}

	public String getName() {
		return name;
	}

	@XmlAttribute()
	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	@XmlAttribute()
	public void setValue(String value) {
		this.value = value;
	}
}
