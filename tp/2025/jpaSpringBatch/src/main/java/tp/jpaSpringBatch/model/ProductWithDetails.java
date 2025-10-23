package tp.jpaSpringBatch.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
//@XmlRootElement(name = "product_with_details") //just for read/generate XML file with jaxb2 marshaller
@Entity
@Table(name="product_with_details")
public class ProductWithDetails extends BasicProduct {

	private String sub_category;

    @Embedded()
    @AttributeOverrides(value = {
            @AttributeOverride(name = "color", column = @Column(name = "f_color")),
            @AttributeOverride(name = "weight", column = @Column(name = "f_weight")),
            @AttributeOverride(name = "size", column = @Column(name = "f_size")),
            @AttributeOverride(name = "description", column = @Column(name = "f_description"))
    })
	private ProductFeatures features ;


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

}
