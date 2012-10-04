package uk.co.bssd.monitoring.cli;

import uk.co.bssd.monitoring.Monitor;
import uk.co.bssd.monitoring.Monitors;

public class SystemOutMonitorsStatus implements MonitorsOutput {

	@Override
	public void list(Monitors monitors) {
		if (monitors.isEmpty()) {
			System.out.println("No monitors registered");
		} else {
			for (Monitor<?> monitor : monitors) {
				System.out.println(monitor.toString());
			}
		}
	}
}