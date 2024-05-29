package tp.tpSpringBatch.reader.java;

import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;

import tp.tpSpringBatch.model.ProductFeatures;
import tp.tpSpringBatch.model.ProductWithDetails;

public class ProductWithDetailsLineMapper implements LineMapper<ProductWithDetails>{

	@Override
	public ProductWithDetails mapLine(String line, int lineNumber) throws Exception {
		var lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(";");
		lineTokenizer.setNames("id","main_category", "sub_category" ,"label", "price", "time_stamp" , "features");
		FieldSet mainFieldSet = lineTokenizer.tokenize(line);
		
		var featuresSubLineTokenizer = new DelimitedLineTokenizer();
		featuresSubLineTokenizer.setDelimiter(",");
		featuresSubLineTokenizer.setNames("color","weight", "size", "description" );
		BeanWrapperFieldSetMapper<ProductFeatures> featuresFieldSetMapper =new BeanWrapperFieldSetMapper<ProductFeatures>();
		featuresFieldSetMapper.setTargetType(ProductFeatures.class);
		FieldSet featuresSubFieldSet = featuresSubLineTokenizer.tokenize(mainFieldSet.readString("features"));
		ProductFeatures features = featuresFieldSetMapper.mapFieldSet(featuresSubFieldSet);
		
		ProductWithDetails p = new ProductWithDetails();
		p.setFeatures(features);
		String sId= mainFieldSet.readString("id");
		//Long longId = mainFieldSet.readLong("id");
		//p.setId((longId!=null)?(int)longId.longValue():null);
		p.setId((sId!=null && !sId.equals("null"))?Integer.parseInt(sId):null);
		p.setMain_category(mainFieldSet.readString("main_category"));
		p.setSub_category(mainFieldSet.readString("sub_category"));
		p.setLabel(mainFieldSet.readString("label"));
		p.setPrice(mainFieldSet.readDouble("price"));
		p.setTime_stamp(mainFieldSet.readString("time_stamp"));
		return p;
	}

}
