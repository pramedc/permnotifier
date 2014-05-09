package permnotifier.controllers.api;
import javax.validation.Valid;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import permnotifier.controllers.api.params.SearchParameters;
import permnotifier.controllers.api.result.SearchResult;
import permnotifier.search.WorkInformationSearchService;

@RequestMapping("/api/search")
@RestController
public class SearchController {

	@Autowired
	WorkInformationSearchService service;
	
    @RequestMapping(value="/workinformation", method = RequestMethod.POST)
    public SearchResult searchWorkInformation(@Valid @RequestBody SearchParameters parameters, Errors errors, Pageable pageable) throws Exception {
    	if(pageable == null) {
    		System.out.println("pageable is null");
    		pageable = new PageRequest(0, 10);
    	}
    	
    	long startTime = System.currentTimeMillis();
    	QueryResponse response = service.search(parameters, pageable);
    	final SearchResult result = SearchResult.create(response);
		result.setMs(System.currentTimeMillis() - startTime);
    	return result;
    }
}
