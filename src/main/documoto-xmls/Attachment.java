import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Attachment")
public class Attachment
{
	private boolean global;
	private boolean publicBelowOrg;
	private String userName;
	private String comments;

	public Attachment () { }

	public Attachment (boolean global, boolean publicBelowOrg, String userName)
	{
		this.setGlobal(global);
		this.setPublicBelowOrg(publicBelowOrg);
		this.setUserName(userName);
	}

	public boolean isGlobal() {
		return global;
	}

	@XmlAttribute(name="global")
	public void setGlobal(boolean global) {
		this.global = global;
	}

	public boolean isPublicBelowOrg() {
		return publicBelowOrg;
	}

	@XmlAttribute(name="publicBelowOrg")
	public void setPublicBelowOrg(boolean publicBelowOrg) {
		this.publicBelowOrg = publicBelowOrg;
	}

	public String getUserName() {
		return userName;
	}

	@XmlAttribute(name="userName")
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getComments() {
		return comments;
	}

	@XmlElement(name="Comments")
	public void setComments(String comments) {
		this.comments = comments;
	}
}
