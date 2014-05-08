package permnotifier.helpers;

import java.util.Date;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateHelper {

	private static final DateTimeFormatter YEAR_FORMATTER = DateTimeFormat.forPattern("YYYY");
	private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormat.forPattern("MMM");
	
	public static String getMonth(Date date) {
		if(date == null) return null;
		return MONTH_FORMATTER.print(date.getTime());
	}

	public static String getYear(Date date) {
		if(date == null) return null;
		return YEAR_FORMATTER.print(date.getTime());
	}
	
}
