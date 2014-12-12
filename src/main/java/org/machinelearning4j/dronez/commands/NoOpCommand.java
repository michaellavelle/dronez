package org.machinelearning4j.dronez.commands;

import org.machinelearning4j.dronez.domain.DroneAction;
import org.machinelearning4j.dronez.domain.DroneState;
import org.machinelearning4j.dronez.domain.DroneStateWithRecentActions;
import org.machinelearning4j.dronez.domain.PositionVelocity;
import org.machinelearning4j.dronez.policy.DistanceToTargetPositionsPolicyStateMapper;
import org.machinelearning4j.dronez.policy.NoOpPolicy;
import org.ml4j.mdp.Policy;
import org.ml4j.mdp.PolicyStateMapper;
import org.ml4j.mdp.StateActionSequenceHistory;
import org.ml4j.mdp.Trajectory;

public class NoOpCommand extends AbstractPolicyCommand<DroneState, DroneStateWithRecentActions, DroneAction> {

	private StateActionSequenceHistory<?,?,DroneAction> history;
	private Trajectory<DroneState> trajectory;
	
	public NoOpCommand( int iterations,StateActionSequenceHistory<?,?,DroneAction> history)
	{
		super(iterations);
		this.trajectory = new Trajectory<DroneState>() {

			@Override
			public DroneState getTarget(long arg0) {
				return new DroneState(new PositionVelocity(0,0),new PositionVelocity(0,0),new PositionVelocity(0,0),new PositionVelocity(0,0));
			}};
;		this.history = history;
	}
	
	@Override
	public Policy<DroneStateWithRecentActions, DroneAction> getPolicy() {
		return new NoOpPolicy();
	}

	@Override
	public PolicyStateMapper<DroneState, DroneStateWithRecentActions> getPolicyStateMapper() {
		return new DistanceToTargetPositionsPolicyStateMapper(trajectory,history);
	}


}
