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
package org.machinelearning4j.dronez.policy;

import java.io.Serializable;
import java.util.List;

import org.machinelearning4j.dronez.domain.DroneAction;
import org.machinelearning4j.dronez.domain.ForwardBackAction;
import org.machinelearning4j.dronez.domain.LeftRightAction;
import org.machinelearning4j.dronez.domain.PositionVelocity;
import org.machinelearning4j.dronez.domain.PositionVelocityWithRecentActions;
import org.machinelearning4j.dronez.domain.SpinAction;
import org.machinelearning4j.dronez.domain.UpDownAction;
import org.ml4j.mdp.PolicyStateMapper;
import org.ml4j.mdp.StateActionSequenceHistory;
import org.ml4j.mdp.Trajectory;

/**
 * Maps an absolute position to a position relative to an iteration-dependent target specified
 * by a given position Trajectory.  Does not modify velocity state.
 * 
 * @author Michael Lavelle
 *
 */
public class DistanceToTargetPositionPolicyStateMapper<A extends Serializable> implements PolicyStateMapper<PositionVelocity,PositionVelocityWithRecentActions<A>> {

	private Trajectory<Double> targetPositionTrajectory;
	private StateActionSequenceHistory<?,?,DroneAction> history;
	private ActionExtractor<A> actionExtractor;
	
	public DistanceToTargetPositionPolicyStateMapper(Trajectory<Double> targetPositionTrajectory,StateActionSequenceHistory<?,?,DroneAction> history,ActionExtractor<A> actionExtractor)
	{
		this.targetPositionTrajectory = targetPositionTrajectory;
		this.history = history;
		this.actionExtractor = actionExtractor;
	}
	
	@Override
	public PositionVelocityWithRecentActions<A> getPolicyState(PositionVelocity state,long iteration) {
		double distanceToTargetPosition = targetPositionTrajectory.getTarget(iteration) - state.getPosition();
		
		List<DroneAction> actions = history.getRecentActions(PositionVelocityWithRecentActions.RECENT_ACTION_COUNT,new DroneAction(LeftRightAction.NO_OP,UpDownAction.NO_OP,ForwardBackAction.NO_OP,SpinAction.NO_OP));
		
		return new PositionVelocityWithRecentActions<A>(distanceToTargetPosition,state.getVelocity(),actionExtractor.getActions(actions));
	}

}
