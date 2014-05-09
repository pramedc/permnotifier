package permnotifier.batch;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
		Set<T> permRecords = new TreeSet<>(recordSavingStrategy.getEqualityComparator());
		try {
			for (T obj : nonNullList) {
//				System.out.println(obj.isValid() + "=" + obj);
				if(!permRecords.contains(obj) && obj.isValid() && recordSavingStrategy.canSave(obj)) {
//					recordSavingStrategy.save(obj);
					permRecords.add(obj);
				}
			}
			recordSavingStrategy.saveAll(permRecords);
		}
		catch(Exception e2) {
			// ignore
		}
		
//		System.out.println(permRecords);
		solrService.indexItems(permRecords, WorkInformationConverter.<T>newInstance());
	}

	public void setRecordSavingStrategy(
			DOLRecordSavingStrategy<T> recordSavingStrategy) {
		this.recordSavingStrategy = recordSavingStrategy;
	}
}
