package uk.co.bssd.monitoring.cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Commands {

	private final Map<String, CommandHandler> handlers;
	private final CommandHandler defaultHandler;

	public Commands() {
		this.handlers = new HashMap<String, CommandHandler>();
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
		registerHandler(new ExitCommand());
	}

	private void registerHandler(CommandHandler handler) {
		this.handlers.put(handler.command(), handler);
	}
}