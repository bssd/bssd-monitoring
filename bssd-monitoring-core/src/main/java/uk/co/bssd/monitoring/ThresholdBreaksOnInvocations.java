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

public class ThresholdBreaksOnInvocations implements Threshold {

	private final int invocations;

	private int conditionMetCount;

	public ThresholdBreaksOnInvocations(int invocations) {
		this.invocations = invocations;
		this.conditionMetCount = 0;
	}

	@Override
	public boolean thresholdBroken(boolean conditionMet) {
		if (conditionMet) {
			this.conditionMetCount++;
		} else {
			this.conditionMetCount = 0;
		}
		return this.conditionMetCount == this.invocations;
	}
	
	@Override
	public String toString() {
		return String.format("After %d invocations", this.invocations);
	}
}