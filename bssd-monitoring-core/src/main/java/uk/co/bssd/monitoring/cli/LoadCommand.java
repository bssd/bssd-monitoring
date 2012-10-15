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

import uk.co.bssd.monitoring.loader.MonitorsLoader;
import uk.co.bssd.monitoring.loader.SpringMonitorsLoader;

public class LoadCommand implements CommandHandler {

	private static final String SPRING_MONITORS_CONTEXT_FILE = "spring-monitors-context.xml";

	@Override
	public String command() {
		return "load";
	}

	@Override
	public String description() {
		return "Load the monitors from the spring-monitors-context.xml configuration file";
	}

	@Override
	public void handle(String command, CommandLineShell shell) {
		MonitorsLoader loader = new SpringMonitorsLoader(SPRING_MONITORS_CONTEXT_FILE);
		shell.loadMonitors(loader);
		System.out.println("Monitors loaded successfully");
	}
}