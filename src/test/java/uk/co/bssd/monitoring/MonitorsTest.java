package uk.co.bssd.monitoring;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MonitorsTest {

	@Mock
	private Scheduler mockScheduler;

	@Mock
	private Monitor<?> mockMonitor;
	
	private Monitors monitors;

	@Before
	public void before() {
		this.monitors = new Monitors(this.mockScheduler);
	}

	@Test
	public void testListMonitorsWhenNoMonitorsRegistered() {
		assertThat(this.monitors.list().isEmpty(), is(true));
	}
	
	@Test
	public void testListMonitorsWhenOneIsRegistered() {
		this.monitors.register(this.mockMonitor, 1);
		assertThat(this.monitors.list().size(), is(1));
	}
	
	@Test
	public void testRegisterSchedulesJob() {
		long interval = 46234;
		this.monitors.register(this.mockMonitor, interval);
		verify(this.mockScheduler).schedule(notNull(Runnable.class), eq(interval));
	}
	
	@Test
	public void testRegisteredJobWrapsMonitor() {
		this.monitors.register(this.mockMonitor, 34);
		ArgumentCaptor<Runnable> runnableCaptor = ArgumentCaptor.forClass(Runnable.class);
		verify(this.mockScheduler).schedule(runnableCaptor.capture(), anyLong());
		
		Runnable job = runnableCaptor.getValue();
		job.run();
		verify(this.mockMonitor).monitor();
	}
}
