package tp.tpSpringBatch.processor;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import tp.tpSpringBatch.model.ProductWithDetails;
import tp.tpSpringBatch.model.wrapper.ProductToUpdate;

/*
 * NB: cette version du processeur va récupérer les paramétrages
 * dans un "wrapper" préparé par un certain reader "CategoryDrivenProductToIncreaseReader" .
 */


@Component
@StepScope
//@JobScope
public class IncreasePriceOfProductCategoryDrivenProcessor implements ItemProcessor<ProductToUpdate,ProductWithDetails>{
	
	public static final Logger logger = LoggerFactory.getLogger(IncreasePriceOfProductCategoryDrivenProcessor.class);
	

	@Override
	public ProductWithDetails process(ProductToUpdate productToUpdate) throws Exception {
		ProductWithDetails p = productToUpdate.getProductWithDetails(); //source (dans wrapper) à modifier
		
		Double newPrice  = p.getPrice() * (1 + productToUpdate.getIncreaseRatePct()/100.0);
		String newTimeStamp = LocalDateTime.now().toString();
		
		ProductWithDetails product=new ProductWithDetails(p.getId(),p.getMain_category(),p.getSub_category(),p.getLabel(),
                    newPrice,newTimeStamp,p.getFeatures());
		logger.debug("IncreasePriceOfProductCategoryDrivenProcessor.process() called with p = " + p);
		return product;		
	}
}
