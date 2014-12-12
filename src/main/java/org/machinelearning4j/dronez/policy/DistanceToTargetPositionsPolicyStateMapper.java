package org.machinelearning4j.dronez.policy;

import org.machinelearning4j.dronez.domain.DroneAction;
import org.machinelearning4j.dronez.domain.DroneState;
import org.machinelearning4j.dronez.domain.DroneStateWithRecentActions;
import org.machinelearning4j.dronez.domain.ForwardBackAction;
import org.machinelearning4j.dronez.domain.LeftRightAction;
import org.machinelearning4j.dronez.domain.PositionVelocity;
import org.machinelearning4j.dronez.domain.PositionVelocityWithRecentActions;
import org.machinelearning4j.dronez.domain.SpinAction;
import org.machinelearning4j.dronez.domain.UpDownAction;
import org.machinelearning4j.dronez.trajectory.ForwardBackTrajectory;
import org.machinelearning4j.dronez.trajectory.LeftRightTrajectory;
import org.machinelearning4j.dronez.trajectory.SpinTrajectory;
import org.machinelearning4j.dronez.trajectory.UpDownTrajectory;
import org.ml4j.mdp.PolicyStateMapper;
import org.ml4j.mdp.StateActionSequenceHistory;
import org.ml4j.mdp.Trajectory;

public class DistanceToTargetPositionsPolicyStateMapper implements
		PolicyStateMapper<DroneState, DroneStateWithRecentActions> {

	private Trajectory<DroneState> trajectory;
	private StateActionSequenceHistory<?,?,DroneAction> history;
	
	public DistanceToTargetPositionsPolicyStateMapper(Trajectory<DroneState> trajectory,StateActionSequenceHistory<?,?,DroneAction> history)
	{
		this.trajectory = trajectory;
		this.history = history;
	}
	
	@Override
	public DroneStateWithRecentActions getPolicyState(DroneState droneState, long iteration) {

		PolicyStateMapper<PositionVelocity,PositionVelocityWithRecentActions<LeftRightAction>> leftRightPositionMapper
		 = new DistanceToTargetPositionPolicyStateMapper<LeftRightAction>(new LeftRightTrajectory(trajectory),history,new LeftRightActionExtractor());
		
		PolicyStateMapper<PositionVelocity,PositionVelocityWithRecentActions<UpDownAction>> upDownPositionMapper
		 = new DistanceToTargetPositionPolicyStateMapper<UpDownAction>(new UpDownTrajectory(trajectory),history,new UpDownActionExtractor());
		
		PolicyStateMapper<PositionVelocity,PositionVelocityWithRecentActions<ForwardBackAction>> forwardBackPositionMapper
		 = new DistanceToTargetPositionPolicyStateMapper<ForwardBackAction>(new ForwardBackTrajectory(trajectory),history,new ForwardBackActionExtractor());
		
		PolicyStateMapper<PositionVelocity,PositionVelocityWithRecentActions<SpinAction>> spinPositionMapper
		 = new DistanceToTargetPositionPolicyStateMapper<SpinAction>(new SpinTrajectory(trajectory),history,new SpinActionExtractor());
		
		PositionVelocityWithRecentActions<LeftRightAction> leftRightPositionVelocity = leftRightPositionMapper.getPolicyState(droneState.getLeftRightPositionVelocity(), iteration);
		PositionVelocityWithRecentActions<UpDownAction> upDownPositionVelocity = upDownPositionMapper.getPolicyState(droneState.getUpDownPositionVelocity(), iteration);
		PositionVelocityWithRecentActions<ForwardBackAction> forwardBackPositionVelocity = forwardBackPositionMapper.getPolicyState(droneState.getForwardBackPositionVelocity(), iteration);
		PositionVelocityWithRecentActions<SpinAction> spinPositionVelocity = spinPositionMapper.getPolicyState(droneState.getSpinPositionVelocity(), iteration);

		return new DroneStateWithRecentActions(leftRightPositionVelocity,upDownPositionVelocity,forwardBackPositionVelocity,spinPositionVelocity);
	}

}
