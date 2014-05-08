package permnotifier.controllers.api;
import javax.validation.Valid;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
    public SearchResult searchWorkInformation(@Valid @RequestBody SearchParameters parameters, Errors errors) throws Exception {
    	QueryResponse response = service.search(parameters, null);
    	return SearchResult.create(response);
    }
}
