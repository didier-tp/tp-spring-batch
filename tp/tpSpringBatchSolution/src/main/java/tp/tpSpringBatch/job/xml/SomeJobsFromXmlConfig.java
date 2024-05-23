package tp.tpSpringBatch.job.xml;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("xmlJobConfig")
@ImportResource({"classpath:job/globalCommonConfig.xml",
	             "classpath:job/myHelloWorldJob.xml",
	             "classpath:job/fromCsvToConsoleJob.xml",
	             "classpath:job/fromCsvToJsonJob.xml"})
public class SomeJobsFromXmlConfig {
}