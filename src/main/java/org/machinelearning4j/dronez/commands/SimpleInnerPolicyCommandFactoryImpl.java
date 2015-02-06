package org.machinelearning4j.dronez.commands;

import org.machinelearning4j.dronez.policy.SimpleForwardBackPolicy;
import org.machinelearning4j.dronez.policy.SimpleLeftRightPolicy;
import org.machinelearning4j.dronez.policy.SimpleUpDownPolicy;
import org.ml4j.dronez.ForwardBackAction;
import org.ml4j.dronez.LeftRightAction;
import org.ml4j.dronez.TargetRelativePositionWithVelocityAndRecentActions;
import org.ml4j.dronez.UpDownAction;
import org.ml4j.mdp.Policy;

public class SimpleInnerPolicyCommandFactoryImpl extends AbstractInnerOuterRegionPolicyCommandFactory {

	
	protected Policy<TargetRelativePositionWithVelocityAndRecentActions<LeftRightAction>,LeftRightAction> createLeftRightDistanceToTargetInnerPolicy()
	{
		return new SimpleLeftRightPolicy();
	}
	
	protected Policy<TargetRelativePositionWithVelocityAndRecentActions<UpDownAction>,UpDownAction> createUpDownDistanceToTargetInnerPolicy()
	{
		return new SimpleUpDownPolicy();
	}
	
	protected Policy<TargetRelativePositionWithVelocityAndRecentActions<ForwardBackAction>,ForwardBackAction> createForwardBackDistanceToTargetInnerPolicy()
	{
		return new SimpleForwardBackPolicy();
	}

	@Override
	public void updatePolicies() {
		// Polices are static, no-op
	}

}
