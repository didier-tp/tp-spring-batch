package tp.jtaSpringBatch.tasklet;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
public class PrintMessageTasklet implements Tasklet{
	
	private String message="hello world";//by default
	
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		System.out.println(message);
		return RepeatStatus.FINISHED;
	}
	
	public PrintMessageTasklet(String message) {
		this.message = message;
	}


}
