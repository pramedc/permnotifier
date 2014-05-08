package permnotifier.helpers;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.Date;

import org.joda.time.MutableDateTime;
import org.junit.Test;

public class DateHelperTest {

	@Test
	public void should_properly_get_month() {
		assertThat(DateHelper.getMonth(null), nullValue());
		assertThat(DateHelper.getMonth(createDate(2014, 5, 1)), equalTo("May"));
		assertThat(DateHelper.getMonth(createDate(2010, 2, 28)), equalTo("Feb"));
		assertThat(DateHelper.getMonth(createDate(2000, 12, 25)), equalTo("Dec"));
	}

	@Test
	public void should_properly_get_year() {
		assertThat(DateHelper.getYear(null), nullValue());
		assertThat(DateHelper.getYear(createDate(2014, 5, 1)), equalTo("2014"));
		assertThat(DateHelper.getYear(createDate(2010, 2, 28)), equalTo("2010"));
		assertThat(DateHelper.getYear(createDate(2000, 12, 25)), equalTo("2000"));
	}

	private static Date createDate(int year, int month, int day) {
		MutableDateTime mutableDateTime = new MutableDateTime();
		mutableDateTime.setMonthOfYear(month);
		mutableDateTime.setYear(year);
		mutableDateTime.setDayOfMonth(day);
		return mutableDateTime.toDate();
	}
}
