package tp.tpSpringBatch.reader.custom;

import java.time.LocalDateTime;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import tp.tpSpringBatch.model.ProductFeatures;
import tp.tpSpringBatch.model.ProductWithDetails;

public class ProductWithDetailsGeneratorReader extends AbstractProductGenerator implements ItemReader<ProductWithDetails>{
	

	public ProductWithDetailsGeneratorReader() {
		super();
	}

	public ProductWithDetailsGeneratorReader(long dataSetSize) {
		super(dataSetSize);
	}

	private ProductWithDetails generateProductWithDetails(){
		index++;
		int nbColors = colorList.size();
		int nbSizes = sizeList.size();
		int nbCategories = mainCategoryList.size();
		if(index<=dataSetSize) {
			double randomCoeff = Math.random();
			double price = Math.floor(10.0 * (1 + randomCoeff));
			String timeStamp = LocalDateTime.now().toString();
			String main_category = mainCategoryList.get((int)(nbCategories * randomCoeff) % nbCategories);
			String label =  main_category + "_" + index;
			String color = colorList.get((int)(nbColors * randomCoeff) % nbColors);
			double weight = Math.floor(500 * (1 + randomCoeff));
			String size = sizeList.get((int)(nbSizes * randomCoeff) % nbSizes);
			String description = "...";
			ProductFeatures features = new ProductFeatures(color, weight, size, description);
			return new ProductWithDetails(null, main_category, null , label, price, timeStamp, features );
		}else
			return null;
	}
		
	@Override
	public ProductWithDetails read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		return generateProductWithDetails();
	}


}
