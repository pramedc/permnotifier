package permnotifier.controllers;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import permnotifier.controllers.params.SubscriptionData;
import permnotifier.domain.PermSubscription;
import permnotifier.repositories.PermSubscriptionRepository;


@RequestMapping("/subscription")
@RestController
public class SubscriptionController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionController.class);
	
	@Autowired
	PermSubscriptionRepository permSubscriptionRepository;
	
	@RequestMapping(method = RequestMethod.POST)
	public boolean subscribeByCaseNumber(@Valid @RequestBody SubscriptionData subscriptionData, Errors errors, HttpServletResponse response) {
		if(!errors.hasErrors()) {
			PermSubscription entity = new PermSubscription();
			entity.setCaseNumber(subscriptionData.getCaseNumber());
			entity.setEmail(subscriptionData.getEmail());
			try {
				permSubscriptionRepository.save(entity);
				return true;
			}
			catch (Exception e) {
				LOGGER.error("Failed while saving permSubscriptionInstance", e);
			}
		}
		
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return false;
	}
	
	
}
