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
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.co.bssd.monitoring.Monitors;
import uk.co.bssd.monitoring.loader.MonitorsLoader;

public class SpringMonitorsLoaderTest {

	private static final String CONTEXT_LOCATION = "spring-monitors-context.xml";
	
	private Monitors monitors;
	
	private MonitorsLoader loader;
	
	@Before
	public void before() {
		this.monitors = new Monitors();
		this.loader = new SpringMonitorsLoader(CONTEXT_LOCATION);
	}
	
	@Test
	public void testLoadMonitorsAddsMonitor() {
		this.loader.load(this.monitors);
		assertThat(this.monitors.list().size(), is(2));
	}
}