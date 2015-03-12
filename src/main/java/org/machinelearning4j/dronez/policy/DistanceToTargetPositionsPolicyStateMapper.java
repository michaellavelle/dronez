package org.machinelearning4j.dronez.policy;

import org.machinelearning4j.dronez.trajectory.ForwardBackTrajectory;
import org.machinelearning4j.dronez.trajectory.LeftRightTrajectory;
import org.machinelearning4j.dronez.trajectory.SpinTrajectory;
import org.machinelearning4j.dronez.trajectory.UpDownTrajectory;
import org.ml4j.dronez.DroneAction;
import org.ml4j.dronez.DroneState;
import org.ml4j.dronez.ForwardBackAction;
import org.ml4j.dronez.ForwardBackActionExtractor;
import org.ml4j.dronez.LeftRightAction;
import org.ml4j.dronez.LeftRightActionExtractor;
import org.ml4j.dronez.PositionVelocity;
import org.ml4j.dronez.SpinAction;
import org.ml4j.dronez.TargetRelativeDroneStateWithRecentActions;
import org.ml4j.dronez.TargetRelativePositionWithVelocityAndRecentActions;
import org.ml4j.dronez.UpDownAction;
import org.ml4j.dronez.UpDownActionExtractor;
import org.ml4j.mdp.PolicyStateMapper;
import org.ml4j.mdp.StateActionSequenceHistory;
import org.ml4j.mdp.Trajectory;

public class DistanceToTargetPositionsPolicyStateMapper implements
		PolicyStateMapper<DroneState, TargetRelativeDroneStateWithRecentActions> {

	private Trajectory<DroneState> trajectory;
	private StateActionSequenceHistory<?, ?, DroneAction> history;
	private int recentActionCount;
	
	public DistanceToTargetPositionsPolicyStateMapper(Trajectory<DroneState> trajectory,
			StateActionSequenceHistory<?, ?, DroneAction> history,int recentActionCount) {
		this.trajectory = trajectory;
		this.history = history;
		this.recentActionCount = recentActionCount;
	}

	@Override
	public TargetRelativeDroneStateWithRecentActions getPolicyState(DroneState droneState, long iteration) {

		PolicyStateMapper<PositionVelocity, TargetRelativePositionWithVelocityAndRecentActions<LeftRightAction>> leftRightPositionMapper = new DistanceToTargetPositionPolicyStateMapper<LeftRightAction>(
				new LeftRightTrajectory(trajectory), history, new LeftRightActionExtractor(),recentActionCount);

		PolicyStateMapper<PositionVelocity, TargetRelativePositionWithVelocityAndRecentActions<UpDownAction>> upDownPositionMapper = new DistanceToTargetPositionPolicyStateMapper<UpDownAction>(
				new UpDownTrajectory(trajectory), history, new UpDownActionExtractor(),recentActionCount);

		PolicyStateMapper<PositionVelocity, TargetRelativePositionWithVelocityAndRecentActions<ForwardBackAction>> forwardBackPositionMapper = new DistanceToTargetPositionPolicyStateMapper<ForwardBackAction>(
				new ForwardBackTrajectory(trajectory), history, new ForwardBackActionExtractor(),recentActionCount);

		PolicyStateMapper<PositionVelocity, TargetRelativePositionWithVelocityAndRecentActions<SpinAction>> spinPositionMapper = new DistanceToTargetPositionPolicyStateMapper<SpinAction>(
				new SpinTrajectory(trajectory), history, new SpinActionExtractor(),recentActionCount);

		TargetRelativePositionWithVelocityAndRecentActions<LeftRightAction> leftRightPositionVelocity = leftRightPositionMapper
				.getPolicyState(droneState.getLeftRightPositionVelocity(), iteration);
		TargetRelativePositionWithVelocityAndRecentActions<UpDownAction> upDownPositionVelocity = upDownPositionMapper
				.getPolicyState(droneState.getUpDownPositionVelocity(), iteration);
		TargetRelativePositionWithVelocityAndRecentActions<ForwardBackAction> forwardBackPositionVelocity = forwardBackPositionMapper
				.getPolicyState(droneState.getForwardBackPositionVelocity(), iteration);
		TargetRelativePositionWithVelocityAndRecentActions<SpinAction> spinPositionVelocity = spinPositionMapper
				.getPolicyState(droneState.getSpinPositionVelocity(), iteration);

		return new TargetRelativeDroneStateWithRecentActions(leftRightPositionVelocity, upDownPositionVelocity,
				forwardBackPositionVelocity, spinPositionVelocity);
	}

}
