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