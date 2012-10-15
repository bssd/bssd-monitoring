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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.co.bssd.jmx.LocalManagementBeanServer;

public class JmxAttributeAdapterTest {

	public interface MonitoredAttributeMBean {
		int getMonitoredValue();
	}
	
	public static class MonitoredAttribute implements MonitoredAttributeMBean {
		private int value;
		
		public void setMonitoredValue(int newValue) {
			this.value = newValue;
		}
		
		@Override
		public int getMonitoredValue() {
			return this.value;
		}
	}
	
	private static final String MBEAN_NAME = "uk.co.bssd:type=monitoredAttribute";
	private static final String ATTRIBUTE_NAME = "MonitoredValue";

	private MonitoredAttribute monitoredAttribute;
	private LocalManagementBeanServer managementBeanServer;

	private MonitoredValueAdapter<Integer> adapter;
	
	@Before
	public void before() {
		this.monitoredAttribute = new MonitoredAttribute();
		
		this.managementBeanServer = new LocalManagementBeanServer();
		this.managementBeanServer.registerManagementBean(MBEAN_NAME, this.monitoredAttribute);
		
		this.adapter = new JmxAttributeAdapter<Integer>(this.managementBeanServer, MBEAN_NAME, ATTRIBUTE_NAME, Integer.class);
	}
	
	@Test
	public void testAdapterExtractsValueFromJmxCorrectly() {
		int value = 49984;
		this.monitoredAttribute.setMonitoredValue(value);
		assertThat(this.adapter.currentValue(), is(value));
	}
}