package uk.co.bssd.monitoring;

public interface Scheduler {

	void schedule(Runnable job, long intervalMs);
}