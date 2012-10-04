package uk.co.bssd.monitoring.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicBoolean;

import uk.co.bssd.monitoring.Monitors;
import uk.co.bssd.monitoring.loader.MonitorsLoader;

public class CommandLineShell {

	private final Monitors monitors;
	private final Commands commands;
	private final AtomicBoolean shutdown;

	public static void main(String[] args) {
		CommandLineShell shell = new CommandLineShell();
		shell.startHandlingCommands();
	}

	public CommandLineShell() {
		this.monitors = new Monitors();
		this.commands = new Commands();
		this.shutdown = new AtomicBoolean(false);
	}

	public void loadMonitors(MonitorsLoader loader) {
		loader.load(this.monitors);
	}
	
	public void listMonitors(MonitorsOutput output) {
		output.list(this.monitors);
	}
	
	public void shutdown() {
		this.monitors.shutdown();
		this.shutdown.set(true);
	}
	
	private void startHandlingCommands() {
		System.out.println("Monitoring command line shell started");
		while (isRunning()) {
			System.out.print("> ");
			String command = readCommand();
			handleCommand(command);
		}
		System.out.println("Shell shutdown");
	}

	private boolean isRunning() {
		return !this.shutdown.get();
	}

	private String readCommand() {
		if (System.console() == null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					System.in));
			try {
				return reader.readLine();
			} catch (IOException e) {
				throw new IllegalStateException("Unable to read command", e);
			}
		}
		return System.console().readLine();
	}

	private void handleCommand(String command) {
		CommandHandler handler = this.commands.handlerFor(command);
		handler.handle(command, this);
	}
}