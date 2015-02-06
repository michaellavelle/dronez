package org.machinelearning4j.dronez.policy;

import org.machinelearning4j.dronez.domain.DroneAction;
import org.machinelearning4j.dronez.domain.TargetRelativeDroneStateWithRecentActions;
import org.ml4j.dronez.ForwardBackAction;
import org.ml4j.dronez.LeftRightAction;
import org.ml4j.dronez.SpinAction;
import org.ml4j.dronez.TargetRelativePositionWithVelocityAndRecentActions;
import org.ml4j.dronez.UpDownAction;
import org.ml4j.mdp.Policy;

public class IndependentDimensionsTargetTrajectoryPolicy implements
		Policy<TargetRelativeDroneStateWithRecentActions, DroneAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Policy<TargetRelativePositionWithVelocityAndRecentActions<LeftRightAction>,LeftRightAction> leftRightTargetTrajectoryPolicy;
	private Policy<TargetRelativePositionWithVelocityAndRecentActions<UpDownAction>,UpDownAction> upDownTargetTrajectoryPolicy;
	private Policy<TargetRelativePositionWithVelocityAndRecentActions<ForwardBackAction>,ForwardBackAction> forwardBackTargetTrajectoryPolicy;
	private Policy<TargetRelativePositionWithVelocityAndRecentActions<SpinAction>,SpinAction> spinTargetTrajectoryPolicy;

	
	public IndependentDimensionsTargetTrajectoryPolicy(
			Policy<TargetRelativePositionWithVelocityAndRecentActions<LeftRightAction>,LeftRightAction> leftRightTargetTrajectoryPolicy,
			Policy<TargetRelativePositionWithVelocityAndRecentActions<UpDownAction>,UpDownAction> upDownTargetTrajectoryPolicy,
			Policy<TargetRelativePositionWithVelocityAndRecentActions<ForwardBackAction>,ForwardBackAction> forwardBackTargetTrajectoryPolicy,
			Policy<TargetRelativePositionWithVelocityAndRecentActions<SpinAction>,SpinAction> spinTargetTrajectoryPolicy)
	{
		this.leftRightTargetTrajectoryPolicy = leftRightTargetTrajectoryPolicy;
		this.upDownTargetTrajectoryPolicy = upDownTargetTrajectoryPolicy;
		this.forwardBackTargetTrajectoryPolicy = forwardBackTargetTrajectoryPolicy;
		this.spinTargetTrajectoryPolicy = spinTargetTrajectoryPolicy;
	}
	@Override
	public DroneAction getAction(TargetRelativeDroneStateWithRecentActions droneState) {

		LeftRightAction leftRightAction = leftRightTargetTrajectoryPolicy.getAction(droneState.getLeftRightPositionVelocity());
		UpDownAction upDownAction = upDownTargetTrajectoryPolicy.getAction(droneState.getUpDownPositionVelocity());
		ForwardBackAction forwardBackAction = forwardBackTargetTrajectoryPolicy.getAction(droneState.getForwardBackPositionVelocity());
		SpinAction spinAction = spinTargetTrajectoryPolicy.getAction(droneState.getSpinPositionVelocity());

		
		return new DroneAction(leftRightAction,upDownAction,forwardBackAction,spinAction);
	}

}
