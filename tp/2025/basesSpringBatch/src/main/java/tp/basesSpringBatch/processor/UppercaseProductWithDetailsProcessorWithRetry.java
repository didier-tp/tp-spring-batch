package tp.basesSpringBatch.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tp.basesSpringBatch.exception.MyProcessException;
import tp.basesSpringBatch.model.ProductWithDetails;

@Component
@StepScope
@Slf4j
public class UppercaseProductWithDetailsProcessorWithRetry implements ItemProcessor<ProductWithDetails,ProductWithDetails>{

    public static int numberOfFailures=0;
    public static int maxRetry=3;
	
	@Value("#{jobParameters['enableUpperCase']}") 
	private Boolean enableUpperCase=true;

	@Override
	public ProductWithDetails process(ProductWithDetails p) throws Exception {

        numberOfFailures++;
        if(numberOfFailures>0 && numberOfFailures < maxRetry ) {
            throw new MyProcessException("processExceptionSimulation " +
                    "with numberOfFailures=" + numberOfFailures);
        }
		String mainCategory =enableUpperCase?p.getMain_category().toUpperCase():p.getMain_category();
		//String mainCategory = p.getMain_category().toUpperCase();
        ProductWithDetails product=new ProductWithDetails(p.getId(),mainCategory,p.getSub_category() ,p.getLabel(),p.getPrice(),p.getTime_stamp(),p.getFeatures());
		//log.debug("PPPP  SimpleUppercaseProductProcessor.process() returning product=" + product);
		return product;
	}
}
