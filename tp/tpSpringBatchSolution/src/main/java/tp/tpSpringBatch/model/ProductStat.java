package tp.tpSpringBatch.model;

import jakarta.xml.bind.annotation.XmlRootElement;
//import jakarta.xml.bind.annotation.XmlRootElement;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;


//@Data
//@NoArgsConstructor
//@AllArgsConstructor
@XmlRootElement(name = "product_stat")
public class ProductStat {
	private String category;
	private Integer number_of_products;
	private Double min_price;
	private Double max_price;
	private Double avg_price;
	
	public ProductStat() {
		super();
	}

	public ProductStat(String category, Integer number_of_products, Double min_price, Double max_price,
			Double avg_price) {
		super();
		this.category = category;
		this.number_of_products = number_of_products;
		this.min_price = min_price;
		this.max_price = max_price;
		this.avg_price = avg_price;
	}

	@Override
	public String toString() {
		return "ProductStat [category=" + category + ", number_of_products=" + number_of_products + ", min_price="
				+ min_price + ", max_price=" + max_price + ", avg_price=" + avg_price + "]";
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getNumber_of_products() {
		return number_of_products;
	}

	public void setNumber_of_products(Integer number_of_products) {
		this.number_of_products = number_of_products;
	}

	public Double getMin_price() {
		return min_price;
	}

	public void setMin_price(Double min_price) {
		this.min_price = min_price;
	}

	public Double getMax_price() {
		return max_price;
	}

	public void setMax_price(Double max_price) {
		this.max_price = max_price;
	}

	public Double getAvg_price() {
		return avg_price;
	}

	public void setAvg_price(Double avg_price) {
		this.avg_price = avg_price;
	}
	

	
}
