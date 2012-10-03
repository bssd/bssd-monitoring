package uk.co.bssd.monitoring.cli;

import static org.mockito.Mockito.verify;

import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UnknownCommandTest {

	private CommandHandler handler;

	@Mock
	private CommandLineShell mockShell;

	@Mock
	private PrintStream mockOutputStream;

	private PrintStream realOutputStream;

	@Before
	public void before() {
		this.handler = new UnknownCommand();

		this.realOutputStream = System.out;
		System.setOut(this.mockOutputStream);
	}

	@After
	public void after() {
		System.setOut(this.realOutputStream);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testCommandIsUnsupportedForUnknownCommand() {
		this.handler.command();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testDescriptionIsUnsupportedForUnknownCommand() {
		this.handler.description();
	}

	@Test
	public void testErrorMessageIsOutputWhenCommandIsInvoked() {
		this.handler.handle("unknown", this.mockShell);
		verify(this.mockOutputStream)
				.println(
						"Command 'unknown' not recognised, type 'help' to see list of available commands");

	}
}