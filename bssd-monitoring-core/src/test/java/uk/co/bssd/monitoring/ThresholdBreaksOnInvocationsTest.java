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

import org.junit.Before;
import org.junit.Test;

public class ThresholdBreaksOnInvocationsTest {

	private static final int NUMBER_INVOCATIONS = 2;
	
	private Threshold samplingRate;
	
	@Before
	public void before() {
		this.samplingRate = new ThresholdBreaksOnInvocations(NUMBER_INVOCATIONS);
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
	
	@Test
	public void testToString() {
		assertThat(this.samplingRate.toString(), is("After "+  NUMBER_INVOCATIONS + " invocations"));
	}	
}