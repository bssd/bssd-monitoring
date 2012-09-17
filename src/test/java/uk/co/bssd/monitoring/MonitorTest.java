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
public class MonitorTest {

	@Mock
	private MonitoredValueAdapter<Long> mockValueAdapter;

	@Mock
	private Condition<Long> mockCondition;

	@Mock
	private Threshold mockThreshold;

	@Mock
	private Alert mockAlert;

	@Mock
	private ValueReporter<Long> mockValueReporter;

	private Monitor<Long> monitor;

	@Before
	public void before() {
		this.monitor = new Monitor<Long>(this.mockValueAdapter,
				this.mockCondition, this.mockThreshold, this.mockAlert,
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
	public void testWhenMonitorIsCalledCurrentValueIsCheckedWithCondition() {
		Long currentValue = Long.valueOf(4);
		when(this.mockValueAdapter.currentValue()).thenReturn(currentValue);
		this.monitor.monitor();
		verify(this.mockCondition).conditionMet(currentValue);
	}

	@Test
	public void testResultFromConditionIsCheckedAgainstWhetherThresholdHasBeenBroken() {
		boolean conditionMet = true;
		when(this.mockCondition.conditionMet(any(Long.class))).thenReturn(
				conditionMet);
		this.monitor.monitor();
		verify(this.mockThreshold).thresholdBroken(conditionMet);
	}

	@Test
	public void testWhenThresholdIsBrokenAlertIsRaised() {
		when(this.mockThreshold.thresholdBroken(anyBoolean())).thenReturn(true);
		this.monitor.monitor();
		verify(this.mockAlert).alert(anyAlertEvent());
	}

	@Test
	public void testWhenThresholdIsNotBrokenAlertIsNotRaised() {
		when(this.mockThreshold.thresholdBroken(anyBoolean()))
				.thenReturn(false);
		this.monitor.monitor();
		verify(this.mockAlert, never()).alert(anyAlertEvent());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testAlertEventRaisedContainsCorrectInformation() {
		Long currentValue = Long.valueOf(7);
		when(this.mockValueAdapter.currentValue()).thenReturn(currentValue);
		when(this.mockThreshold.thresholdBroken(anyBoolean())).thenReturn(true);
		this.monitor.monitor();

		ArgumentCaptor<AlertEvent> alertEventCaptor = ArgumentCaptor
				.forClass(AlertEvent.class);
		verify(this.mockAlert).alert(alertEventCaptor.capture());

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