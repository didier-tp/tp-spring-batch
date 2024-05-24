package tp.tpSpringBatch.model;

import jakarta.xml.bind.annotation.XmlRootElement;
//import lombok.Getter;
//import lombok.Setter;

//@Getter @Setter
@XmlRootElement(name = "product_with_details") //just for read/generate XML file with jaxb2 marshaller
public class ProductWithDetails extends BasicProduct {
	private String sub_category;
	private ProductFeatures features;

	public ProductWithDetails() {
		super();
		features=new ProductFeatures(null,0.0,null,null);
	}

	public ProductWithDetails(Integer id, String main_category, String sub_category , String label, Double price, String time_stamp, ProductFeatures features) {
		super(id, main_category, label, price, time_stamp);
		this.sub_category=sub_category;
		this.features=features;
	}

	@Override
	public String toString() {
		return "ProductWithDetails [sub_category= " + sub_category + " features=" + features + "] " + super.toString();
	}

	public String getSub_category() {
		return sub_category;
	}

	public void setSub_category(String sub_category) {
		this.sub_category = sub_category;
	}

	public ProductFeatures getFeatures() {
		return features;
	}

	public void setFeatures(ProductFeatures features) {
		this.features = features;
	}

	
}
