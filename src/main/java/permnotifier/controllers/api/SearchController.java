package permnotifier.controllers.api;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import permnotifier.controllers.api.params.SearchParameters;
import permnotifier.domain.WorkInformationRecord;
import permnotifier.search.WorkInformationSearchService;

@RequestMapping("/api/search")
@RestController
public class SearchController {

	@Autowired
	WorkInformationSearchService service;
	
    @RequestMapping(value="/workinformation", method = RequestMethod.POST)
    public String searchWorkInformation(@Valid @RequestBody SearchParameters parameters, Errors errors) throws Exception {
    	QueryResponse search = service.search(parameters, null);
//    	System.out.println(ToStringBuilder.reflectionToString(search));
    	
    	try {
    	List<WorkInformationRecord> beans = search.getBeans(WorkInformationRecord.class);
    	for (WorkInformationRecord workInformationRecord : beans) {
			System.out.println(ToStringBuilder.reflectionToString(workInformationRecord));
		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return "search/index";
    }
}
