package tp.tpSpringBatch.writer.custom;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

public class SimpleObjectWriter<T extends Object> implements ItemWriter<T>{
	
	
	public SimpleObjectWriter() {
	}
	
	@Override
	public void write(Chunk<? extends T> chunk) throws Exception {
		for(T obj : chunk) {
			System.out.println(obj.toString());
		}
	}

	
}
