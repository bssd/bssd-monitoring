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