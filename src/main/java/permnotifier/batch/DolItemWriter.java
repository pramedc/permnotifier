package permnotifier.batch;

import java.util.Collection;
import java.util.List;

import org.apache.solr.common.SolrInputDocument;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import permnotifier.domain.DolItem;
import permnotifier.repositories.DolItemRepository;
import permnotifier.search.SolrService;

public class DolItemWriter implements ItemWriter<DolItem> {

	private static final class FunctionImplementation implements
			Function<DolItem, SolrInputDocument> {
		@Override
		public SolrInputDocument apply(DolItem input) {
			SolrInputDocument document = new SolrInputDocument();
			document.addField("caseNumber_s", input.getCaseNumber());
			document.addField("employer_s", input.getEmployer());
			document.addField("city_s", input.getCity());
			document.addField("state_s", input.getState());
			document.addField("postingDate_dt", input.getJobPostingDate());
			document.addField("jobTitle_s", input.getJobTitle());
			document.addField("prevailingWage_c", input.getPrevailingWage());
			document.addField("offerLow_c", input.getOfferLow());
			document.addField("offerHigh_c", input.getOfferHigh());
			document.addField("countryOfOrigin_s", input.getCountryOfOrigin());
			return document;
		}
	}

	@Autowired
	DolItemRepository dolItemRepository;
	
	@Autowired SolrService solrService;
	
	@Override
	public void write(List<? extends DolItem> items) throws Exception {
		Collection<DolItem> nonNullList = Lists.newArrayList(Collections2.filter(items, Predicates.notNull()));
		
		// save first on database
		Iterable<DolItem> savedItems = dolItemRepository.save(nonNullList);
		
		// then index data
		solrService.indexItems(savedItems, new FunctionImplementation());
	}

}
