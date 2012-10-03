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