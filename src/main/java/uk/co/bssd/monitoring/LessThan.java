package uk.co.bssd.monitoring;

public class LessThan<T extends Number> extends NumberCondition<T> {

	private final T threshold;

	public LessThan(T threshold) {
		this.threshold = threshold;
	}

	@Override
	public boolean conditionMet(T value) {
		return this.threshold.floatValue() > value.floatValue();
	}
}