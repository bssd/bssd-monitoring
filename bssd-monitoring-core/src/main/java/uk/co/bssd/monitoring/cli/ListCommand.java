package uk.co.bssd.monitoring.cli;

public class ListCommand implements CommandHandler {

	@Override
	public String command() {
		return "list";
	}

	@Override
	public String description() {
		return "List all the registered monitors";
	}

	@Override
	public void handle(String command, CommandLineShell shell) {
		System.out.println("No monitors registered");
	}
}