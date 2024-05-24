package tp.tpSpringBatch.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import tp.tpSpringBatch.model.Product;

@Component
@StepScope
//@JobScope
public class SimpleUppercaseProductProcessor implements ItemProcessor<Product,Product>{
	
	public static final Logger logger = LoggerFactory.getLogger(SimpleUppercaseProductProcessor.class);
	
	@Value("#{jobParameters['enableUpperCase']}") 
	private Boolean enableUpperCase=true;

	@Override
	public Product process(Product p) throws Exception {
		String mainCategory =enableUpperCase?p.getMain_category().toUpperCase():p.getMain_category();
		//String mainCategory = p.getMain_category().toUpperCase();
		Product product=new Product(p.getId(),mainCategory,p.getLabel(),p.getPrice(),p.getTime_stamp(),p.getFeatures());
		//logger.debug("PPPP  SimpleUppercaseProductProcessor.process() returning product=" + product);
		return product;
	}
}
