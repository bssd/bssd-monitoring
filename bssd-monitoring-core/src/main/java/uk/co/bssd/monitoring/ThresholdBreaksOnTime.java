package uk.co.bssd.monitoring;

import org.joda.time.DateTime;

public class ThresholdBreaksOnTime implements Threshold {

	private final int seconds;

	private DateTime timeThresholdExceeded;

	public ThresholdBreaksOnTime(int seconds) {
		this.seconds = seconds;
	}

	@Override
	public boolean thresholdBroken(boolean conditionMet) {
		if (conditionMet) {
			if (timeThresholdExceeded == null) {
				this.timeThresholdExceeded = new DateTime();
			} else {
				return this.timeThresholdExceeded.plusSeconds(this.seconds).isAfterNow();
			}
		} else {
			this.timeThresholdExceeded = null;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return String.format("After %d seconds", this.seconds);
	}
}