package tp.tpSpringBatch.writer.java;

import java.util.HashMap;

import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import tp.tpSpringBatch.model.ProductWithDetails;

public class MyProductWithDetailsParamProvider implements ItemSqlParameterSourceProvider<ProductWithDetails> {

	/*
	 For this request:
	 """
		  INSERT INTO product_with_details (main_category,sub_category,label,price,time_stamp,f_color,f_weight,f_size,f_description)
		  VALUES(:main_category,:sub_category,:label,:price,:time_stamp,:f_color,:f_weight,:f_size,:f_description)"""
	 */
	
	
	@Override
	public SqlParameterSource createSqlParameterSource(ProductWithDetails item) {
		return new MapSqlParameterSource(new HashMap<String, Object>() {
            {
                put("main_category", item.getMain_category());
                put("sub_category", item.getSub_category());
                put("label", item.getLabel());
                put("price", item.getPrice());
                put("time_stamp", item.getTime_stamp());
                put("f_color", item.getFeatures().getColor());
                put("f_weight", item.getFeatures().getWeight());
                put("f_size", item.getFeatures().getSize());
                put("f_description", item.getFeatures().getDescription());
            }
        });
	}

}
