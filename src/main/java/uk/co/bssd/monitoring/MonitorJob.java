package uk.co.bssd.monitoring;

/* package */class MonitorJob implements Runnable {

	private final Monitor<?> monitor;

	/* package */MonitorJob(Monitor<?> monitor) {
		this.monitor = monitor;
	}

	@Override
	public void run() {
		this.monitor.monitor();
	}
}