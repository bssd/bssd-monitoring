package uk.co.bssd.monitoring;

public class EqualTo<T extends Number> extends NumberCondition<T> {

	private final T threshold;

	public EqualTo(T threshold) {
		this.threshold = threshold;
	}

	@Override
	public boolean conditionMet(T value) {
		return this.threshold.floatValue() == value.floatValue();
	}
	
	@Override
	public String toString() {
		return String.format("Equal to %s", this.threshold);
	}
}