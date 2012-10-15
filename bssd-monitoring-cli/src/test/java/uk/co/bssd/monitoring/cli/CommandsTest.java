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
	private static final String COMMAND_LOAD = "load";
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
	public void testCorrectHandlerIsReturnForLoadCommand() {
		assertThat(this.commands.handlerFor(COMMAND_LOAD),
				is(instanceOf(LoadCommand.class)));
	}

	@Test
	public void testCorrectHandlerIsReturnedForExitCommand() {
		assertThat(this.commands.handlerFor(COMMAND_EXIT),
				is(instanceOf(ExitCommand.class)));
	}

	@Test
	public void testListReturnsCorrectNumberOfHandlers() {
		assertThat(this.commands.list().size(), is(4));
	}

	@Test
	public void testListReturnsHandlersInCorrectOrder() {
		Iterator<CommandHandler> handlers = this.commands.list().iterator();
		assertThat(handlers.next(), is(instanceOf(HelpCommand.class)));
		assertThat(handlers.next(), is(instanceOf(ListCommand.class)));
		assertThat(handlers.next(), is(instanceOf(LoadCommand.class)));
		assertThat(handlers.next(), is(instanceOf(ExitCommand.class)));
	}
}