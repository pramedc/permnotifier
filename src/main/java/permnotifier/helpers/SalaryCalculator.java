package permnotifier.helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;

import permnotifier.domain.SalaryType;

public class SalaryCalculator {

	private static BigDecimal MONTH_IN_A_YEAR = new BigDecimal("12");
	private static BigDecimal WEEK_IN_A_YEAR = new BigDecimal("52");
	private static BigDecimal HOUR_IN_A_WEEK = new BigDecimal("40");
	
	public static BigDecimal getYearlySalary(SalaryType baseSalaryType, BigDecimal baseSalary) {
		if(baseSalaryType == SalaryType.ANNUALY) return baseSalary;
		return getWeeklySalary(baseSalaryType, baseSalary).multiply(WEEK_IN_A_YEAR);
	}
	
	public static BigDecimal getMonthlySalary(SalaryType baseSalaryType, BigDecimal baseSalary) {
		if(baseSalaryType == SalaryType.MONTHLY) return baseSalary;
		return getYearlySalary(baseSalaryType, baseSalary).divide(MONTH_IN_A_YEAR, RoundingMode.CEILING);
	}
	
	public static BigDecimal getBiWeeklySalary(SalaryType baseSalaryType, BigDecimal baseSalary) {
		if(baseSalaryType == SalaryType.BIWEEKLY) return baseSalary;
		return getWeeklySalary(baseSalaryType, baseSalary).multiply(BigDecimal.valueOf(2));
	}
	
	public static BigDecimal getWeeklySalary(SalaryType baseSalaryType, BigDecimal baseSalary) {
		if(baseSalaryType == SalaryType.WEEKLY) return baseSalary;
		return getHourlySalary(baseSalaryType, baseSalary).multiply(HOUR_IN_A_WEEK);
	}
	
	public static BigDecimal getHourlySalary(SalaryType baseSalaryType, BigDecimal baseSalary) {
		System.out.println(baseSalary);
		switch (baseSalaryType) {
			case HOURLY:
				return baseSalary;
			case WEEKLY:
				return baseSalary.divide(HOUR_IN_A_WEEK, RoundingMode.CEILING);
			case BIWEEKLY:
				return baseSalary.divide(HOUR_IN_A_WEEK, RoundingMode.CEILING).divide(BigDecimal.valueOf(2), RoundingMode.CEILING);
			case MONTHLY:
				return baseSalary.multiply(MONTH_IN_A_YEAR).divide(WEEK_IN_A_YEAR, RoundingMode.CEILING).divide(HOUR_IN_A_WEEK, RoundingMode.CEILING);
			case ANNUALY:
				return baseSalary.divide(WEEK_IN_A_YEAR, RoundingMode.CEILING).divide(HOUR_IN_A_WEEK, RoundingMode.CEILING);
		}
		throw new IllegalArgumentException("salaryType is null");
	}

}
