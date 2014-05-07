package permnotifier.domain;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.*;

import org.junit.Test;

public class WageTypeTest {

	@Test
	public void should_return_valid_wage_type() {
		assertThat(SalaryType.getSalaryType(null), nullValue());
		assertThat(SalaryType.getSalaryType(""), nullValue());
		assertThat(SalaryType.getSalaryType("not-existing"), nullValue());
		assertThat(SalaryType.getSalaryType("year"), sameInstance(SalaryType.ANNUALY));
		assertThat(SalaryType.getSalaryType("yr"), sameInstance(SalaryType.ANNUALY));
		assertThat(SalaryType.getSalaryType("month"), sameInstance(SalaryType.MONTHLY));
		assertThat(SalaryType.getSalaryType("mth"), sameInstance(SalaryType.MONTHLY));
		assertThat(SalaryType.getSalaryType("bi"), sameInstance(SalaryType.BIWEEKLY));
		assertThat(SalaryType.getSalaryType("bi-weekly"), sameInstance(SalaryType.BIWEEKLY));
		assertThat(SalaryType.getSalaryType("week"), sameInstance(SalaryType.WEEKLY));
		assertThat(SalaryType.getSalaryType("wk"), sameInstance(SalaryType.WEEKLY));
		assertThat(SalaryType.getSalaryType("hour"), sameInstance(SalaryType.HOURLY));
		assertThat(SalaryType.getSalaryType("hr"), sameInstance(SalaryType.HOURLY));
	}

}
