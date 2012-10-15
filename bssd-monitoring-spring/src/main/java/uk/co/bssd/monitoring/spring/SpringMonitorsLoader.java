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
package uk.co.bssd.monitoring.spring;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uk.co.bssd.monitoring.Monitors;
import uk.co.bssd.monitoring.loader.MonitorDefinition;
import uk.co.bssd.monitoring.loader.MonitorsLoader;

public class SpringMonitorsLoader implements MonitorsLoader {

	private final ApplicationContext context;
	
	public SpringMonitorsLoader(String configLocation) {
		this.context = new ClassPathXmlApplicationContext(configLocation);
	}
	
	@Override
	public void load(Monitors monitors) {
		Map<String, MonitorDefinition> monitorDefinitions = this.context.getBeansOfType(MonitorDefinition.class);
		
		for (MonitorDefinition definition : monitorDefinitions.values()) {
			monitors.register(definition.monitor(), definition.monitorPeriodMs());
		}
	}
}