package uk.co.bssd.monitoring.cli;

import uk.co.bssd.monitoring.loader.MonitorsLoader;
import uk.co.bssd.monitoring.loader.SpringMonitorsLoader;

public class LoadCommand implements CommandHandler {

	@Override
	public String command() {
		return "load";
	}

	@Override
	public String description() {
		return "Load the monitors from a configuration file";
	}

	@Override
	public void handle(String command, CommandLineShell shell) {
		MonitorsLoader loader = new SpringMonitorsLoader("spring-monitors-context.xml");
		shell.loadMonitors(loader);
	}
}