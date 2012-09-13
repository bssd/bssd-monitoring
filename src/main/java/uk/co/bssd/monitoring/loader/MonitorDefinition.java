package uk.co.bssd.monitoring.loader;

import uk.co.bssd.monitoring.Monitor;

public class MonitorDefinition {

	private final Monitor<?> monitor;
	private final long monitorPeriodMs;
	
	public MonitorDefinition(Monitor<?> monitor, long monitorPeriodMs) {
		this.monitor = monitor;
		this.monitorPeriodMs = monitorPeriodMs;
	}
	
	public Monitor<?> monitor() {
		return this.monitor;
	}
	
	public long monitorPeriodMs() {
		return this.monitorPeriodMs;
	}
}