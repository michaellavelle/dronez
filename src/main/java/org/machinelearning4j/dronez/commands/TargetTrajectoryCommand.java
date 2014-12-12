package org.machinelearning4j.dronez.commands;

import org.machinelearning4j.dronez.domain.DroneAction;
import org.machinelearning4j.dronez.domain.DroneState;
import org.machinelearning4j.dronez.domain.DroneStateWithRecentActions;
import org.machinelearning4j.dronez.policy.DistanceToTargetPositionsPolicyStateMapper;
import org.ml4j.mdp.Policy;
import org.ml4j.mdp.PolicyStateMapper;
import org.ml4j.mdp.StateActionSequenceHistory;
import org.ml4j.mdp.Trajectory;

public class TargetTrajectoryCommand extends AbstractPolicyCommand<DroneState,DroneStateWithRecentActions,DroneAction> {

	private Trajectory<DroneState> trajectory;
	private Policy<DroneStateWithRecentActions,DroneAction> distanceToTargetPolicy;
	private StateActionSequenceHistory<?,?,DroneAction> history;
	
	public TargetTrajectoryCommand(Trajectory<DroneState> trajectory, int iterations,Policy<DroneStateWithRecentActions,DroneAction> distanceToTargetPolicy,StateActionSequenceHistory<?,?,DroneAction> history) {
		super(iterations);
		this.trajectory = trajectory;
		this.distanceToTargetPolicy = distanceToTargetPolicy;
		this.history = history;
	}

	@Override
	public Policy<DroneStateWithRecentActions, DroneAction> getPolicy() {
		return distanceToTargetPolicy;
	}

	@Override
	public PolicyStateMapper<DroneState, DroneStateWithRecentActions> getPolicyStateMapper() {
		return new DistanceToTargetPositionsPolicyStateMapper(trajectory,history);
	}


	public Trajectory<DroneState> getTrajectory() {
		return trajectory;
	}

	

	


}
