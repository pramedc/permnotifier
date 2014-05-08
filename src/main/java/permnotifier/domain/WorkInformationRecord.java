package permnotifier.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.beans.Field;

public class WorkInformationRecord {

	@Field
	private String id;
	
	@Field("employer_s")
	private String employer;
	
	@Field("city_s")
	private String city;
	
	@Field("state_s")
	private String state;
	
	@Field("jobTitle_s")
	private String jobTitle;
	
	@Field("jobLevel_s")
	private String jobLevel;
	
	@Field("jobPostDate_dt")
	private Date jobPostDate;
	
//	@Field("jobPostMonth_s")
	private String jobPostMonth;
	
//	@Field("jobPostYear_s")
	private String jobPostYear;
	
	private BigDecimal yearlySalary;
	
	private BigDecimal monthlySalary;
	
	private BigDecimal biWeeklySalary;
	
	private BigDecimal weeklySalary;
	
	private BigDecimal hourlySalary;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
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

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}

	public Date getJobPostDate() {
		return jobPostDate;
	}

	public void setJobPostDate(Date jobPostDate) {
		this.jobPostDate = jobPostDate;
	}

	public String getJobPostMonth() {
		return jobPostMonth;
	}

	public void setJobPostMonth(String jobPostMonth) {
		this.jobPostMonth = jobPostMonth;
	}

	public String getJobPostYear() {
		return jobPostYear;
	}

	public void setJobPostYear(String jobPostYear) {
		this.jobPostYear = jobPostYear;
	}

	public BigDecimal getYearlySalary() {
		return yearlySalary;
	}
	
	@Field("yearlySalary_c")
	public void setYearlySalary(String yearlySalary) {
		this.yearlySalary = this.getAmount(yearlySalary);
	}

	public BigDecimal getMonthlySalary() {
		return monthlySalary;
	}

	@Field("monthlySalary_c")
	public void setMonthlySalary(String monthlySalary) {
		this.monthlySalary = this.getAmount(monthlySalary);
	}

	public BigDecimal getBiWeeklySalary() {
		return biWeeklySalary;
	}

	@Field("biweeklySalary_c")
	public void setBiWeeklySalary(String biWeeklySalary) {
		this.biWeeklySalary = this.getAmount(biWeeklySalary);
	}

	public BigDecimal getWeeklySalary() {
		return weeklySalary;
	}

	@Field("weeklySalary_c")
	public void setWeeklySalary(String weeklySalary) {
		this.weeklySalary = this.getAmount(weeklySalary);
	}

	public BigDecimal getHourlySalary() {
		return hourlySalary;
	}

	@Field("hourlySalary_c")
	public void setHourlySalary(String hourlySalary) {
		this.hourlySalary = this.getAmount(hourlySalary);
	}

	private BigDecimal getAmount(String val) {
		String[] split = StringUtils.split(val, ",", 2);
		return new BigDecimal(split[0]);
	}

}
