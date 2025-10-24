package tp.restartableBatch.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class BasicProduct {
	private Integer id;
	private String main_category;
	private String label;
	private Double price;
	private String time_stamp;
	

	public BasicProduct(Integer id, String main_category, String label, Double price, String time_stamp) {
		super();
		this.id = id;
		this.main_category = main_category;
		this.label = label;
		this.price = price;
		this.time_stamp = time_stamp;
	}
	


}
