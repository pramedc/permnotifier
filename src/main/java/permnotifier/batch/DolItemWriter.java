package permnotifier.batch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.solr.common.SolrInputDocument;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import permnotifier.domain.PermRecord;
import permnotifier.domain.WorkInformation;
import permnotifier.repositories.DolItemRepository;
import permnotifier.search.SolrService;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

public class DolItemWriter implements ItemWriter<PermRecord> {

	private static final class WorkInformationFunction<T extends WorkInformation> implements
			Function<T, SolrInputDocument> {
		@Override
		public SolrInputDocument apply(T input) {
			SolrInputDocument document = new SolrInputDocument();
			document.addField("employer_s", input.getEmployer());
			document.addField("city_s", input.getCity());
			document.addField("state_s", input.getState());
			document.addField("jobPostingDate_dt", input.getJobPostDate());
			document.addField("jobTitle_s", input.getJobTitle());
			document.addField("yearlySalary_c", input.getYearlySalary());
			document.addField("monthlySalary_c", input.getMonthlySalary());
			document.addField("biweeklySalary_c", input.getBiWeeklySalary());
			document.addField("weeklySalary_c", input.getWeeklySalary());
			document.addField("hourlySalary_c", input.getHourlySalary());
			document.addField("jobLevel_s", input.getJobLevel());
			return document;
		}
	}

	@Autowired
	DolItemRepository dolItemRepository;
	
	@Autowired SolrService solrService;
	
	@Override
	public void write(List<? extends PermRecord> items) throws Exception {
		Collection<PermRecord> nonNullList = Lists.newArrayList(Collections2.filter(items, Predicates.notNull()));
		List<PermRecord> permRecords = new ArrayList<>();
		try {
			for (PermRecord permRecord : nonNullList) {
				PermRecord existing = dolItemRepository.findByCaseNumber(permRecord.getCaseNumber());
				if(existing == null) {
					dolItemRepository.save(permRecord);
					permRecords.add(permRecord);
				}
			}
		}
		catch(Exception e2) {
			// ignore
		}
		
		solrService.indexItems(permRecords, new WorkInformationFunction<PermRecord>());
	}

}
