package uk.co.bssd.monitoring;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class JdkExecutorScheduler implements Scheduler {

	private final ScheduledExecutorService executor;

	public JdkExecutorScheduler() {
		this.executor = Executors.newScheduledThreadPool(1);
	}

	@Override
	public void schedule(Runnable job, long intervalMs) {
		this.executor.scheduleAtFixedRate(job, intervalMs, intervalMs,
				TimeUnit.MILLISECONDS);
	}
	
	@Override
	public void shutdown() {
		this.executor.shutdown();		
	}
}