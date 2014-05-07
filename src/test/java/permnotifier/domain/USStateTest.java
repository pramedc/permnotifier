package permnotifier.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class USStateTest {

	@Test
	public void should_return_valid_state() {
		assertThat(USState.getState(null), nullValue());
		assertThat(USState.getState(""), nullValue());
		assertThat(USState.getState("manila"), nullValue());
		assertThat(USState.getState("wa"), sameInstance(USState.WA));
		assertThat(USState.getState("washington"), sameInstance(USState.WA));
	}

	@Test
	public void should_return_state_name() {
		assertThat(USState.WA.getStateName(), equalTo("WASHINGTON"));
	}
}
