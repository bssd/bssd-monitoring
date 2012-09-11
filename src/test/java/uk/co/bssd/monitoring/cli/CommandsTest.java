package uk.co.bssd.monitoring.cli;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class CommandsTest {

	private static final String COMMAND_UNKNOWN = "unknown";
	private static final String COMMAND_HELP = "help";
	private static final String COMMAND_LIST = "list";
	private static final String COMMAND_EXIT = "exit";

	private Commands commands;

	@Before
	public void before() {
		this.commands = new Commands();
	}

	@Test
	public void testDefaultHandlerIsForUnknownCommand() {
		assertThat(this.commands.handlerFor(COMMAND_UNKNOWN),
				is(instanceOf(UnknownCommand.class)));
	}

	@Test
	public void testCorrectHandlerIsReturnedForHelpCommand() {
		assertThat(this.commands.handlerFor(COMMAND_HELP),
				is(instanceOf(HelpCommand.class)));
	}

	@Test
	public void testCorrectHandlerIsReturnedForListCommand() {
		assertThat(this.commands.handlerFor(COMMAND_LIST),
				is(instanceOf(ListCommand.class)));
	}

	@Test
	public void testCorrectHandlerIsReturnedForExitCommand() {
		assertThat(this.commands.handlerFor(COMMAND_EXIT),
				is(instanceOf(ExitCommand.class)));
	}

	@Test
	public void testListReturnsCorrectNumberOfHandlers() {
		assertThat(this.commands.list().size(), is(3));
	}

	@Test
	public void testListReturnsHandlersInCorrectOrder() {
		Iterator<CommandHandler> handlers = this.commands.list().iterator();
		assertThat(handlers.next(), is(instanceOf(HelpCommand.class)));
		assertThat(handlers.next(), is(instanceOf(ListCommand.class)));
		assertThat(handlers.next(), is(instanceOf(ExitCommand.class)));
	}
}