import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ProcessingInstructions")
public class ProcessingInstructions
{
	private boolean doAutoHotpoint;

	public boolean isDoAutoHotpoint() {
		return doAutoHotpoint;
	}

	@XmlAttribute(name="doAutoHotpoint")
	public void setDoAutoHotpoint(boolean doAutoHotpoint) {
		this.doAutoHotpoint = doAutoHotpoint;
	}
}
