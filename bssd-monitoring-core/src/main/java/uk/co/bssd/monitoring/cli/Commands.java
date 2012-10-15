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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Commands {

	private final Map<String, CommandHandler> handlers;
	private final CommandHandler defaultHandler;

	public Commands() {
		this.handlers = new LinkedHashMap<String, CommandHandler>();
		this.defaultHandler = new UnknownCommand();
		registerHandlers();
	}

	public CommandHandler handlerFor(String command) {
		if (this.handlers.containsKey(command)) {
			return this.handlers.get(command);
		}
		return this.defaultHandler;
	}

	public List<CommandHandler> list() {
		return new ArrayList<CommandHandler>(this.handlers.values());
	}

	private void registerHandlers() {
		registerHandler(new HelpCommand(this));
		registerHandler(new ListCommand());
		registerHandler(new LoadCommand());
		registerHandler(new ExitCommand());
	}

	private void registerHandler(CommandHandler handler) {
		this.handlers.put(handler.command(), handler);
	}
}