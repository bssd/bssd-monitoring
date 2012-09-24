package uk.co.bssd.monitoring;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class LessThanEqualTest {

	private static final Long THRESHOLD = Long.valueOf(5);

	private Condition<Long> condition;

	@Before
	public void before() {
		this.condition = new LessThanEqual<Long>(THRESHOLD);
	}

	@Test
	public void testConditionIsMetIfLessThanThreshold() {
		assertThat(this.condition.conditionMet(THRESHOLD - 1), is(true));
	}

	@Test
	public void testConditionIsMetIfEqualToThreshold() {
		assertThat(this.condition.conditionMet(THRESHOLD), is(true));
	}
	
	@Test
	public void testConditionIsNotMetIfGreaterThanThreshold() {
		assertThat(this.condition.conditionMet(THRESHOLD + 1), is(false));
	}
	
	@Test
	public void testToString() {
		assertThat(this.condition.toString(), is("Less than or equal to " + THRESHOLD));
	}	
}