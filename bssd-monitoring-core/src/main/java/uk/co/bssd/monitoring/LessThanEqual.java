package uk.co.bssd.monitoring;

public class LessThanEqual<T extends Number> extends NumberCondition<T> {

	private final T threshold;

	public LessThanEqual(T threshold) {
		this.threshold = threshold;
	}

	@Override
	public boolean conditionMet(T value) {
		return this.threshold.floatValue() >= value.floatValue();
	}
	
	@Override
	public String toString() {
		return String.format("Less than or equal to %s", threshold);
	}
}