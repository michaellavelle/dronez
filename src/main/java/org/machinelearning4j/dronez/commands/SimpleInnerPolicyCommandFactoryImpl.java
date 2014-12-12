package org.machinelearning4j.dronez.commands;

import org.machinelearning4j.dronez.domain.ForwardBackAction;
import org.machinelearning4j.dronez.domain.LeftRightAction;
import org.machinelearning4j.dronez.domain.PositionVelocityWithRecentActions;
import org.machinelearning4j.dronez.domain.UpDownAction;
import org.machinelearning4j.dronez.policy.SimpleForwardBackPolicy;
import org.machinelearning4j.dronez.policy.SimpleLeftRightPolicy;
import org.machinelearning4j.dronez.policy.SimpleUpDownPolicy;
import org.ml4j.mdp.Policy;

public class SimpleInnerPolicyCommandFactoryImpl extends AbstractInnerOuterRegionPolicyCommandFactory {

	
	protected Policy<PositionVelocityWithRecentActions<LeftRightAction>,LeftRightAction> createLeftRightDistanceToTargetInnerPolicy()
	{
		return new SimpleLeftRightPolicy();
	}
	
	protected Policy<PositionVelocityWithRecentActions<UpDownAction>,UpDownAction> createUpDownDistanceToTargetInnerPolicy()
	{
		return new SimpleUpDownPolicy();
	}
	
	protected Policy<PositionVelocityWithRecentActions<ForwardBackAction>,ForwardBackAction> createForwardBackDistanceToTargetInnerPolicy()
	{
		return new SimpleForwardBackPolicy();
	}

	@Override
	public void updatePolicies() {
		// Polices are static, no-op
	}

}
