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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HelpCommandTest {

	private static final String COMMAND = "help";
	
	@Mock
	private CommandLineShell mockShell;
	
	@Mock
	private PrintStream mockOutputStream;

	private PrintStream realOutputStream;
	
	private Commands commands;
	
	private CommandHandler handler;
	
	@Before
	public void before() {
		this.commands = new Commands();
		this.handler = new HelpCommand(this.commands);
		
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
		assertThat(this.handler.description(), is("Display all the available commands"));
	}
	
	@Test
	public void testCorrectNumberOfLinesOutputWhenCommandInvoked() {
		this.handler.handle(COMMAND, mockShell);
		int expectedNumberLines = 2 + this.commands.list().size();
		verify(this.mockOutputStream, times(expectedNumberLines)).println(anyString());
	}
}