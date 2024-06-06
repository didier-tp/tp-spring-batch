package tp.tpSpringBatch.processor;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import tp.tpSpringBatch.model.ProductWithDetails;

/*
 * NB: cette version du processeur va récupérer les paramétrages
 * dans jobParameters .
 */


@Component
@StepScope
//@JobScope
public class IncreasePriceOfProductWithDetailsProcessor implements ItemProcessor<ProductWithDetails,ProductWithDetails>{
	
	public static final Logger logger = LoggerFactory.getLogger(IncreasePriceOfProductWithDetailsProcessor.class);
	
	@Value("#{jobParameters['increaseRatePct']}") 
	private Double increaseRatePct =0.0;//taux d'augmentation en %
	
	@Value("#{jobParameters['slowProcessorDelay']}") 
	private Long slowProcessorDelay =null;//nb ms de pause (simulation traitement long)
	
	@Value("#{jobParameters['productCategoryToIncrease']}") 
	private String productCategoryToIncrease ="?";//ex: "aliment" ou "vetement" ou ...

	@Value("#{stepExecution}")//ok with @StepScope
	private StepExecution stepExecution;
	
	private void incrementNumberOfAjustedProducts(){
		Integer nbAjustedProducts = (Integer) stepExecution.getExecutionContext().get("nbAjustedProducts");
	    nbAjustedProducts=(nbAjustedProducts!=null)?(nbAjustedProducts+1):1;
	    stepExecution.getExecutionContext().put("nbAjustedProducts",nbAjustedProducts);
	}
	
	

	@Override
	public ProductWithDetails process(ProductWithDetails p) throws Exception {
		
		if(slowProcessorDelay!=null) {
			Thread.currentThread().sleep(slowProcessorDelay); //simulation traitement long
		}
		
		if( ( p.getMain_category()!=null && p.getMain_category().equals(productCategoryToIncrease) )
				|| productCategoryToIncrease.equals("all")) {
			Double newPrice  = p.getPrice() * (1 + increaseRatePct/100.0);
			String newTimeStamp = LocalDateTime.now().toString();
			incrementNumberOfAjustedProducts();
			ProductWithDetails product=new ProductWithDetails(p.getId(),p.getMain_category(),p.getSub_category(),p.getLabel(),
                    newPrice,newTimeStamp,p.getFeatures());
			return product;
		}else {
			return p;
		}
	}
}
