/*
 * Copyright 2002-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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