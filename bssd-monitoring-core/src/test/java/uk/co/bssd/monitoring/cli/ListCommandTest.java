package uk.co.bssd.monitoring.cli;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ListCommandTest {

	private static final String COMMAND = "list";

	@Mock
	private CommandLineShell mockShell;
	
	@Mock
	private PrintStream mockOutputStream;

	private PrintStream realOutputStream;
	
	private CommandHandler handler;
	
	@Before
	public void before() {
		this.handler = new ListCommand();
		
		this.realOutputStream = System.out;
		System.setOut(this.mockOutputStream);
	}
	
	@After
	public void after() {
		System.setOut(this.realOutputStream);
	}
	
	@Test
	public void testCommand() {
		assertThat(this.handler.command(), is(COMMAND));
	}
	
	@Test
	public void testDescription() {
		assertThat(this.handler.description(), is("List all the registered monitors"));
	}
	
	@Test
	public void testHandlerCallsShellWithMonitorOutput() {
		this.handler.handle(COMMAND, this.mockShell);
		verify(this.mockShell).listMonitors(any(MonitorsOutput.class));
	}
}
