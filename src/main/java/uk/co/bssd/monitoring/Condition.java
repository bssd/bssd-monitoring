package uk.co.bssd.monitoring;

public interface Condition<T> {

	boolean conditionMet(T value);
}