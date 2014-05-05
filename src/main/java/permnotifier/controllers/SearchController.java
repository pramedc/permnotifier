package permnotifier.controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/search/**")
@RestController
public class SearchController {

    @RequestMapping
    public String index() {
        return "search/index";
    }
}
