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

public class UnknownCommand implements CommandHandler {

	private static final String MESSAGE_TEMPLATE = "Command '%s' not recognised, type 'help' to see list of available commands";

	@Override
	public String command() {
		throw new UnsupportedOperationException("Not supported for unknown command");
	}
	
	@Override
	public String description() {
		throw new UnsupportedOperationException("Not supported for unknown command");
	}
	
	@Override
	public void handle(String command, CommandLineShell shell) {
		String message = String.format(MESSAGE_TEMPLATE, command);
		System.out.println(message);
	}
}