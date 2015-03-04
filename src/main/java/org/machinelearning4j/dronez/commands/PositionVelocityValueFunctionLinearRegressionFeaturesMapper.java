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

import org.ml4j.algorithms.FeaturesMapper;
import org.ml4j.dronez.LeftRightAction;
import org.ml4j.dronez.PositionVelocityWithRecentActions;

/**
 * <p>
 * Extracts numeric feature vectors from PositionVelocity instances that we
 * would like to be able to build a linear approximation regression model for
 * the value function from.
 * 
 * Assume here that value is inversely linked to position and velocity
 * magnitude, and so we choose a suitable mapping of the velocity feature as our
 * training data, along with an intercept term
 * </p>
 *
 * @author Michael Lavelle
 */
public class PositionVelocityValueFunctionLinearRegressionFeaturesMapper implements
		FeaturesMapper<PositionVelocityWithRecentActions<LeftRightAction>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean includeRecentActions = false;

	@Override
	public double[] toFeaturesVector(PositionVelocityWithRecentActions<LeftRightAction> data) {

		double features[] = new double[getFeatureCount()];
		features[0] = 1d;
		features[1] = Math.pow(data.getPosition(), 2d);
		features[2] = Math.pow(data.getVelocity(), 2d);
		if (includeRecentActions) {
			for (int i = 0; i < PositionVelocityWithRecentActions.RECENT_ACTION_COUNT; i++) {
				features[3 + i] = data.getRecentActions().get(i).ordinal() - 1;
			}
		}
		return features;
	}

	@Override
	public int getFeatureCount() {
		return includeRecentActions ? (3 + PositionVelocityWithRecentActions.RECENT_ACTION_COUNT) : 3;

	}

}
