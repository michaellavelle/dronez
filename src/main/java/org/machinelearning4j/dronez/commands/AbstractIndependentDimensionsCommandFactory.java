package org.machinelearning4j.dronez.commands;

import org.machinelearning4j.dronez.domain.DroneAction;
import org.machinelearning4j.dronez.domain.TargetRelativeDroneStateWithRecentActions;
import org.machinelearning4j.dronez.policy.IndependentDimensionsTargetTrajectoryPolicy;
import org.machinelearning4j.dronez.policy.NoOpSpinPolicy;
import org.ml4j.dronez.ForwardBackAction;
import org.ml4j.dronez.LeftRightAction;
import org.ml4j.dronez.SpinAction;
import org.ml4j.dronez.TargetRelativePositionWithVelocityAndRecentActions;
import org.ml4j.dronez.UpDownAction;
import org.ml4j.mdp.Policy;

public abstract class AbstractIndependentDimensionsCommandFactory extends
		AbstractCommandFactory {

	
	protected Policy<TargetRelativeDroneStateWithRecentActions, DroneAction> createDistanceToTargetPolicy() {
		return new IndependentDimensionsTargetTrajectoryPolicy(createLeftRightDistanceToTargetPolicy(),
				createUpDownDistanceToTargetPolicy(),createForwardBackDistanceToTargetPolicy(),createSpinDistanceToTargetPolicy());
	}
		
	protected abstract Policy<TargetRelativePositionWithVelocityAndRecentActions<LeftRightAction>,LeftRightAction> createLeftRightDistanceToTargetPolicy();

	protected Policy<TargetRelativePositionWithVelocityAndRecentActions<SpinAction>,SpinAction> createSpinDistanceToTargetPolicy()
	{
		return new NoOpSpinPolicy();
	}
	
	
	protected abstract Policy<TargetRelativePositionWithVelocityAndRecentActions<UpDownAction>,UpDownAction> createUpDownDistanceToTargetPolicy();
		
	protected abstract Policy<TargetRelativePositionWithVelocityAndRecentActions<ForwardBackAction>,ForwardBackAction> createForwardBackDistanceToTargetPolicy();

}
