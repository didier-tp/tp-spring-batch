package tp.jpaSpringBatch.model;

//import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor
//@XmlRootElement(name = "product_stat")

/*
Not a JPA entity, but a POJO (Plain Old Java Object) used to hold aggregated product statistics.
 */
public class ProductStat {
	private String category;
	private Integer number_of_products;
	private Double min_price;
	private Double max_price;
	private Double avg_price;
	


	public ProductStat(String category, Integer number_of_products, Double min_price, Double max_price,
			Double avg_price) {
		super();
		this.category = category;
		this.number_of_products = number_of_products;
		this.min_price = min_price;
		this.max_price = max_price;
		this.avg_price = avg_price;
	}

    //additional constructor to convert Long to Integer for number_of_products (because COUNT in JPQL returns Long)
    public ProductStat(String category, Long number_of_products_as_long, Double min_price, Double max_price,
                       Double avg_price) {
          this(category,
               number_of_products_as_long != null ? number_of_products_as_long.intValue() : null,
               min_price,
               max_price,
               avg_price);
    }


}
