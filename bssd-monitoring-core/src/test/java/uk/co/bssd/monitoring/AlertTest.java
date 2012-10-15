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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AlertTest {

	@Mock
	private Condition<Long> mockCondition;

	@Mock
	private Threshold mockThreshold;

	@Mock
	private AlertListener mockAlertListener;

	private Alert<Long> alert;

	@Before
	public void before() {
		this.alert = new Alert<Long>(this.mockCondition, this.mockThreshold,
				this.mockAlertListener);
	}

	@Test
	public void testValueIsCheckedWithCondition() {
		Long currentValue = Long.valueOf(4);
		this.alert.alertIfConditionBroken(currentValue);
		verify(this.mockCondition).conditionMet(currentValue);
	}

	@Test
	public void testResultFromConditionIsCheckedAgainstWhetherThresholdHasBeenBroken() {
		boolean conditionMet = true;
		when(this.mockCondition.conditionMet(any(Long.class))).thenReturn(
				conditionMet);
		this.alert.alertIfConditionBroken(4L);
		verify(this.mockThreshold).thresholdBroken(conditionMet);
	}

	@Test
	public void testWhenThresholdIsBrokenAlertIsRaised() {
		when(this.mockThreshold.thresholdBroken(anyBoolean())).thenReturn(true);
		this.alert.alertIfConditionBroken(4L);
		verify(this.mockAlertListener).alert(anyAlertEvent());
	}

	@Test
	public void testWhenThresholdIsNotBrokenAlertIsNotRaised() {
		when(this.mockThreshold.thresholdBroken(anyBoolean()))
				.thenReturn(false);
		this.alert.alertIfConditionBroken(4L);
		verify(this.mockAlertListener, never()).alert(anyAlertEvent());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testAlertEventRaisedContainsCorrectInformation() {
		Long currentValue = Long.valueOf(7);
		when(this.mockThreshold.thresholdBroken(anyBoolean())).thenReturn(true);
		this.alert.alertIfConditionBroken(currentValue);

		ArgumentCaptor<AlertEvent> alertEventCaptor = ArgumentCaptor
				.forClass(AlertEvent.class);
		verify(this.mockAlertListener).alert(alertEventCaptor.capture());

		AlertEvent<Long> alertEvent = alertEventCaptor.getValue();
		assertThat(alertEvent.value(), is(currentValue));
		assertThat(alertEvent.condition(), is(this.mockCondition));
		assertThat(alertEvent.threshold(), is(this.mockThreshold));
	}

	@SuppressWarnings("unchecked")
	private AlertEvent<Long> anyAlertEvent() {
		return any(AlertEvent.class);
	}
}