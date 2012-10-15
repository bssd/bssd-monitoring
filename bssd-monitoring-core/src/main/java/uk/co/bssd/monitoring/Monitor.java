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
	private final Alert<T> alert;
	private final ValueReporter<T> valueReporter;

	public Monitor(MonitoredValueAdapter<T> adapter, Alert<T> alert) {
		this(adapter, alert, new DoNothingValueReporter<T>());
	}

	public Monitor(MonitoredValueAdapter<T> adapter, Alert<T> alert,
			ValueReporter<T> reporter) {
		this.valueAdapter = adapter;
		this.alert = alert;
		this.valueReporter = reporter;
	}

	public void monitor() {
		T value = this.valueAdapter.currentValue();
		this.valueReporter.report(value);
		this.alert.alertIfConditionBroken(value);
	}

	@Override
	public String toString() {
		return String.format(
				"Value Adapter [%s], Alert [%s], Value Reporter [%s]",
				this.valueAdapter, this.alert, this.valueReporter);
	}
}