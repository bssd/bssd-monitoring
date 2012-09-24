package uk.co.bssd.monitoring;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.joda.time.DateTimeUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ThresholdBreaksOnTimeTest {

	private static final int THRESHOLD_SECONDS = 60;

	private Threshold samplingRate;

	@Before
	public void before() {
		DateTimeUtils.setCurrentMillisFixed(DateTimeUtils.currentTimeMillis());
		this.samplingRate = new ThresholdBreaksOnTime(THRESHOLD_SECONDS);
	}

	@After
	public void after() {
		DateTimeUtils.setCurrentMillisSystem();
	}

	@Test
	public void testWhenTimeNotReachedThresholdIsNotBroken() {
		this.samplingRate.thresholdBroken(true);
		setTimeNowPlusSeconds(THRESHOLD_SECONDS + 1);
		assertThat(this.samplingRate.thresholdBroken(true), is(false));
	}

	@Test
	public void testWhenTimeReachedThresholdIsBroken() {
		this.samplingRate.thresholdBroken(true);
		setTimeNowPlusSeconds(THRESHOLD_SECONDS - 1);
		assertThat(this.samplingRate.thresholdBroken(true), is(true));
	}

	@Test
	public void testWhenTimeExceededThresholdIsNotBrokenAgain() {
		this.samplingRate.thresholdBroken(true);
		setTimeNowPlusSeconds(THRESHOLD_SECONDS - 1);
		this.samplingRate.thresholdBroken(true);
		setTimeNowPlusSeconds(1);
		assertThat(this.samplingRate.thresholdBroken(true), is(false));
	}

	@Test
	public void testWhenInterruptedThresholdIsNotBroken() {
		this.samplingRate.thresholdBroken(true);
		setTimeNowPlusSeconds(THRESHOLD_SECONDS - 1);
		this.samplingRate.thresholdBroken(false);
		setTimeNowPlusSeconds(THRESHOLD_SECONDS - 1);
		assertThat(this.samplingRate.thresholdBroken(true), is(false));
	}
	
	@Test
	public void testToString() {
		assertThat(this.samplingRate.toString(), is("After "+  THRESHOLD_SECONDS + " seconds"));
	}
	
	private void setTimeNowPlusSeconds(int seconds) {
		DateTimeUtils.setCurrentMillisFixed(nowPlusSeconds(seconds));
	}

	private long nowPlusSeconds(int seconds) {
		return DateTimeUtils.currentTimeMillis() + toMilliseconds(seconds);
	}

	private long toMilliseconds(int seconds) {
		return seconds * 1000;
	}
}