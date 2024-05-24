package tp.tpSpringBatch.model;

import jakarta.xml.bind.annotation.XmlRootElement;
//import jakarta.xml.bind.annotation.XmlRootElement;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;


//@Data
//@NoArgsConstructor
//@AllArgsConstructor
@XmlRootElement(name = "features")
public class ProductFeatures {
	private String color;
	private Double weight;
	private String size;
	private String description;
	
	public ProductFeatures() {
		super();
	}

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

	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
