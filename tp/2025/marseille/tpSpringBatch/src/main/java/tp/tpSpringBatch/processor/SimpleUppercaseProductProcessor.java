package tp.tpSpringBatch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import tp.tpSpringBatch.model.Product;

@Component
public class SimpleUppercaseProductProcessor implements ItemProcessor<Product,Product> {
    public Product process(Product p) throws Exception {
        Product product=new Product(p.getId(),
                                   p.getMain_category().toUpperCase() ,
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

