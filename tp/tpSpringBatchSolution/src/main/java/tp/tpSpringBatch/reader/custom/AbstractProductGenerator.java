package tp.tpSpringBatch.reader.custom;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractProductGenerator {
	
	protected long dataSetSize=100; //ou 10000 ou autre
	protected long index=0;
	
	//most frequent sizes list 
	protected List<String> sizeList = Arrays.asList("xs" , "s" , "m" , "l" , "xl");
	
	//most frequent colors list
	protected List<String> colorList = Arrays.asList("blanc" , "noir" , "rouge" , "vert" , "bleu" , "orange" , "jaune" , "gris" );
	
	//most frequent functions list
	protected List<String> mainCategoryList = Arrays.asList("aliment" ,"vetement" , "divers" );
	
	public AbstractProductGenerator() {
		super();
	}

	public AbstractProductGenerator(long dataSetSize) {
		super();
		this.dataSetSize = dataSetSize;
	}
	
	public long getDataSetSize() {
		return dataSetSize;
	}

	public void setDataSetSize(long dataSetSize) {
		this.dataSetSize = dataSetSize;
	}

}
