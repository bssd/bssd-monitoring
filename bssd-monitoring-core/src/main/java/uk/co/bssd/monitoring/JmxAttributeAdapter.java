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

import uk.co.bssd.jmx.ManagementAttribute;
import uk.co.bssd.jmx.ManagementBean;
import uk.co.bssd.jmx.ManagementBeanServer;

public class JmxAttributeAdapter<T> implements MonitoredValueAdapter<T> {

	private final ManagementAttribute<T> attribute;
	
	public JmxAttributeAdapter(ManagementBeanServer mbeanServer, String objectName, String attributeName, Class<T> attributeClazz) {
		ManagementBean mbean = mbeanServer.findManagementBean(objectName);
		this.attribute = mbean.findAttribute(attributeName, attributeClazz);
	}

	@Override
	public T currentValue() {
		return this.attribute.value();
	}
}