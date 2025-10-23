package tp.basesSpringBatch.partitionner;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

public class NumericColumnRangePartitioner implements Partitioner {
	
	private static Logger logger = LoggerFactory.getLogger(NumericColumnRangePartitioner.class);
	
	private JdbcOperations jdbcTemplate;
	private String table;
	private String column;//numeric_Column name
	 
	  public void setTable(String table) {
	    this.table = table;
	  }
	 
	  public void setColumn(String column) {
	    this.column = column;
	  }
	 
	  public void setDataSource(DataSource dataSource) {
	    jdbcTemplate = new JdbcTemplate(dataSource);
	  }
	 
	 
	public NumericColumnRangePartitioner(String table,String column) {
		super();
		this.table=table;
		this.column=column;
	}
	
	
	public NumericColumnRangePartitioner() {
		super();
	}


	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		//NB: grid size will be the number of threads (in //)
		
		int min = jdbcTemplate.queryForObject("SELECT MIN(" + column + ") FROM " + table, Integer.class);
	    int max = jdbcTemplate.queryForObject("SELECT MAX(" + column + ") FROM " + table, Integer.class);
	 
	    int range =  (int) Math.ceil(((max - min)+1) / gridSize );
	    logger.debug("NumericColumnRangePartitioner min=" + min + " max=" + max + " range=" + range);
		Map<String, ExecutionContext> partitionMap = new HashMap<String, ExecutionContext>();

		int from = min;
		int to = min+(range -1); 

		for (int i = 1; i <= gridSize; i++) {
			//NB: a springBatch ExcecutionContext is a sort of Map of any key/value
			//that will be used by a executionThread
			ExecutionContext partitionExecutionContext = new ExecutionContext();
			
			partitionExecutionContext.putString("name", "partition_" + i);
			partitionExecutionContext.putInt("from", from);//first column value in table to be managed by this thread/partition
			partitionExecutionContext.putInt("to", to);//last column value in table to be managed by this thread/partition
			logger.debug(partitionExecutionContext.getString("name") + " managed by a specific thread" 
			+ " will be use to manage data whith value between " + partitionExecutionContext.getInt("from")
            + "and " + partitionExecutionContext.getInt("to"));

			partitionMap.put(partitionExecutionContext.getString("name"), partitionExecutionContext);

			from = to + 1;
			to += range;
		}

		return partitionMap;
	}
	
	

}
