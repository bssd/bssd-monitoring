package uk.co.bssd.monitoring;

public class ThresholdBreaksOnInvocations implements Threshold {

	private final int invocations;

	private int conditionMetCount;

	public ThresholdBreaksOnInvocations(int invocations) {
		this.invocations = invocations;
		this.conditionMetCount = 0;
	}

	@Override
	public boolean thresholdBroken(boolean conditionMet) {
		if (conditionMet) {
			this.conditionMetCount++;
		} else {
			this.conditionMetCount = 0;
		}
		return this.conditionMetCount == this.invocations;
	}
	
	@Override
	public String toString() {
		return String.format("After %d invocations", this.invocations);
	}
}