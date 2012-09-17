package uk.co.bssd.monitoring;

public class Monitor<T> {

	private final MonitoredValueAdapter<T> valueAdapter;
	private final Condition<T> condition;
	private final Threshold threshold;
	private final Alert alert;
	private final ValueReporter<T> valueReporter;

	public Monitor(MonitoredValueAdapter<T> adapter, Condition<T> condition, Threshold threshold, Alert alert) {
		this(adapter, condition, threshold, alert, new DoNothingValueReporter<T>());
	}
	
	public Monitor(MonitoredValueAdapter<T> adapter, Condition<T> condition, Threshold threshold, Alert alert, ValueReporter<T> reporter) {
		this.valueAdapter = adapter;
		this.condition = condition;
		this.threshold = threshold;
		this.alert = alert;
		this.valueReporter = reporter;
	}
	
	public void monitor() {
		T value = this.valueAdapter.currentValue();
		this.valueReporter.report(value);
		alertIfConditionBroken(value);
	}

	private void alertIfConditionBroken(T value) {
		boolean conditionMet = this.condition.conditionMet(value);
		if (this.threshold.thresholdBroken(conditionMet)) {
			AlertEvent<T> event = new AlertEvent<T>(value, condition, threshold);
			this.alert.alert(event);
		}
	}
}