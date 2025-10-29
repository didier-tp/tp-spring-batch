package tp.jtaSpringBatch.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class ProductFeatures {
	private String color;
	private Double weight;
	private String size;
	private String description;


	public ProductFeatures(String color, Double weight, String size, String description) {
		super();
		this.color = color;
		this.weight = weight;
		this.size = size;
		this.description = description;
	}

	@Override
	public String toString() {
		return "ProductFeatures [color=" + color + ", weight=" + weight + ", size=" + size + ", description="
				+ description + "]";
	}

}
