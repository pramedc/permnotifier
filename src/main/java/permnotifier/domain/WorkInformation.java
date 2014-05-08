package permnotifier.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public interface WorkInformation extends Serializable {

	String getEmployer();
	
	String getCity();
	
	String getState();
	
	String getJobTitle();
	
	String getJobLevel();
	
	Date getJobPostDate();
	
	String getJobPostMonth();

	String getJobPostYear();

	BigDecimal getYearlySalary();
	
	BigDecimal getMonthlySalary();
	
	BigDecimal getBiWeeklySalary();
	
	BigDecimal getWeeklySalary();
	
	BigDecimal getHourlySalary();
	
	boolean isValid();
}
