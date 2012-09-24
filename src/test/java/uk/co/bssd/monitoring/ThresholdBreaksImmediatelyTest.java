package uk.co.bssd.monitoring;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ThresholdBreaksImmediatelyTest {

	private Threshold samplingRate;
	
	@Before
	public void before() {
		this.samplingRate = new ThresholdBreaksImmediately();
	}
	
	@Test
	public void testWhenConditionIsNotMetThresholdIsNotBroken() {
		assertThat(this.samplingRate.thresholdBroken(false), is(false));
	}
	
	@Test
	public void testWhenConditionIsNotMetRepeatedlyTheThresholdIsNotBroken() {
		this.samplingRate.thresholdBroken(false);
		assertThat(this.samplingRate.thresholdBroken(false), is(false));
	}
	
	@Test
	public void testWhenConditionIsMetThresholdIsBroken() {
		assertThat(this.samplingRate.thresholdBroken(true), is(true));
	}
	
	@Test
	public void testWhenTheConditionIsMetRepeatedlyTheThresholdIsNotTriggeredMultipleTimes() {
		this.samplingRate.thresholdBroken(true);
		assertThat(this.samplingRate.thresholdBroken(true), is(false));
	}
	
	@Test
	public void testWhenTheConditionIsMetThenNotMetThenMetThresholdIsTriggeredAgain() {
		this.samplingRate.thresholdBroken(true);
		this.samplingRate.thresholdBroken(false);
		assertThat(this.samplingRate.thresholdBroken(true), is(true));
	}
	
	@Test
	public void testToString() {
		assertThat(this.samplingRate.toString(), is("Immediately"));
	}	
}