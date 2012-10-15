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

public class HelpCommand implements CommandHandler {

	private static final String EMPTY = "";
	
	private final Commands commands;

	public HelpCommand(Commands commands) {
		this.commands = commands;
	}

	@Override
	public String command() {
		return "help";
	}

	@Override
	public String description() {
		return "Display all the available commands";
	}

	@Override
	public void handle(String command, CommandLineShell shell) {
		System.out.println("The following commands are available:");
		System.out.println(EMPTY);
		
		for (CommandHandler handler : this.commands.list()) {
			System.out.println("\t" + handler.command() + " - " + handler.description());
		}
	}
}