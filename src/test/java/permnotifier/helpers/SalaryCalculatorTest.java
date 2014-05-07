package permnotifier.helpers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

import permnotifier.domain.SalaryType;

public class SalaryCalculatorTest {

	@Test
	public void should_properly_convert_hourly_salary() {
		SalaryType baseSalaryType = SalaryType.HOURLY;
		BigDecimal baseSalary = new BigDecimal("50");
		
		assertThat(SalaryCalculator.getHourlySalary(baseSalaryType, baseSalary).toString(), equalTo("50"));
		assertThat(SalaryCalculator.getWeeklySalary(baseSalaryType, baseSalary).toString(), equalTo("2000"));
		assertThat(SalaryCalculator.getBiWeeklySalary(baseSalaryType, baseSalary).toString(), equalTo("4000"));
		assertThat(SalaryCalculator.getMonthlySalary(baseSalaryType, baseSalary).toString(), equalTo("8667"));
		assertThat(SalaryCalculator.getYearlySalary(baseSalaryType, baseSalary).toString(), equalTo("104000"));
	}
	
	@Test
	public void should_properly_convert_weekly_salary() {
		SalaryType baseSalaryType = SalaryType.WEEKLY;
		BigDecimal baseSalary = new BigDecimal("2000");
		
		assertThat(SalaryCalculator.getHourlySalary(baseSalaryType, baseSalary).toString(), equalTo("50"));
		assertThat(SalaryCalculator.getWeeklySalary(baseSalaryType, baseSalary).toString(), equalTo("2000"));
		assertThat(SalaryCalculator.getBiWeeklySalary(baseSalaryType, baseSalary).toString(), equalTo("4000"));
		assertThat(SalaryCalculator.getMonthlySalary(baseSalaryType, baseSalary).toString(), equalTo("8667"));
		assertThat(SalaryCalculator.getYearlySalary(baseSalaryType, baseSalary).toString(), equalTo("104000"));
	}
	
	@Test
	public void should_properly_convert_biweekly_salary() {
		SalaryType baseSalaryType = SalaryType.BIWEEKLY;
		BigDecimal baseSalary = new BigDecimal("4000");
		
		assertThat(SalaryCalculator.getHourlySalary(baseSalaryType, baseSalary).toString(), equalTo("50"));
		assertThat(SalaryCalculator.getWeeklySalary(baseSalaryType, baseSalary).toString(), equalTo("2000"));
		assertThat(SalaryCalculator.getBiWeeklySalary(baseSalaryType, baseSalary).toString(), equalTo("4000"));
		assertThat(SalaryCalculator.getMonthlySalary(baseSalaryType, baseSalary).toString(), equalTo("8667"));
		assertThat(SalaryCalculator.getYearlySalary(baseSalaryType, baseSalary).toString(), equalTo("104000"));
	}
	
	@Test
	public void should_properly_convert_monthly_salary() {
		SalaryType baseSalaryType = SalaryType.MONTHLY;
		BigDecimal baseSalary = new BigDecimal("10400");
		
		assertThat(SalaryCalculator.getHourlySalary(baseSalaryType, baseSalary).toString(), equalTo("60"));
		assertThat(SalaryCalculator.getWeeklySalary(baseSalaryType, baseSalary).toString(), equalTo("2400"));
		assertThat(SalaryCalculator.getBiWeeklySalary(baseSalaryType, baseSalary).toString(), equalTo("4800"));
		assertThat(SalaryCalculator.getMonthlySalary(baseSalaryType, baseSalary).toString(), equalTo("10400"));
		assertThat(SalaryCalculator.getYearlySalary(baseSalaryType, baseSalary).toString(), equalTo("124800"));
	}

	@Test
	public void should_properly_convert_annualy_salary() {
		SalaryType baseSalaryType = SalaryType.ANNUALY;
		BigDecimal baseSalary = new BigDecimal("104000");
		
		assertThat(SalaryCalculator.getHourlySalary(baseSalaryType, baseSalary).toString(), equalTo("50"));
		assertThat(SalaryCalculator.getWeeklySalary(baseSalaryType, baseSalary).toString(), equalTo("2000"));
		assertThat(SalaryCalculator.getBiWeeklySalary(baseSalaryType, baseSalary).toString(), equalTo("4000"));
		assertThat(SalaryCalculator.getMonthlySalary(baseSalaryType, baseSalary).toString(), equalTo("8667"));
		assertThat(SalaryCalculator.getYearlySalary(baseSalaryType, baseSalary).toString(), equalTo("104000"));
	}

}
