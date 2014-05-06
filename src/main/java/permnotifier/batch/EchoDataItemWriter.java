package permnotifier.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import permnotifier.domain.DolItem;

public class EchoDataItemWriter implements ItemWriter<DolItem> {

	@Override
	public void write(List<? extends DolItem> items) throws Exception {
		for(int i = 0; i < items.size(); i++) {
			System.out.println(i + ": " + items.get(i));
		}
	}

	
	
}
