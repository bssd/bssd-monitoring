package uk.co.bssd.monitoring;

import java.util.concurrent.atomic.AtomicBoolean;

public class ThresholdBreaksImmediately implements Threshold{

	private AtomicBoolean thresholdTriggered;
	
	public ThresholdBreaksImmediately() {
		this.thresholdTriggered = new AtomicBoolean(false);
	}
	
	@Override
	public boolean thresholdBroken(boolean conditionMet) {
		return this.thresholdTriggered.compareAndSet(!conditionMet, conditionMet);
	}
}