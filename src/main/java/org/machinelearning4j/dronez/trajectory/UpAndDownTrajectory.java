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
package org.machinelearning4j.dronez.trajectory;

import org.ml4j.dronez.DroneState;
import org.ml4j.dronez.PositionVelocity;
import org.ml4j.mdp.Trajectory;

/**
 * A sine-wave trajectory, specifying a target position/velocity for a given
 * iteration
 * 
 * @author Michael Lavelle
 * 
 */
public class UpAndDownTrajectory implements Trajectory<DroneState> {

	private DroneState initialState;

	public UpAndDownTrajectory(DroneState initialState) {
		this.initialState = initialState;
	}

	@Override
	public DroneState getTarget(long iteration) {
		PositionVelocity leftRightTarget = new PositionVelocity(initialState.getLeftRightPositionVelocity()
				.getPosition(), 0);
		PositionVelocity upDownTarget = new PositionVelocity(initialState.getUpDownPositionVelocity().getPosition()
				+ 60 - 60 * Math.cos(2 * Math.PI * iteration / 60), 0);
		PositionVelocity forwardBackTarget = new PositionVelocity(initialState.getForwardBackPositionVelocity()
				.getPosition(), 0);
		PositionVelocity spinTarget = new PositionVelocity(initialState.getSpinPositionVelocity().getPosition(), 0);
		return new DroneState(leftRightTarget, upDownTarget, forwardBackTarget, spinTarget);
	}

}
