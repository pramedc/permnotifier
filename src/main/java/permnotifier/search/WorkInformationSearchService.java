package permnotifier.search;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import permnotifier.controllers.api.params.SearchParameters;

@Service
public class WorkInformationSearchService  {

	@Autowired
	SolrServer solrServer;
	
	public QueryResponse search(SearchParameters parameters, Pageable pageable) throws Exception {
		
		SolrQuery query = new SolrQuery();
		query.setQuery("jobTitle_s:*" + StringUtils.upperCase(parameters.getSearchTerm()) + "*");
		query.setFacet(true);
		query.setFacetMinCount(1);
		
		if(StringUtils.isNotBlank(parameters.getState())) {
			query.addFacetField("employer_s", "city_s");
		}
		else {
			query.addFacetField("employer_s", "state_s");
		}
		
		return solrServer.query(query);
	}
	
}
