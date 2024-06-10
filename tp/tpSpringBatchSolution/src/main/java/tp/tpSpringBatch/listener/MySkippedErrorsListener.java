package tp.tpSpringBatch.listener;

import org.springframework.batch.core.SkipListener;

//import org.springframework.batch.item.ItemReaderException;
import tp.tpSpringBatch.model.ProductWithDetails;

//NB: org.springframework.batch.item.file.FlatFileParseException hérite de ItemReaderException
//ItemReaderException hérite de RuntimeException
//NB: ce listener est à enregistrer au niveau chunk ou tasklet ou step (mais pas job)
//NB: SkipListener hérite de StepListener
public class MySkippedErrorsListener implements SkipListener<ProductWithDetails, ProductWithDetails> {
	@Override
	public void onSkipInRead(Throwable t) {
		System.err.println("SKIPPED_READ_ERROR:" + t.getMessage());
		SkipListener.super.onSkipInRead(t);
	}

	/*
	@Override
	public void onSkipInWrite(ProductWithDetails item, Throwable t) {
		System.err.println("SKIPPED_WRITE_ERROR:" + t.getMessage());
		SkipListener.super.onSkipInWrite(item, t);
	}

	@Override
	public void onSkipInProcess(ProductWithDetails item, Throwable t) {
		System.err.println("SKIPPED_PROCESS_ERROR:" + t.getMessage());
		SkipListener.super.onSkipInProcess(item, t);
	}
	*/
}