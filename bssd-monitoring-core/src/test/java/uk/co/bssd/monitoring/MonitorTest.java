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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MonitorTest {

	@Mock
	private MonitoredValueAdapter<Long> mockValueAdapter;

	@Mock
	private Alert<Long> mockAlert;

	@Mock
	private ValueReporter<Long> mockValueReporter;

	private Monitor<Long> monitor;

	@Before
	public void before() {
		this.monitor = new Monitor<Long>(this.mockValueAdapter, this.mockAlert,
				this.mockValueReporter);
	}

	@Test
	public void testWhenMonitorIsCalledValueIsPassedToReporter() {
		Long value = Long.valueOf(98982);
		when(this.mockValueAdapter.currentValue()).thenReturn(value);
		this.monitor.monitor();
		verify(this.mockValueReporter).report(value);
	}

	@Test
	public void testWhenMonitorIsCalledValueAdapterIsAskedForCurrentValue() {
		this.monitor.monitor();
		verify(this.mockValueAdapter).currentValue();
	}

	@Test
	public void testWhenMonitorIsCalledCurrentValueIsPassedToAlertToCheck() {
		Long currentValue = Long.valueOf(4);
		when(this.mockValueAdapter.currentValue()).thenReturn(currentValue);
		this.monitor.monitor();
		verify(this.mockAlert).alertIfConditionBroken(currentValue);
	}
}