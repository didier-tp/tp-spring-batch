package tp.myBatchAdmin.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tp.myBatchAdmin.batch.BatchEssential;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j
public class TestBatchEssentialService {

    @Autowired
    BatchEssentialService batchEssentialService;

    @Test
    public void testLaunchBatchEssential(){
        assertNotNull(batchEssentialService);
        //batchEssentialService.launchBatchEssential("helloWorld");
        //batchEssentialService.launchBatchEssential("fromCsvToJson");
        batchEssentialService.launchBatchEssential("fromCsvToXml");
    }
}
