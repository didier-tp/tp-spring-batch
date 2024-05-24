package tp.tpSpringBatch.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import tp.tpSpringBatch.model.ProductFeatures;
import tp.tpSpringBatch.model.ProductWithDetails;

public class ProductWithDetailsRowMapper implements RowMapper<ProductWithDetails> {

	public static final String ID_COLUMN = "id";
    public static final String MAIN_CATEGORY_COLUMN = "main_category";
    public static final String SUB_CATEGORY_COLUMN = "sub_category";
    public static final String LABEL_COLUMN = "label";
    public static final String PRICE_COLUMN = "price";
    public static final String TIMESTAMP_COLUMN = "time_stamp";
    public static final String F_COLOR_COLUMN = "f_color";
    public static final String F_WEIGHT_COLUMN = "f_weight";
    public static final String F_SIZE_COLUMN = "f_size";
    public static final String F_DESCRIPTION_COLUMN = "f_description";

    public ProductWithDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
    	ProductWithDetails p = new ProductWithDetails();
        p.setId(rs.getInt(ID_COLUMN));
        p.setMain_category(rs.getString(MAIN_CATEGORY_COLUMN));
        p.setSub_category(rs.getString(SUB_CATEGORY_COLUMN));
        p.setPrice(rs.getDouble(PRICE_COLUMN));
        p.setTime_stamp(rs.getString(TIMESTAMP_COLUMN));
	        ProductFeatures f  = new ProductFeatures();
	        f.setColor(rs.getString(F_COLOR_COLUMN));
	        f.setWeight(rs.getDouble(F_WEIGHT_COLUMN));
	        f.setSize(rs.getString(F_SIZE_COLUMN));
	        f.setDescription(rs.getString(F_DESCRIPTION_COLUMN));
	        p.setFeatures(f);
        return p;
    }
}
