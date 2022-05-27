import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(name="",propOrder={"instanceId", "item", "supplierKey", "quantity", "partNumber", "unitOfMeasure", "orderable", "orderableForPage", "partNumberVisible", "partNumberVisibleForPage", "translations", "tags"})
@XmlRootElement(name="Part")
public class Part
{
	private String supplierKey;
	private String quantity;
	private String partNumber;
	private String unitOfMeasure;
	private String item;
	private String discountedPrice;
	private String retailPrice;
	private String wholesalePrice;
	private int instanceId;
	private String orderable;
	private String orderableForPage;
	private String partNumberVisible;
	private String partNumberVisibleForPage;

	private List<Translation> translations = new ArrayList<Translation>();
	private List<Tag> tags = new ArrayList<Tag>();

	public Part () {  }

	public Part(String supplierKey, String quantity, String partNumber, String unitOfMeasure, String item, String discountedPrice, String retailPrice, String wholesalePrice, int instanceId, String orderable, String orderableForPage, String partNumberVisible, String partNumberVisibleForPage, List<Translation> translations, List<Tag> tags) {
		this.supplierKey = supplierKey;
		this.quantity = quantity;
		this.partNumber = partNumber;
		this.unitOfMeasure = unitOfMeasure;
		this.item = item;
		this.discountedPrice = discountedPrice;
		this.retailPrice = retailPrice;
		this.wholesalePrice = wholesalePrice;
		this.instanceId = instanceId;
		this.orderable = orderable;
		this.orderableForPage = orderableForPage;
		this.partNumberVisible = partNumberVisible;
		this.partNumberVisibleForPage = partNumberVisibleForPage;
		this.translations = translations;
		this.tags = tags;
	}

	public Part(String supplierKey, String quantity, String partNumber, String unitOfMeasure, String item, String instanceId)
	{
		this.setSupplierKey(supplierKey);
		this.setQuantity(quantity);
		this.setPartNumber(partNumber);
		this.setUnitOfMeasure(unitOfMeasure);
		this.setItem(item);
	}

	public String getSupplierKey() {
		return supplierKey;
	}

	@XmlAttribute(name="supplierKey")
	public void setSupplierKey(String supplierKey) {
		this.supplierKey = supplierKey;
	}

	public String getQuantity() {
		return quantity;
	}

	@XmlAttribute(name="quantity")
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getPartNumber() {
		return partNumber;
	}

	@XmlAttribute(name="partNumber")
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	@XmlAttribute(name="unitOfMeasure")
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public String getItem() {
		return item;
	}

	@XmlAttribute(name="item")
	public void setItem(String item) {
		this.item = item;
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

	public String getDiscountedPrice() {
		return discountedPrice;
	}

	@XmlAttribute(name="discountedPrice")
	public void setDiscountedPrice(String discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	public String getRetailPrice() {
		return retailPrice;
	}

	@XmlAttribute(name="retailPrice")
	public void setRetailPrice(String retailPrice) {
		this.retailPrice = retailPrice;
	}

	public String getWholesalePrice() {
		return wholesalePrice;
	}

	@XmlAttribute(name="wholesalePrice")
	public void setWholesalePrice(String wholesalePrice) {
		this.wholesalePrice = wholesalePrice;
	}

	public int getInstanceId() {
		return instanceId;
	}

	@XmlAttribute(name="instanceId")
	public void setInstanceId(int instanceId) {
		this.instanceId = instanceId;
	}

	public String getOrderable() {
		return orderable;
	}

	@XmlAttribute(name="orderable")
	public void setOrderable(String orderable) {
		this.orderable = orderable;
	}

	public String getOrderableForPage() {
		return orderableForPage;
	}

	@XmlAttribute(name="orderableForPage")
	public void setOrderableForPage(String orderableForPage) {
		this.orderableForPage = orderableForPage;
	}

	public String isPartNumberVisible() {
		return partNumberVisible;
	}

	@XmlAttribute(name="partNumberVisible")
	public void setPartNumberVisible(String partNumberVisible) {
		this.partNumberVisible = partNumberVisible;
	}

	public String isPartNumberVisibleForPage() {
		return partNumberVisibleForPage;
	}

	@XmlAttribute(name="partNumberVisibleForPage")
	public void setPartNumberVisibleForPage(String partNumberVisibleForPage) {
		this.partNumberVisibleForPage = partNumberVisibleForPage;
	}
}
