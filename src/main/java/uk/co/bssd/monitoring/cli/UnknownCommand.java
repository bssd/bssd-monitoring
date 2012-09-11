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