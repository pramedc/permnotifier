package permnotifier.controllers.api.result;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import permnotifier.domain.WorkInformationRecord;

public class SearchResult {

	private List<WorkInformationRecord> results;
	private long totalCount;
	private long start;
	private List<FacetList> facets = new ArrayList<FacetList>(); 
	
	// property to store time on how fast the query was executed
	private long ms;
	
	public static SearchResult create(QueryResponse response) {
		SearchResult result = new SearchResult();
		result.results = response.getBeans(WorkInformationRecord.class);
		
		SolrDocumentList responseDocument = (SolrDocumentList) response.getResponse().get("response");
		result.totalCount = responseDocument.getNumFound();
		result.start = responseDocument.getStart();
		
		List<FacetField> facetFields = response.getFacetFields();
		for (FacetField facetField : facetFields) {
			FacetList list = new FacetList();
			list.setName(facetField.getName());
			
			List<Count> values = facetField.getValues();
			for (Count count : values) {
				list.addFacet(count.getName(), count.getCount());
			}
			
			result.facets.add(list);
		}
		return result;
	}
	
	public long getMs() {
		return ms;
	}

	public void setMs(long ms) {
		this.ms = ms;
	}

	public List<WorkInformationRecord> getResults() {
		return results;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public long getStart() {
		return start;
	}

	public List<FacetList> getFacets() {
		return facets;
	}
	
}
