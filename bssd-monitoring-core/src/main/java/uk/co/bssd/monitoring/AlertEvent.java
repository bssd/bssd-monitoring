package uk.co.bssd.monitoring;

public class AlertEvent<T> {

	private final T value;
	private final Condition<T> condition;
	private final Threshold threshold;

	public AlertEvent(T value, Condition<T> condition, Threshold threshold) {
		this.value = value;
		this.condition = condition;
		this.threshold = threshold;
	}

	public T value() {
		return this.value;
	}

	public Condition<T> condition() {
		return this.condition;
	}

	public Threshold threshold() {
		return this.threshold;
	}
}