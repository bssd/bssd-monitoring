package uk.co.bssd.monitoring;

public class Monitor<T> {

	private final MonitoredValueAdapter<T> valueAdapter;
	private final Condition<T> condition;
	private final Threshold threshold;
	private final Alert alert;

	public Monitor(MonitoredValueAdapter<T> adapter, Condition<T> condition, Threshold threshold, Alert alert) {
		this.valueAdapter = adapter;
		this.condition = condition;
		this.threshold = threshold;
		this.alert = alert;
	}
	
	public void monitor() {
		T value = this.valueAdapter.currentValue();
		boolean conditionMet = this.condition.conditionMet(value);
		if (this.threshold.thresholdBroken(conditionMet)) {
			AlertEvent<T> event = new AlertEvent<T>(value, condition, threshold);
			this.alert.alert(event);
		}
	}
}