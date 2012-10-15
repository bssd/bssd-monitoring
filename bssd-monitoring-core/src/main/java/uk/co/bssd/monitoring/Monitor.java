/*
 * Copyright 2002-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.co.bssd.monitoring;

public class Monitor<T> {

	private final MonitoredValueAdapter<T> valueAdapter;
	private final Condition<T> condition;
	private final Threshold threshold;
	private final Alert alert;
	private final ValueReporter<T> valueReporter;

	public Monitor(MonitoredValueAdapter<T> adapter, Condition<T> condition,
			Threshold threshold, Alert alert) {
		this(adapter, condition, threshold, alert,
				new DoNothingValueReporter<T>());
	}

	public Monitor(MonitoredValueAdapter<T> adapter, Condition<T> condition,
			Threshold threshold, Alert alert, ValueReporter<T> reporter) {
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

	@Override
	public String toString() {
		return String
				.format("Value Adapter [%s], Condition [%s], Threshold [%s], Alert [%s]",
						this.valueAdapter, this.condition, this.threshold,
						this.alert);
	}
}