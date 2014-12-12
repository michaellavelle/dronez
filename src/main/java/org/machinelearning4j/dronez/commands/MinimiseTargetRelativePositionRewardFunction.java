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

import org.machinelearning4j.dronez.domain.PositionVelocity;
import org.ml4j.mdp.RewardFunction;

/**
 * Models a RewardFunction for the objective to minimise target-relative position in a PositionVelocity
 * observation model
 * 
 * @author Michael Lavelle
 *
 */
public class MinimiseTargetRelativePositionRewardFunction implements RewardFunction<PositionVelocity> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public double getReward(PositionVelocity state) {
	
		//if (Math.abs(state.getPosition()) < 0.25d) return 2000d + 200d/(1d + Math.pow(1 + Math.abs(state.getPosition()),2d)) +1000d/(1d + Math.pow(1 + Math.abs(state.getVelocity()),2d)) ;
		//if (Math.abs(state.getPosition()) >= 0.25d) return -100d * Math.pow(1 + Math.abs(state.getPosition()),2d) ;
		//return 100d/(1d + Math.pow(1 + Math.abs(state.getPosition()),2d)) + 50d/ (1 + Math.pow(1 + Math.abs(state.getVelocity()),2d));
	 double reward =  100d/(1d + Math.pow(1 + Math.abs(state.getPosition()),2d));
	 if (Math.abs(state.getPosition()) < 0.5)
	 {
		 reward = reward * 100 + (100d/Math.pow(1 + Math.abs(state.getVelocity()),2d));
	 }
	 if (Math.abs(state.getPosition()) < 0.25)
	 {
		 reward = reward *2 ;
	 }
	 return reward;
	}

}
