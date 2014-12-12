/*
 * Copyright 2014 the original author or authors.
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
package org.machinelearning4j.dronez.domain;

import java.io.Serializable;

/**
 * POJO for position and velocity state attributes
 * 
 * @author Michael Lavelle
 */
public class PositionVelocityWithRecentActions<A extends Serializable> extends PositionVelocity {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private A[] recentActions;
	
	public final static int RECENT_ACTION_COUNT = 10;


	public PositionVelocityWithRecentActions(double position,double vel,A[] recentActions) {
		super(position,vel);
	
		this.recentActions = recentActions;
	}



	public A[] getRecentActions() {
		return recentActions;
	}
	
	
	

}
