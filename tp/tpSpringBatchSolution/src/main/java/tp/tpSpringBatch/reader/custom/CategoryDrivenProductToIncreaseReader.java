package tp.tpSpringBatch.reader.custom;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import tp.tpSpringBatch.db.ProductWithDetailsRowMapper;
import tp.tpSpringBatch.model.IncreaseForCategory;
import tp.tpSpringBatch.model.ProductWithDetails;
import tp.tpSpringBatch.model.wrapper.ProductToUpdate;

/*
 custom reader with delegate pattern
 - driven by categories (with specific increaseRatePct)
 - delegate retreiving of category;increaseRatePct in a csv file
 - delegate retreiving of dataBase data to a subReader
 - store read productWithDetails in a "ProductToUpdate" wrapper for IncreasePriceOfProductCategoryDrivenProcessor
 */

public class CategoryDrivenProductToIncreaseReader  implements ItemReader<ProductToUpdate>{
	
  private static final Logger logger = LoggerFactory.getLogger(CategoryDrivenProductToIncreaseReader.class);

  private StepExecution stepExecution=null; //for storing stepExecution after beforeStep() call
  
  private DataSource productdbDataSource;
  //subReader (for delegate) , initialized by outside constructor call:
  private FlatFileItemReader<IncreaseForCategory> increaseForCategoryCsvMainDelegateReader;
  //secondary subReader (for delegate) , re-build inside this reader with dataSource
  private JdbcPagingItemReader<ProductWithDetails> productWithDetailsDbDrivenDelegateReader;
 
  public CategoryDrivenProductToIncreaseReader(
		  FlatFileItemReader<IncreaseForCategory> increaseForCategoryCsvMainDelegateReader,
		  DataSource productdbDataSource
		  ){
	  this.increaseForCategoryCsvMainDelegateReader=increaseForCategoryCsvMainDelegateReader;
	  this.productdbDataSource=productdbDataSource;
  }
  

  private IncreaseForCategory currentIncreaseForCategory=null; //for main loop
 
  
  private void readNewIncreaseForCategoryViaMainDelegateReader() throws Exception{
	  currentIncreaseForCategory=increaseForCategoryCsvMainDelegateReader.read();
	  logger.debug("CategoryDrivenProductToIncreaseReader.readNewIncreaseForCategoryViaMainDelegateReader() called with currentIncreaseForCategory="+currentIncreaseForCategory);
	  this.productWithDetailsDbDrivenDelegateReader=this.buildJdbcProductWithDetailsForCategoryReader(currentIncreaseForCategory.getCategory());
	  openSecondaryDrivenDelegateReader();
  }
 
  
  /*
   Algorithme principal:
      tant que  currentIncreaseForCategory=increaseForCategoryCsvMainDelegateReader.read() n'est pas null
           tant que currentProductWithDetails=productWithDetailsDbDrivenDelegateReader.read(); n'est pas null
              créer et retourner 
                productToUpdateWrapper = 
				new ProductToUpdate(this.currentProductWithDetails, 
				                    this.currentIncreaseForCategory.getIncreaseRatePct());
	 si tout est parcouru , retourner null
	 OUI MAIS, le declencheur de l'avancement de la sous-boucle ou de la boucle principale
	 est un nouvel appel à la méthode .read()
   */
  
  
  //private internal method for subLoop:
  private ProductToUpdate readNextProductToUpdateForCurrentIncreaseForCategory()throws Exception {
	  ProductToUpdate productToUpdateWrapper = null;
	  ProductWithDetails currentProductWithDetails=productWithDetailsDbDrivenDelegateReader.read();
	  logger.debug("CategoryDrivenProductToIncreaseReader.readNextProductToUpdateForCurrentIncreaseForCategory() called with currentProductWithDetails="+currentProductWithDetails);
	  if(currentProductWithDetails!=null) {
		  productToUpdateWrapper = 
					new ProductToUpdate(currentProductWithDetails, 
					                    this.currentIncreaseForCategory.getIncreaseRatePct());
	  }
	  return productToUpdateWrapper;//may be null if end of subLoop
  }
  
