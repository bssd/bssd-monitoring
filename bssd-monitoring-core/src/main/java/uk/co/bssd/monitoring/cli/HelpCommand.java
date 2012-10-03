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