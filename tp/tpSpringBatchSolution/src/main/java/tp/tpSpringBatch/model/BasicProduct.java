package tp.tpSpringBatch.model;

//import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
//@XmlRootElement(name = "product")
public class BasicProduct {
	private Integer id;
	private String main_category;
	private String label;
	private Double price;
	private String time_stamp;
}
