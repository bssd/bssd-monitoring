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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.bssd.monitoring.loader.MonitorsLoader;

@RunWith(MockitoJUnitRunner.class)
public class LoadCommandTest {

	private static final String COMMAND = "load";

	@Mock
	private CommandLineShell mockShell;
	
	@Mock
	private PrintStream mockOutputStream;

	private PrintStream realOutputStream;
	
	private CommandHandler handler;
	
	@Before
	public void before() {
		this.handler = new LoadCommand();
		
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
		assertThat(this.handler.description(), is("Load the monitors from the spring-monitors-context.xml configuration file"));
	}
	
	@Test
	public void testInvokeHandlerCallsShellBackWithMonitorLoader() {
		this.handler.handle(COMMAND, this.mockShell);
		verify(this.mockShell).loadMonitors(any(MonitorsLoader.class));
	}
	
	@Test
	public void testSuccessfulInvocationNotifiesOutputOfSuccess() {
		this.handler.handle(COMMAND, this.mockShell);
		verify(this.mockOutputStream).println("Monitors loaded successfully");
	}
}
