package uk.co.bssd.monitoring;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ThresholdBreaksOnInvocationsTest {

	private Threshold samplingRate;
	
	@Before
	public void before() {
		this.samplingRate = new ThresholdBreaksOnInvocations(2);
	}
	
	@Test
	public void testWhenNumberOfInvocationsNotReachedThresholdIsNotBroken() {
		assertThat(this.samplingRate.thresholdBroken(true), is(false));
	}
	
	@Test
	public void testWhenNumberOfInvocationsReachedThresholdIsBroken() {
		this.samplingRate.thresholdBroken(true);
		assertThat(this.samplingRate.thresholdBroken(true), is(true));
	}
	
	@Test
	public void testWhenNumberOfInvocationsExceededThresholdIsNotBrokenAgain() {
		this.samplingRate.thresholdBroken(true);
		this.samplingRate.thresholdBroken(true);
		assertThat(this.samplingRate.thresholdBroken(true), is(false));
	}
	
	@Test
	public void testWhenNumberOfInvocationsInterruptedThresholdIsNotBroken() {
		this.samplingRate.thresholdBroken(true);
		this.samplingRate.thresholdBroken(false);
		assertThat(this.samplingRate.thresholdBroken(true), is(false));
	}
}