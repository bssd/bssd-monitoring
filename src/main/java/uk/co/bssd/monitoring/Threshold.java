package uk.co.bssd.monitoring;

public interface Threshold {

	boolean thresholdBroken(boolean conditionMet);
}