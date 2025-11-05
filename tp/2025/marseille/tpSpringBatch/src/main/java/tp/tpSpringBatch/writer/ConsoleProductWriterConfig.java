package tp.tpSpringBatch.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tp.tpSpringBatch.model.Product;
import tp.tpSpringBatch.writer.custom.SimpleObjectWriter;

@Configuration
public class ConsoleProductWriterConfig {

    @Bean @Qualifier("console")
    ItemWriter<Product> consoleProductWriter(){
        return new SimpleObjectWriter<Product>();
    }

}
