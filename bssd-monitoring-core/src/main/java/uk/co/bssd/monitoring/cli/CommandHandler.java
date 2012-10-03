package uk.co.bssd.monitoring.cli;


public interface CommandHandler {

	String command();
	
	String description();
	
	void handle(String command, CommandLineShell shell);
}