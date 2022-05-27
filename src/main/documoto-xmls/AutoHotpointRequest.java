import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="AutoHotpointRequest")
public class AutoHotpointRequest
{
	private boolean setWhiteBackground;
	private int minThreshold;
	private int maxThreshold;
	private int thresholdStep;
	private int minArea;
	private int maxArea;
	private int minDistBetweenBlobs;
	private int minOcrConfidence;

	public boolean isSetWhiteBackground() {
		return setWhiteBackground;
	}

	@XmlAttribute(name="setWhiteBackground")
	public void setSetWhiteBackground(boolean setWhiteBackground) {
		this.setWhiteBackground = setWhiteBackground;
	}

	public int getMinThreshold() {
		return minThreshold;
	}

	@XmlAttribute(name="minThreshold")
	public void setMinThreshold(int minThreshold) {
		this.minThreshold = minThreshold;
	}

	public int getMaxThreshold() {
		return maxThreshold;
	}

	@XmlAttribute(name="maxThreshold")
	public void setMaxThreshold(int maxThreshold) {
		this.maxThreshold = maxThreshold;
	}
	public int getThresholdStep() {
		return thresholdStep;
	}

	@XmlAttribute(name="thresholdStep")
	public void setThresholdStep(int thresholdStep) {
		this.thresholdStep = thresholdStep;
	}
	public int getMinArea() {
		return minArea;
	}

	@XmlAttribute(name="minArea")
	public void setMinArea(int minArea) {
		this.minArea = minArea;
	}
	public int getMaxArea() {
		return maxArea;
	}

	@XmlAttribute(name="maxArea")
	public void setMaxArea(int maxArea) {
		this.maxArea = maxArea;
	}
	public int getMinDistBetweenBlobs() {
		return minDistBetweenBlobs;
	}

	@XmlAttribute(name="minDistBetweenBlobs")
	public void setMinDistBetweenBlobs(int minDistBetweenBlobs) {
		this.minDistBetweenBlobs = minDistBetweenBlobs;
	}
	public int getMinOcrConfidence() {
		return minOcrConfidence;
	}

	@XmlAttribute(name="minOcrConfidence")
	public void setMinOcrConfidence(int minOcrConfidence) {
		this.minOcrConfidence = minOcrConfidence;
	}
}
