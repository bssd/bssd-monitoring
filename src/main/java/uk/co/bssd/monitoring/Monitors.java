package uk.co.bssd.monitoring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Monitors {

	private final Scheduler scheduler;
	private final List<Monitor<?>> monitors;

	public Monitors() {
		this(new JdkExecutorScheduler());
	}
	
	public Monitors(Scheduler scheduler) {
		this.scheduler = scheduler;
		this.monitors = new ArrayList<Monitor<?>>();
	}

	public List<Monitor<?>> list() {
		return Collections.unmodifiableList(this.monitors);
	}

	public void register(Monitor<?> monitor, long intervalMs) {
		this.monitors.add(monitor);
		MonitorJob job = new MonitorJob(monitor);
		this.scheduler.schedule(job, intervalMs);
	}
	
	public void shutdown() {
		this.scheduler.shutdown();
		this.monitors.clear();
	}
}