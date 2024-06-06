package tp.tpSpringBatch.model.wrapper;

import tp.tpSpringBatch.model.ProductWithDetails;

public class ProductToUpdate {
	
	private ProductWithDetails productWithDetails;
	private Double increaseRatePct; //en %
	
	public ProductToUpdate() {
		this(null,0.0);
	}
   
	public ProductToUpdate(ProductWithDetails productWithDetails, Double increaseRatePct) {
		super();
		this.productWithDetails = productWithDetails;
		this.increaseRatePct = increaseRatePct;
	}
	@Override
	public String toString() {
		return "ProductToUpdate [productWithDetails=" + productWithDetails + ", increaseRatePct=" + increaseRatePct
				+ "]";
	}
	public ProductWithDetails getProductWithDetails() {
		return productWithDetails;
	}
	public void setProductWithDetails(ProductWithDetails productWithDetails) {
		this.productWithDetails = productWithDetails;
	}
	public Double getIncreaseRatePct() {
		return increaseRatePct;
	}
	public void setIncreaseRatePct(Double increaseRatePct) {
		this.increaseRatePct = increaseRatePct;
	}
	
	

}
