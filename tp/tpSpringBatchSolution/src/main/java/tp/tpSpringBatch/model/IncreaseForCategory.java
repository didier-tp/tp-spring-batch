package tp.tpSpringBatch.model;

public class IncreaseForCategory {
	private String category;
	private Double increaseRatePct;
	
	public IncreaseForCategory(String category, Double increaseRatePct) {
		super();
		this.category = category;
		this.increaseRatePct = increaseRatePct;
	}
	
	public IncreaseForCategory() {
		this(null,null);
	}
	
	@Override
	public String toString() {
		return "IncreaseForCategory [category=" + category + ", increaseRatePct=" + increaseRatePct + "]";
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Double getIncreaseRatePct() {
		return increaseRatePct;
	}
	public void setIncreaseRatePct(Double increaseRatePct) {
		this.increaseRatePct = increaseRatePct;
	}
	
	
}
