package permnotifier.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class DolItem extends AbstractModel{

	private static final long serialVersionUID = 1151604782989545072L;

	@Column(name = "case_id", nullable = true)
	private int caseId;
	
	@Column(name = "case_number", unique = true)
	private String caseNumber;

	@Column(name = "case_type")
	private String caseType;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "employer")
	private String employer;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "job_posting_date")
	private Date jobPostingDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "work_start_date")
	private Date workStartDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "work_end_date")
	private Date workEndDate;
	
	@Column(name = "job_title")
	private String jobTitle;
	
	@Column(name = "state")
	private String state;

	@Column(name = "prevailing_wage")
	private BigDecimal prevailingWage;
	
	@Column(name = "occupation_title")
	private String occupationTitle;
	
	@Column(name = "occupation_level")
	private String occupationLevel;

	@Column(name = "city")
	private String city;
	
	@Column(name = "offer_low")
	private BigDecimal offerLow;
	
	@Column(name = "offer_high")
	private BigDecimal offerHigh;
	
	@Column(name = "field_of_study")
	private String fieldOfStudy;
	
	@Column(name = "country_of_origin")
	private String countryOfOrigin;
	
	@Column(name = "details_loaded")
	private boolean detailsLoaded = false;

	public int getCaseId() {
		return this.caseId;
	}

	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}

	public String getCaseNumber() {
		return this.caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public Date getJobPostingDate() {
		return jobPostingDate;
	}

	public void setJobPostingDate(Date jobPostingDate) {
		this.jobPostingDate = jobPostingDate;
	}

	public Date getWorkStartDate() {
		return workStartDate;
	}

	public void setWorkStartDate(Date workStartDate) {
		this.workStartDate = workStartDate;
	}

	public Date getWorkEndDate() {
		return workEndDate;
	}

	public void setWorkEndDate(Date workEndDate) {
		this.workEndDate = workEndDate;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public BigDecimal getPrevailingWage() {
		return prevailingWage;
	}

	public void setPrevailingWage(BigDecimal prevailingWage) {
		this.prevailingWage = prevailingWage;
	}

	public String getOccupationTitle() {
		return occupationTitle;
	}

	public void setOccupationTitle(String occupationTitle) {
		this.occupationTitle = occupationTitle;
	}

	public String getOccupationLevel() {
		return occupationLevel;
	}

	public void setOccupationLevel(String occupationLevel) {
		this.occupationLevel = occupationLevel;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public BigDecimal getOfferLow() {
		return offerLow;
	}

	public void setOfferLow(BigDecimal offerLow) {
		this.offerLow = offerLow;
	}

	public BigDecimal getOfferHigh() {
		return offerHigh;
	}

	public void setOfferHigh(BigDecimal offerHigh) {
		this.offerHigh = offerHigh;
	}

	public String getFieldOfStudy() {
		return fieldOfStudy;
	}

	public void setFieldOfStudy(String fieldOfStudy) {
		this.fieldOfStudy = fieldOfStudy;
	}

	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}

	public void setCountryOfOrigin(String countryOfOrigin) {
		this.countryOfOrigin = countryOfOrigin;
	}

	public boolean isDetailsLoaded() {
		return detailsLoaded;
	}

	public void setDetailsLoaded(boolean detailsLoaded) {
		this.detailsLoaded = detailsLoaded;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
