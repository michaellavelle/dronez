package org.machinelearning4j.dronez.commands;

import org.machinelearning4j.dronez.policy.NoOpForwardBackPolicy;
import org.machinelearning4j.dronez.policy.NoOpLeftRightPolicy;
import org.machinelearning4j.dronez.policy.NoOpUpDownPolicy;
import org.ml4j.dronez.ForwardBackAction;
import org.ml4j.dronez.LeftRightAction;
import org.ml4j.dronez.TargetRelativePositionWithVelocityAndRecentActions;
import org.ml4j.dronez.UpDownAction;
import org.ml4j.mdp.Policy;

public class NoOpInnerPolicyCommandFactoryImpl extends AbstractInnerOuterRegionPolicyCommandFactory {

	
	protected Policy<TargetRelativePositionWithVelocityAndRecentActions<LeftRightAction>,LeftRightAction> createLeftRightDistanceToTargetInnerPolicy()
	{
		return new NoOpLeftRightPolicy();
	}
	
	protected Policy<TargetRelativePositionWithVelocityAndRecentActions<UpDownAction>,UpDownAction> createUpDownDistanceToTargetInnerPolicy()
	{
		return new NoOpUpDownPolicy();
	}
	
	protected Policy<TargetRelativePositionWithVelocityAndRecentActions<ForwardBackAction>,ForwardBackAction> createForwardBackDistanceToTargetInnerPolicy()
	{
		return new NoOpForwardBackPolicy();
	}

	@Override
	public void updatePolicies() {
		// No-op
	}

}
