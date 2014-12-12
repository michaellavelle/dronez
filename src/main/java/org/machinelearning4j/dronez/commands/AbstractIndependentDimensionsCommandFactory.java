package org.machinelearning4j.dronez.commands;

import org.machinelearning4j.dronez.domain.DroneAction;
import org.machinelearning4j.dronez.domain.DroneStateWithRecentActions;
import org.machinelearning4j.dronez.domain.ForwardBackAction;
import org.machinelearning4j.dronez.domain.LeftRightAction;
import org.machinelearning4j.dronez.domain.PositionVelocityWithRecentActions;
import org.machinelearning4j.dronez.domain.SpinAction;
import org.machinelearning4j.dronez.domain.UpDownAction;
import org.machinelearning4j.dronez.policy.IndependentDimensionsTargetTrajectoryPolicy;
import org.machinelearning4j.dronez.policy.NoOpSpinPolicy;
import org.ml4j.mdp.Policy;

public abstract class AbstractIndependentDimensionsCommandFactory extends
		AbstractCommandFactory {

	
	protected Policy<DroneStateWithRecentActions, DroneAction> createDistanceToTargetPolicy() {
		return new IndependentDimensionsTargetTrajectoryPolicy(createLeftRightDistanceToTargetPolicy(),
				createUpDownDistanceToTargetPolicy(),createForwardBackDistanceToTargetPolicy(),createSpinDistanceToTargetPolicy());
	}
		
	protected abstract Policy<PositionVelocityWithRecentActions<LeftRightAction>,LeftRightAction> createLeftRightDistanceToTargetPolicy();

	protected Policy<PositionVelocityWithRecentActions<SpinAction>,SpinAction> createSpinDistanceToTargetPolicy()
	{
		return new NoOpSpinPolicy();
	}
	
	
	protected abstract Policy<PositionVelocityWithRecentActions<UpDownAction>,UpDownAction> createUpDownDistanceToTargetPolicy();
		
	protected abstract Policy<PositionVelocityWithRecentActions<ForwardBackAction>,ForwardBackAction> createForwardBackDistanceToTargetPolicy();

}
