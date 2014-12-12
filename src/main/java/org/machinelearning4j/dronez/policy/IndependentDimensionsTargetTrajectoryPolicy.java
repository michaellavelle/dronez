package org.machinelearning4j.dronez.policy;

import org.machinelearning4j.dronez.domain.DroneAction;
import org.machinelearning4j.dronez.domain.DroneStateWithRecentActions;
import org.machinelearning4j.dronez.domain.ForwardBackAction;
import org.machinelearning4j.dronez.domain.LeftRightAction;
import org.machinelearning4j.dronez.domain.PositionVelocityWithRecentActions;
import org.machinelearning4j.dronez.domain.SpinAction;
import org.machinelearning4j.dronez.domain.UpDownAction;
import org.ml4j.mdp.Policy;

public class IndependentDimensionsTargetTrajectoryPolicy implements
		Policy<DroneStateWithRecentActions, DroneAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Policy<PositionVelocityWithRecentActions<LeftRightAction>,LeftRightAction> leftRightTargetTrajectoryPolicy;
	private Policy<PositionVelocityWithRecentActions<UpDownAction>,UpDownAction> upDownTargetTrajectoryPolicy;
	private Policy<PositionVelocityWithRecentActions<ForwardBackAction>,ForwardBackAction> forwardBackTargetTrajectoryPolicy;
	private Policy<PositionVelocityWithRecentActions<SpinAction>,SpinAction> spinTargetTrajectoryPolicy;

	
	public IndependentDimensionsTargetTrajectoryPolicy(
			Policy<PositionVelocityWithRecentActions<LeftRightAction>,LeftRightAction> leftRightTargetTrajectoryPolicy,
			Policy<PositionVelocityWithRecentActions<UpDownAction>,UpDownAction> upDownTargetTrajectoryPolicy,
			Policy<PositionVelocityWithRecentActions<ForwardBackAction>,ForwardBackAction> forwardBackTargetTrajectoryPolicy,
			Policy<PositionVelocityWithRecentActions<SpinAction>,SpinAction> spinTargetTrajectoryPolicy)
	{
		this.leftRightTargetTrajectoryPolicy = leftRightTargetTrajectoryPolicy;
		this.upDownTargetTrajectoryPolicy = upDownTargetTrajectoryPolicy;
		this.forwardBackTargetTrajectoryPolicy = forwardBackTargetTrajectoryPolicy;
		this.spinTargetTrajectoryPolicy = spinTargetTrajectoryPolicy;
	}
	@Override
	public DroneAction getAction(DroneStateWithRecentActions droneState) {

		LeftRightAction leftRightAction = leftRightTargetTrajectoryPolicy.getAction(droneState.getLeftRightPositionVelocity());
		UpDownAction upDownAction = upDownTargetTrajectoryPolicy.getAction(droneState.getUpDownPositionVelocity());
		ForwardBackAction forwardBackAction = forwardBackTargetTrajectoryPolicy.getAction(droneState.getForwardBackPositionVelocity());
		SpinAction spinAction = spinTargetTrajectoryPolicy.getAction(droneState.getSpinPositionVelocity());

		
		return new DroneAction(leftRightAction,upDownAction,forwardBackAction,spinAction);
	}

}
