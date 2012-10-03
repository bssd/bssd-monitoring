package uk.co.bssd.monitoring;

public class GreaterThanEqual<T extends Number> extends NumberCondition<T> {

	private final T threshold;

	public GreaterThanEqual(T threshold) {
		this.threshold = threshold;
	}

	@Override
	public boolean conditionMet(T value) {
		return this.threshold.floatValue() <= value.floatValue();
	}
	
	@Override
	public String toString() {
		return String.format("Greater than or equal to %d", threshold);
	}
}