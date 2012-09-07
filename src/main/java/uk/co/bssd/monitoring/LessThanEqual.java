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
}