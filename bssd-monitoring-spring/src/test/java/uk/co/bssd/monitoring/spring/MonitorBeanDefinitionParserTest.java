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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uk.co.bssd.monitoring.loader.MonitorDefinition;

public class MonitorBeanDefinitionParserTest {

	private ApplicationContext context;

	@Before
	public void before() {
		this.context = new ClassPathXmlApplicationContext(
				"classpath:monitor-bean-parser-context.xml");
	}

	@Test
	public void testMonitorDefinitionBeanIsParsedCorrectly() {
		assertThat(monitorDefinition(), is(notNullValue()));
	}

	@Test
	public void testMonitorDefinitionHasCorrectMonitor() {
		assertThat(
				monitorDefinition().monitor().toString(),
				is("Value Adapter [CpuUsageAdapter], Alert [null], Value Reporter [CsvFileLogger with id [csvFileReporter] logging to file [target/namespaceMonitor.csv]]"));
	}
	
	@Test
	public void testMonitorIntervalHasBeenSetCorrectly() {
		assertThat(monitorDefinition().monitorPeriodMs(), is(3982L));
	}

	private MonitorDefinition monitorDefinition() {
		return this.context.getBean(MonitorDefinition.class);
	}
}