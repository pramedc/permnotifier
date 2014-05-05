package permnotifier.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import permnotifier.controllers.params.SubscriptionData;
import permnotifier.domain.PermSubscription;
import permnotifier.repositories.PermSubscriptionRepository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SubscriptionControllerTest {

	@Mock
    private PermSubscriptionRepository permSubscriptionRepository;
 
    @InjectMocks
    private SubscriptionController subscriptionController;
 
	Gson gson = new GsonBuilder().create();
    
    private MockMvc mockMvc;
	
    @Before
    public void setUp() {
    	MockitoAnnotations.initMocks(this);
    	
    	mockMvc = MockMvcBuilders.standaloneSetup(subscriptionController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }
    
	@Test
	public void should_accept_valid_data() throws Exception {
		
		
		SubscriptionData data = new SubscriptionData();
		data.setCaseNumber("CASE123");
		data.setEmail("shuffler16@gmail.com");
		
		
		MvcResult result = mockMvc.perform(post("/subscription")
				.contentType(MediaType.APPLICATION_JSON)
		        .accept(MediaType.APPLICATION_JSON)
		        .content(gson.toJson(data)))
				.andExpect(status().isOk()).andReturn();
		
		assertThat("true", equalTo(result.getResponse().getContentAsString()));
		verify(permSubscriptionRepository).save(Mockito.any(PermSubscription.class));
	}
	
	@Test
	public void should_fail_invalid_data() throws Exception {
		
		
		SubscriptionData data = new SubscriptionData();
		data.setCaseNumber("CASE123");
		data.setEmail("wrongemail");
		
		
		MvcResult result = mockMvc.perform(post("/subscription")
				.contentType(MediaType.APPLICATION_JSON)
		        .accept(MediaType.APPLICATION_JSON)
		        .content(gson.toJson(data)))
				.andExpect(status().isBadRequest()).andReturn();
		
		assertThat("false", equalTo(result.getResponse().getContentAsString()));
		verify(permSubscriptionRepository, never()).save(Mockito.any(PermSubscription.class));
	}

	@Test
	public void should_fail_null_data() throws Exception {
		
		mockMvc.perform(post("/subscription")
				.contentType(MediaType.APPLICATION_JSON)
		        .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn();
		
	}
	
	@Test
	public void should_fail_service_failure() throws Exception {
		
		when(permSubscriptionRepository.save(Mockito.any(PermSubscription.class)))
			.thenThrow(new IllegalArgumentException());
		
		SubscriptionData data = new SubscriptionData();
		data.setCaseNumber("CASE123");
		data.setEmail("shuffler16@gmail.com");
		
		MvcResult result = mockMvc.perform(post("/subscription")
				.contentType(MediaType.APPLICATION_JSON)
		        .accept(MediaType.APPLICATION_JSON)
		        .content(gson.toJson(data)))
				.andExpect(status().isBadRequest()).andReturn();
		
		assertThat("false", equalTo(result.getResponse().getContentAsString()));
		verify(permSubscriptionRepository).save(Mockito.any(PermSubscription.class));
	}
}
