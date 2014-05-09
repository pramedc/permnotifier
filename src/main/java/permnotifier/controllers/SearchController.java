package permnotifier.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("WebSearchController")
@RequestMapping("/search/**")
public class SearchController {
	
	@RequestMapping
	public String index() {
		return "search/index";
	}
	
}
