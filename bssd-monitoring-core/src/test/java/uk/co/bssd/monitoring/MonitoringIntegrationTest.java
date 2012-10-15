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

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MonitoringIntegrationTest {

	private static final int TIMEOUT_MS = 1000;
	
	@Mock
	private Alert mockAlert;

	private AtomicInteger monitoredValue;

	private Monitors monitors;

	@Before
	public void before() {
		this.monitoredValue = new AtomicInteger(0);

		Monitor<Integer> monitor = new Monitor<Integer>(
				new MonitoredValueAdapter<Integer>() {
					@Override
					public Integer currentValue() {
						return MonitoringIntegrationTest.this.monitoredValue
								.get();
					}
				}, new GreaterThan<Integer>(10), new ThresholdBreaksImmediately(),
				this.mockAlert);

		this.monitors = new Monitors();
		this.monitors.register(monitor, 1);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testWhenConditionIsNotBrokenAlertIsNotRaised() {
		verify(this.mockAlert, timeout(TIMEOUT_MS).never()).alert(any(AlertEvent.class));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testWhenConditionIsBrokenAlertIsRaised() {
		this.monitoredValue.set(11);
		verify(this.mockAlert, timeout(TIMEOUT_MS)).alert(any(AlertEvent.class));
	}
}