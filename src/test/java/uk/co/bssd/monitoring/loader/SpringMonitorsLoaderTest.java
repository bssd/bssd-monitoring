package uk.co.bssd.monitoring.loader;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.co.bssd.monitoring.Monitors;

public class SpringMonitorsLoaderTest {

	private static final String CONTEXT_LOCATION = "spring-monitors-context.xml";
	
	private Monitors monitors;
	
	private MonitorsLoader loader;
	
	@Before
	public void before() {
		this.monitors = new Monitors();
		this.loader = new SpringMonitorsLoader(CONTEXT_LOCATION);
	}
	
	@Test
	public void testLoadMonitorsAddsMonitor() {
		this.loader.load(this.monitors);
		assertThat(this.monitors.list().size(), is(1));
	}
}