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

import uk.co.bssd.jmx.ManagementBeanServer;
import uk.co.bssd.jmx.ProcessManagementBean;

public class CpuUsageAdapter implements MonitoredValueAdapter<Double> {

	private final ProcessManagementBean processBean;
	
	public CpuUsageAdapter(ManagementBeanServer beanServer) {
		this.processBean = beanServer.processManagementBean();
	}
	
	@Override
	public Double currentValue() {
		return this.processBean.cpuUsage();
	}
	
	@Override
	public String toString() {
		return CpuUsageAdapter.class.getSimpleName();
	}
}