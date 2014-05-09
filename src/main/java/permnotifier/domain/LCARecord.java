package permnotifier.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;

import permnotifier.helpers.DateHelper;
import permnotifier.helpers.SalaryCalculator;

@Entity
@Table(name = "lca_records")
public class LCARecord extends AbstractModel implements WorkInformation {

	private static final long serialVersionUID = 7841191843078456387L;

	@Column(name = "case_number", unique = true)
	private String caseNumber;

	private String status;

	@Temporal(TemporalType.DATE)
	private Date caseSubmision;

	@Temporal(TemporalType.DATE)
	private Date caseDecision;
	
	@Temporal(TemporalType.DATE)
	private Date employmentStart;
	
	private String employer;

	private String employerCity;

	private String employerState;

	private String workLocationCity;

	private String workLocationState;

	private String jobTitle;

	private BigDecimal offerLow;

	private BigDecimal offerHigh;

	private String offerSalaryType;

	public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCaseSubmision() {
		return caseSubmision;
	}

	public void setCaseSubmision(Date caseSubmision) {
		this.caseSubmision = caseSubmision;
	}

	public Date getCaseDecision() {
		return caseDecision;
	}

	public void setCaseDecision(Date caseDecision) {
		this.caseDecision = caseDecision;
	}

	public Date getEmploymentStart() {
		return employmentStart;
	}

	public void setEmploymentStart(Date employmentStart) {
		this.employmentStart = employmentStart;
	}

	public String getEmployer() {
		return StringUtils.upperCase(employer);
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public String getEmployerCity() {
		return StringUtils.upperCase(employerCity);
	}

	public void setEmployerCity(String employerCity) {
		this.employerCity = employerCity;
	}

	public String getEmployerState() {
		USState usState = USState.getState(employerState);
		return usState != null ? usState.getStateName() : null;
	}

	public void setEmployerState(String employerState) {
		this.employerState = employerState;
	}

	public String getWorkLocationCity() {
		return StringUtils.upperCase(workLocationCity);
	}

	public void setWorkLocationCity(String workLocationCity) {
		this.workLocationCity = workLocationCity;
	}

	public String getWorkLocationState() {
		USState usState = USState.getState(workLocationState);
		return usState != null ? usState.getStateName() : null;
	}

	public void setWorkLocationState(String workLocationState) {
		this.workLocationState = workLocationState;
	}

	public String getJobTitle() {
		return StringUtils.upperCase(StringUtils.trimToNull(jobTitle));
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
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

	public String getOfferSalaryType() {
		return offerSalaryType;
	}

	public void setOfferSalaryType(String offerSalaryType) {
		this.offerSalaryType = offerSalaryType;
	}

	/**
	 * possible offer is always the highest
	 * 
	 * @return
	 */
	public BigDecimal getPossibleOffer() {
		return offerHigh != null ? offerHigh : offerLow;
	}
	
	public SalaryType getSalaryType() {
		return SalaryType.getSalaryType(offerSalaryType);
	}
	
	@Override
	public String getCity() {
		String city = getWorkLocationCity();
		return StringUtils.isNotBlank(city) ? city : getEmployerCity();
	}

	@Override
	public String getState() {
		String state = getWorkLocationState();
		return StringUtils.isNotBlank(state) ? state : getEmployerState();
	}

	@Override
	public String getJobLevel() {
		return null;
	}

	@Override
	public Date getJobPostDate() {
		return caseDecision != null ? caseDecision : caseSubmision;
	}

	@Override
	public String getJobPostMonth() {
		return DateHelper.getMonth(getJobPostDate());
	}
	
	@Override
	public String getJobPostYear() {
		return DateHelper.getYear(getJobPostDate());
	}
	
	@Override
	public BigDecimal getYearlySalary() {
		return isValid() ? SalaryCalculator.getYearlySalary(getSalaryType(), getPossibleOffer()) : null;
	}

	@Override
	public BigDecimal getMonthlySalary() {
		return isValid() ? SalaryCalculator.getMonthlySalary(getSalaryType(), getPossibleOffer()) : null;
	}

	@Override
	public BigDecimal getWeeklySalary() {
		return isValid() ? SalaryCalculator.getWeeklySalary(getSalaryType(), getPossibleOffer()) : null;
	}

	@Override
	public BigDecimal getBiWeeklySalary() {
		return isValid() ? SalaryCalculator.getBiWeeklySalary(getSalaryType(), getPossibleOffer()) : null;
	}

	@Override
	public BigDecimal getHourlySalary() {
		return isValid() ? SalaryCalculator.getHourlySalary(getSalaryType(), getPossibleOffer()) : null;
	}

	@Override
	public boolean isValid() {
		return getPossibleOffer() != null && getSalaryType() != null 
				&& getCity() != null && getState() != null && getEmployer() != null
				&& getJobTitle() != null;
	}
	
	@Override
	public String getType() {
		return "LCA";
	}
	
	@Override
	public String getTypeIdentifier() {
		return caseNumber;
	}

}
