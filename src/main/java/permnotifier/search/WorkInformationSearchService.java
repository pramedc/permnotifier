package permnotifier.search;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import permnotifier.controllers.api.params.SearchParameters;

@Service
public class WorkInformationSearchService  {

	@Autowired
	SolrServer solrServer;
	
	public QueryResponse search(SearchParameters parameters, Pageable pageable) throws Exception {
		
		SolrQuery query = new SolrQuery();
		
		if(StringUtils.isNotBlank(parameters.getSearchTerm())) {
			query.setQuery("searchField_s:*" + StringUtils.upperCase(parameters.getSearchTerm()) + "*");
		}
		else {
			query.setQuery("*:*");
		}
		query.setFacet(true);
		query.setFacetMinCount(1);
		query.setRows(pageable.getPageSize());
		query.setStart(pageable.getOffset());
		
		// remove questionable salaries, more than a million
		query.addFilterQuery("questionable_b:false");
		
		if(StringUtils.isNotBlank(parameters.getState())) {
			query.addFilterQuery("state_s:\"" + parameters.getState() + "\"");
			query.addFacetField("employer_s", "city_s");
		}
		else {
			query.addFacetField("employer_s", "state_s");
		}
		
		if(StringUtils.isNotBlank(parameters.getCity())) {
			query.addFilterQuery("city_s:\"" + parameters.getCity() + "\"");
		}
		
		if(StringUtils.isNotBlank(parameters.getEmployer())) {
			query.addFilterQuery("employer_s:\"" + parameters.getEmployer() + "\"");
			query.addFacetField("jobTitle_s");
		}
		
		if(StringUtils.isNotBlank(parameters.getJobTitle())) {
			query.addFilterQuery("jobTitle_s:\"" + parameters.getJobTitle() + "\"");
		}
		
		if(StringUtils.isNotBlank(parameters.getYear())) {
			query.addFilterQuery("jobPostYear_s:" + parameters.getYear());
			query.addFacetField("jobPostMonth_s");
		}
		else {
			query.addFacetField("jobPostYear_s");
		}
		
		Sort sort = pageable.getSort();
		if(sort != null) {
			for (Order order : sort) {
				query.addSort(order.getProperty(), order.getDirection() == Direction.ASC ? ORDER.asc : ORDER.desc);
			}
		}
		
		return solrServer.query(query);
	}
	
}
