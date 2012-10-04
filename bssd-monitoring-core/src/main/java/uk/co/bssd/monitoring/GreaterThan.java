package uk.co.bssd.monitoring;

public class GreaterThan<T extends Number> extends NumberCondition<T> {

	private final T threshold;

	public GreaterThan(T threshold) {
		this.threshold = threshold;
	}

	@Override
	public boolean conditionMet(T value) {
		return this.threshold.floatValue() < value.floatValue();
	}
	
	@Override
	public String toString() {
		return String.format("Greater than %s", threshold);
	}
}