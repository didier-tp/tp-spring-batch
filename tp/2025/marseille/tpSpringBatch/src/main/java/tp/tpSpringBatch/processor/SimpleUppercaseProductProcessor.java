package tp.tpSpringBatch.processor;

import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tp.tpSpringBatch.model.Product;

@Component
@JobScope
public class SimpleUppercaseProductProcessor implements ItemProcessor<Product,Product> {

    @Value("#{jobParameters['enableUpperCase']}")
    private Boolean enableUpperCase=true;

    public Product process(Product p) throws Exception {

        String mainCategory =enableUpperCase?p.getMain_category().toUpperCase():p.getMain_category();
        //String mainCategory = p.getMain_category().toUpperCase();

        Product product=new Product(p.getId(),
                                   mainCategory ,
                                   p.getLabel(),
                                   p.getPrice(),
                                   p.getTime_stamp(),
                                  p.getFeatures());
        return product;
    }
}

/*
NB:
* soit directement @Component au dessus de la classe SimpleUppercaseProductProcessor
   qui est ici simple (sans <T>)
* soit classe complementaire
 @Configuration
 public class ProductProcessorConfig{

     @Bean
     SimpleUppercaseProductProcessor productProcessor(){
         return new SimpleUppercaseProductProcessor();
     }
   }
 */

