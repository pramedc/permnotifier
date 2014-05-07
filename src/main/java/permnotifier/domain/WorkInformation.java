package permnotifier.domain;

import java.math.BigDecimal;
import java.util.Date;

public interface WorkInformation {

	Long getId();
	
	String getEmployer();
	
	String getCity();
	
	String getState();
	
	String getJobTitle();
	
	String getJobLevel();
	
	Date getJobPostDate();
	
	BigDecimal getYearlySalary();
	
	BigDecimal getMonthlySalary();
	
	BigDecimal getBiWeeklySalary();
	
	BigDecimal getWeeklySalary();
	
	BigDecimal getHourlySalary();
	
	boolean isValid();
}
