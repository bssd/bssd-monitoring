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

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConsoleAlertTest {

	private Long currentValue;
	
	@Mock
	private Condition<Long> mockCondition;
	
	@Mock
	private Threshold mockThreshold;
	
	private AlertEvent<Long> alertEvent;
	
	@Captor
	private ArgumentCaptor<String> alertOutputCaptor;
	
	@Mock
	private PrintStream mockOutputStream;

	private PrintStream realOutputStream;

	private AlertListener alert;
	
	@Before
	public void before() {
		this.currentValue = Long.valueOf(56);
		this.alertEvent = new AlertEvent<Long>(this.currentValue, this.mockCondition, this.mockThreshold);
		
		this.alert = new ConsoleAlert();
		
		this.realOutputStream = System.out;
		System.setOut(this.mockOutputStream);
	}
	
	@After
	public void after() {
		System.setOut(this.realOutputStream);
	}	
	
	@Test
	public void testAlertRaisedLogsToSystemOut() {
		this.alert.alert(this.alertEvent);
		verify(this.mockOutputStream).println(any(String.class));
	}
	
	@Test
	public void testLoggedOutputReportsCurrentValue() {
		assertThat(alertOutput(), containsString("Current value [" + this.currentValue + "]"));
	}
	
	@Test
	public void testLoggedOutputReportsConditionBroken() {
		String condition = "Greater than 0";
		when(this.mockCondition.toString()).thenReturn(condition);
		assertThat(alertOutput(), containsString("has broken condition [" + condition + "]"));
	}
	
	@Test
	public void testLoggedOutputReportsThresholdBroken() {
		String threshold = "Immediately";
		when(this.mockThreshold.toString()).thenReturn(threshold);
		assertThat(alertOutput(), containsString("for threshold [" + threshold + "]"));
	}
	
	private String alertOutput() {
		this.alert.alert(this.alertEvent);
		verify(this.mockOutputStream).println(this.alertOutputCaptor.capture());
		return this.alertOutputCaptor.getValue();
	}
	
}