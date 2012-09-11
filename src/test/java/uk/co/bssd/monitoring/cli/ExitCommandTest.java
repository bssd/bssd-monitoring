package uk.co.bssd.monitoring.cli;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ExitCommandTest {

	private static final String COMMAND = "exit";
	
	private CommandHandler handler;

	@Mock
	private CommandLineShell mockShell;

	@Before
	public void before() {
		this.handler = new ExitCommand();
	}
	
	@Test
	public void testCommand() {
		assertThat(this.handler.command(), is(COMMAND));
	}
	
	@Test
	public void testDescription() {
		assertThat(this.handler.description(), is("Exit the shell"));
	}

	@Test
	public void testHandlerInvokesShutdownOnShell() {
		this.handler.handle(COMMAND, this.mockShell);
		verify(this.mockShell).shutdown();
	}
}