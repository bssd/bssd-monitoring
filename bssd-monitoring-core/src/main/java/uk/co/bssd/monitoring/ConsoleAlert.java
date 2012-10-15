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

public class ConsoleAlert implements Alert {

	private static final String ALERT_TEMPLATE = "Current value [%s] has broken condition [%s] for threshold [%s]";
	
	@Override
	public <T> void alert(AlertEvent<T> event) {
		String alert = String.format(ALERT_TEMPLATE, event.value(), event.condition(), event.threshold());
		System.out.println(alert);
	}
}