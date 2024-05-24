package tp.tpSpringBatch.processor;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import tp.tpSpringBatch.model.ProductWithDetails;

@Component
@StepScope
//@JobScope
public class IncreasePriceOfProductWithDetailsProcessor implements ItemProcessor<ProductWithDetails,ProductWithDetails>{
	
	public static final Logger logger = LoggerFactory.getLogger(IncreasePriceOfProductWithDetailsProcessor.class);
	
	@Value("#{jobParameters['increaseRatePct']}") 
	private Double increaseRatePct =0.0;//taux d'augmentation en %

	@Override
	public ProductWithDetails process(ProductWithDetails p) throws Exception {
		Double newPrice  = p.getPrice() * (1 + increaseRatePct/100.0);
		
		String newTimeStamp = LocalDateTime.now().toString();
		ProductWithDetails product=new ProductWithDetails(p.getId(),p.getMain_category(),p.getSub_category(),p.getLabel(),
				                                          newPrice,newTimeStamp,p.getFeatures());
		return product;
	}
}
