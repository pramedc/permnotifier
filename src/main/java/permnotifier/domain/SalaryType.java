package permnotifier.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public enum SalaryType {

	HOURLY("HOUR", "HR"),
	WEEKLY("WEEK", "WK"),
	BIWEEKLY("BI-WEEKLY", "BI"),
	MONTHLY("MONTH", "MTH"),
	ANNUALY("YEAR", "YR");
	
	private static final Map<String, SalaryType> cachedValues = new HashMap<>();
	static {
		for (SalaryType salaryType : values()) {
			for (String validValue : salaryType.validValues) {
				cachedValues.put(validValue, salaryType);
			}
		}
	}
	
	private final List<String> validValues;
	private SalaryType(String... validValues) {
		this.validValues = Arrays.asList(validValues);
	}
	
	public static SalaryType getSalaryType(String value) {
		String cleanupValue = StringUtils.upperCase(StringUtils.trimToEmpty(value));
		if(StringUtils.isBlank(cleanupValue)) {
			return null;
		}
		
		return cachedValues.get(cleanupValue);
	}
}