  //private internal method for mainLoop
  private ProductToUpdate readNextProductToUpdate()throws Exception {
	  //new iteration of subloop on productWithDetailsDbDrivenDelegateReader:
	  ProductToUpdate productToUpdateWrapper = readNextProductToUpdateForCurrentIncreaseForCategory();
	  if(productToUpdateWrapper!=null) {
		    //subloop not finished , return result
			return productToUpdateWrapper;
	     }else {
	    	 //subloop finished , iterate main loop:
	    	 readNewIncreaseForCategoryViaMainDelegateReader();
	    	 if(this.currentIncreaseForCategory==null) {
	    		    //main loop finished , all is finished , returning null
	    			return null;
	    	 }else {
	    		 //new iteration of subloop on productWithDetailsDbDrivenDelegateReader:
	    		 productToUpdateWrapper = readNextProductToUpdateForCurrentIncreaseForCategory();
	    		 return productToUpdateWrapper; //null or not
	    	 }
	     }
  }
  
  @Override
  public ProductToUpdate read()
		throws Exception {
	  logger.debug("CategoryDrivenProductToIncreaseReader.read() called");
	  if(this.currentIncreaseForCategory==null) {
		  //main loop not already started in previous call
		  //first iteration of main loop:
		  readNewIncreaseForCategoryViaMainDelegateReader();
		  if(this.currentIncreaseForCategory==null) 
				return null; //already end of main empty loop
	  }else {
		  //main loop already started in previous call
	  }
	  return readNextProductToUpdate();
  }
  
  private void openSecondaryDrivenDelegateReader() { 
	  productWithDetailsDbDrivenDelegateReader.open(this.stepExecution.getExecutionContext());
  }
  
  private void closeSecondaryDrivenDelegateReader() {
	  productWithDetailsDbDrivenDelegateReader.close();
  }
  
  @BeforeStep
  public void beforeStep(StepExecution stepExecution) {
	  this.stepExecution=stepExecution;
	  //open main delegate reader
	  increaseForCategoryCsvMainDelegateReader.open(stepExecution.getExecutionContext());
	  //other initialisations:
	  //openSecondaryDrivenDelegateReader();
	  this.currentIncreaseForCategory=null;
	  logger.debug("CategoryDrivenProductToIncreaseReader.beforeStep() called");
  }
  
  @AfterStep
  public void afterStep(StepExecution stepExecution) {
	  //close main delegate reader
	  increaseForCategoryCsvMainDelegateReader.close();
	 //other re-initialisations:
	  closeSecondaryDrivenDelegateReader();
	  this.currentIncreaseForCategory=null;
	  logger.debug("CategoryDrivenProductToIncreaseReader.afterStep() called");
  }
  
  //code for secondary/driven subReader:
  
  private static final String SELECT_CLAUSE = "SELECT id,main_category,sub_category,label,price,time_stamp,f_color,f_weight,f_size,f_description";
  
  
  JdbcPagingItemReader<ProductWithDetails> buildJdbcProductWithDetailsForCategoryReader(
		  String category  ) throws Exception{
		 
		 SqlPagingQueryProviderFactoryBean pagingQueryProviderFactory = new SqlPagingQueryProviderFactoryBean();
			pagingQueryProviderFactory.setDataSource(productdbDataSource);
			pagingQueryProviderFactory.setSelectClause(SELECT_CLAUSE);
			pagingQueryProviderFactory.setFromClause("FROM product_with_details");
			pagingQueryProviderFactory.setWhereClause("WHERE main_category = :category");
			pagingQueryProviderFactory.setSortKey("id");
			PagingQueryProvider pagingQueryProvider = pagingQueryProviderFactory.getObject();
			logger.debug("buildJdbcProductWithDetailsForCategoryReader , category="+category);
			Map<String, Object> parameterValues = new HashMap<>();
			parameterValues.put("category", category);

			return new JdbcPagingItemReaderBuilder<ProductWithDetails>()
					.name("jdbcProductWithDetailsForCategoryReader")
					.dataSource(this.productdbDataSource)
					.queryProvider(pagingQueryProvider)
					.parameterValues(parameterValues).pageSize(5)
					.rowMapper(new ProductWithDetailsRowMapper()).build();
	}
}