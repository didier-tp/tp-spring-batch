package tp.basesSpringBatch.model;

//import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
//@XmlRootElement(name = "product") //just for read/generate XML file with jaxb2 marshaller
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
    
	
	
}
