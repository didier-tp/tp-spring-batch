package tp.jpaSpringBatch.reader;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tp.jpaSpringBatch.model.ProductStat;

@Configuration
public class MyDbProductStatJpaReaderConfig {
    /*
    //old jdbc/sql version:
	private static final String SELECT_QUERY = """
			SELECT main_category as category , count(*) as nb_prod , min(price) as min_price ,
			 max(price) as max_price , avg(price) as avg_price    
			 FROM PRODUCT_WITH_DETAILS GROUP BY main_category""";
	*/
/*  Attention :  COUNT(p) retourne une valeur de type Long et il faut qu'un des constructeurs de ProductStat ait
   un second paramètre de type Long  */
    private static final String SELECT_QUERY = """
			SELECT new tp.jpaSpringBatch.model.ProductStat( p.main_category ,
			                                               COUNT(p) ,
			                                               MIN(p.price) ,
			                                               MAX(p.price),
			                                               AVG(p.price) )     
			FROM ProductWithDetails p GROUP BY p.main_category """; //JPQL version

	 @Bean @Qualifier("db-jpa")
	  ItemReader<ProductStat> jdbcProductStatReader(
             @Qualifier("productdb") EntityManagerFactory entityManagerFactory
           ) {
         JpaPagingItemReader<ProductStat> jpaReader = new JpaPagingItemReader<>();
         jpaReader.setEntityManagerFactory(entityManagerFactory);
         jpaReader.setQueryString(SELECT_QUERY);//JPQL syntax
         jpaReader.setPageSize(3);
         return jpaReader;
     }

}
