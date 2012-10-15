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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Monitors implements Iterable<Monitor<?>>{

	private final Scheduler scheduler;
	private final List<Monitor<?>> monitors;

	public Monitors() {
		this(new JdkExecutorScheduler());
	}
	
	public Monitors(Scheduler scheduler) {
		this.scheduler = scheduler;
		this.monitors = new ArrayList<Monitor<?>>();
	}

	public List<Monitor<?>> list() {
		return Collections.unmodifiableList(this.monitors);
	}
	
	@Override
	public Iterator<Monitor<?>> iterator() {
		return list().iterator();
	}

	public void register(Monitor<?> monitor, long intervalMs) {
		this.monitors.add(monitor);
		MonitorJob job = new MonitorJob(monitor);
		this.scheduler.schedule(job, intervalMs);
	}
	
	public void shutdown() {
		this.scheduler.shutdown();
		this.monitors.clear();
	}

	public boolean isEmpty() {
		return this.monitors.isEmpty();
	}
}