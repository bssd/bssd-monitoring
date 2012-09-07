package uk.co.bssd.monitoring;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class GreaterThanTest {

	private static final Long THRESHOLD = Long.valueOf(5);

	private Condition<Long> condition;

	@Before
	public void before() {
		this.condition = new GreaterThan<Long>(THRESHOLD);
	}

	@Test
	public void testConditionIsNotMetIfLessThanThreshold() {
		assertThat(this.condition.conditionMet(THRESHOLD - 1), is(false));
	}

	@Test
	public void testConditionIsNotMetIfEqualToThreshold() {
		assertThat(this.condition.conditionMet(THRESHOLD), is(false));
	}
	
	@Test
	public void testConditionIsMetIfGreaterThanThreshold() {
		assertThat(this.condition.conditionMet(THRESHOLD + 1), is(true));
	}
}