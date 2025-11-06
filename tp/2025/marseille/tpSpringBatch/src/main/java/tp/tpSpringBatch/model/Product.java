package tp.tpSpringBatch.model;


import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "product") //just for read/generate XML file with jaxb2 marshaller
public class Product extends BasicProduct {
    private String features;

	public Product() {
		super();
	}

	public Product(Integer id, String main_category, String label, Double price, String time_stamp, String features) {
		super(id, main_category, label, price, time_stamp);
		this.features=features;
	}

	@Override
	public String toString() {
		return "Product [features=" + features + "] " + super.toString();
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}
    
	
	
}
