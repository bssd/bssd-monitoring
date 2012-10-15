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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class JdkExecutorScheduler implements Scheduler {

	private final ScheduledExecutorService executor;

	public JdkExecutorScheduler() {
		this.executor = Executors.newScheduledThreadPool(1);
	}

	@Override
	public void schedule(Runnable job, long intervalMs) {
		this.executor.scheduleAtFixedRate(job, intervalMs, intervalMs,
				TimeUnit.MILLISECONDS);
	}
	
	@Override
	public void shutdown() {
		this.executor.shutdown();		
	}
}