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

import uk.co.bssd.monitoring.CpuUsageAdapter;

public class CpuUsageAdapterBeanDefinitionParserTest {

	private ApplicationContext context;
	
	@Before
	public void before() {
		this.context = new ClassPathXmlApplicationContext("classpath:cpu-usage-adapter-context.xml");
	}
	
	@Test
	public void testCpuUsageAdapterBeanIsParsedCorrectly() {
		assertThat(this.context.getBean(CpuUsageAdapter.class), is(notNullValue()));
	}
	
	@Test
	public void testCpuUsageAdapterBeanCanBeFoundById() {
		assertThat(this.context.getBean("cpuUsage"), is(notNullValue()));
	}
}