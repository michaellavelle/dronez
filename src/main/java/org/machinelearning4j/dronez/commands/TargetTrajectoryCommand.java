package org.machinelearning4j.dronez.commands;

import org.machinelearning4j.dronez.policy.DistanceToTargetPositionsPolicyStateMapper;
import org.ml4j.dronez.DroneAction;
import org.ml4j.dronez.DroneState;
import org.ml4j.dronez.TargetRelativeDroneStateWithRecentActions;
import org.ml4j.mdp.Policy;
import org.ml4j.mdp.PolicyStateMapper;
import org.ml4j.mdp.StateActionSequenceHistory;
import org.ml4j.mdp.Trajectory;

public class TargetTrajectoryCommand extends AbstractPolicyCommand<DroneState,TargetRelativeDroneStateWithRecentActions,DroneAction> {

	private Trajectory<DroneState> trajectory;
	private Policy<TargetRelativeDroneStateWithRecentActions,DroneAction> distanceToTargetPolicy;
	private StateActionSequenceHistory<?,?,DroneAction> history;
	
	public TargetTrajectoryCommand(Trajectory<DroneState> trajectory, int iterations,Policy<TargetRelativeDroneStateWithRecentActions,DroneAction> distanceToTargetPolicy,StateActionSequenceHistory<?,?,DroneAction> history) {
		super(iterations);
		this.trajectory = trajectory;
		this.distanceToTargetPolicy = distanceToTargetPolicy;
		this.history = history;
	}

	@Override
	public Policy<TargetRelativeDroneStateWithRecentActions, DroneAction> getPolicy() {
		return distanceToTargetPolicy;
	}

	@Override
	public PolicyStateMapper<DroneState, TargetRelativeDroneStateWithRecentActions> getPolicyStateMapper() {
		return new DistanceToTargetPositionsPolicyStateMapper(trajectory,history);
	}


	public Trajectory<DroneState> getTrajectory() {
		return trajectory;
	}

	

	


}
