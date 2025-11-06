package tp.tpSpringBatch.model;

//import jakarta.xml.bind.annotation.XmlRootElement;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

//@XmlRootElement(name = "product")
@MappedSuperclass
public class BasicProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String main_category;
	private String label;
	private Double price;
	private String time_stamp;
	
	public BasicProduct() {
		super();
	}
	public BasicProduct(Integer id, String main_category, String label, Double price, String time_stamp) {
		super();
		this.id = id;
		this.main_category = main_category;
		this.label = label;
		this.price = price;
		this.time_stamp = time_stamp;
	}
	

	@Override
	public String toString() {
		return "BasicProduct [id=" + id + ", main_category=" + main_category + ", label=" + label + ", price=" + price
				+ ", time_stamp=" + time_stamp + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMain_category() {
		return main_category;
	}
	public void setMain_category(String main_category) {
		this.main_category = main_category;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getTime_stamp() {
		return time_stamp;
	}
	public void setTime_stamp(String time_stamp) {
		this.time_stamp = time_stamp;
	}
	
	
}
