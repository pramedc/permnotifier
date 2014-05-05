package permnotifier.controllers.params;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class SubscriptionData {

	@NotBlank(message = "case number must not be blank!")
	private String caseNumber;

	@Email(message = "please provide a valid email")
	@NotBlank(message = "email must not be blank!")
	private String email;

	public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}