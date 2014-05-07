package permnotifier.batch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import permnotifier.batch.strategy.DOLRecordSavingStrategy;
import permnotifier.domain.AbstractModel;
import permnotifier.domain.WorkInformation;
import permnotifier.search.SolrService;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

public class DolItemWriter<T extends AbstractModel & WorkInformation> implements ItemWriter<T> {

	DOLRecordSavingStrategy<T> recordSavingStrategy;
	
	@Autowired SolrService solrService;
	
	@Override
	public void write(List<? extends T> items) throws Exception {
		Collection<T> nonNullList = Lists.newArrayList(Collections2.filter(items, Predicates.notNull()));
		List<T> permRecords = new ArrayList<>();
		try {
			for (T obj : nonNullList) {
				if(recordSavingStrategy.canSave(obj)) {
					recordSavingStrategy.save(obj);
					permRecords.add(obj);
				}
			}
		}
		catch(Exception e2) {
			// ignore
		}
		
		solrService.indexItems(permRecords, WorkInformationConverter.<T>newInstance());
	}

	public void setRecordSavingStrategy(
			DOLRecordSavingStrategy<T> recordSavingStrategy) {
		this.recordSavingStrategy = recordSavingStrategy;
	}
}
