package permnotifier.controllers.api.params;

import org.hibernate.validator.constraints.NotBlank;

public class SearchParameters {

	@NotBlank(message = "search term must not be blank!")
	private String searchTerm;
	
	private String city;
	private String state;

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
