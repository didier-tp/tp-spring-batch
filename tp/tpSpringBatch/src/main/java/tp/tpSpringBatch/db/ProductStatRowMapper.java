package tp.tpSpringBatch.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import tp.tpSpringBatch.model.ProductStat;

public class ProductStatRowMapper implements RowMapper<ProductStat> {

    public static final String CATEGORY_COLUMN_ALIAS = "category";
    public static final String NB_PROD_COLUMN_ALIAS = "nb_prod";
    public static final String MIN_PRICE_COLUMN_ALIAS = "min_price";
    public static final String MAX_PRICE_COLUMN_ALIAS = "max_price";
    public static final String AVG_PRICE_COLUMN_ALIAS = "avg_price";


    public ProductStat mapRow(ResultSet rs, int rowNum) throws SQLException {
    	ProductStat s = new ProductStat();
        s.setNumber_of_products(rs.getInt(NB_PROD_COLUMN_ALIAS));
        s.setCategory(rs.getString(CATEGORY_COLUMN_ALIAS));
        s.setMin_price(rs.getDouble(MIN_PRICE_COLUMN_ALIAS));
        s.setMax_price(rs.getDouble(MAX_PRICE_COLUMN_ALIAS));
        s.setAvg_price(rs.getDouble(AVG_PRICE_COLUMN_ALIAS)); 
        return s;
    }
    
    /*
     pour récupérer le résultat de cette requête SQL:
     --------
     SELECT main_category as category , count(*) as nb_prod , min(price) as min_price ,
			 max(price) as max_price , avg(price) as avg_price    
			 FROM PRODUCT_WITH_DETAILS GROUP BY main_category
     */
}
