package tp.tpSpringBatch.writer.helper;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;
//helper class for xml config of csvWriter with header callback
public class MyFlatFileHeaderCallback implements FlatFileHeaderCallback{
	
	private String headerString;
	

	public String getHeaderString() {
		return headerString;
	}

	public void setHeaderString(String headerString) {
		this.headerString = headerString;
	}

	@Override
	public void writeHeader(Writer writer) throws IOException {
		writer.write(this.headerString);
	}

}
