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
package org.machinelearning4j.dronez.commands;

import org.machinelearning4j.dronez.domain.NumericAction;
import org.machinelearning4j.dronez.domain.PositionVelocityWithRecentActions;
import org.ml4j.algorithms.FeaturesMapper;

/**
* <p>Extracts numeric feature vectors from DroneStateAction<PositionVelocity,LeftRightAction> instances that we would like
* to be able to build a linear approximation regression model from for our Drone simulation model
* 
* Assume here that value can be approximated by a linear regression of numeric mappings
* of starting state, action taken and ending state.
* </p>
*
* @author Michael Lavelle
*/
public class DroneStateActionLinearRegressionFeaturesMapper implements FeaturesMapper<DroneStateAction<PositionVelocityWithRecentActions<?>,NumericAction>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public double[] toFeaturesVector(DroneStateAction<PositionVelocityWithRecentActions<?>,NumericAction> data) {
		return new double[] {1d,data.getState().getPosition(),data.getState().getVelocity(),data.getAction().getValue()};
	}

	@Override
	public int getFeatureCount() {
		return 4;
	}

}
