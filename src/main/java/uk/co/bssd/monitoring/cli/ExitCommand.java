package uk.co.bssd.monitoring.cli;


public class ExitCommand implements CommandHandler {

	@Override
	public String command() {
		return "exit";
	}
	
	@Override
	public String description() {
		return "Exit the shell";
	}
	
	@Override
	public void handle(String command, CommandLineShell shell) {
		shell.shutdown();
	}
}