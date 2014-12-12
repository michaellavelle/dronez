package org.machinelearning4j.dronez.commands;

import org.machinelearning4j.dronez.domain.ForwardBackAction;
import org.machinelearning4j.dronez.domain.LeftRightAction;
import org.machinelearning4j.dronez.domain.PositionVelocityWithRecentActions;
import org.machinelearning4j.dronez.domain.UpDownAction;
import org.machinelearning4j.dronez.policy.NoOpForwardBackPolicy;
import org.machinelearning4j.dronez.policy.NoOpLeftRightPolicy;
import org.machinelearning4j.dronez.policy.NoOpUpDownPolicy;
import org.ml4j.mdp.Policy;

public class NoOpInnerPolicyCommandFactoryImpl extends AbstractInnerOuterRegionPolicyCommandFactory {

	
	protected Policy<PositionVelocityWithRecentActions<LeftRightAction>,LeftRightAction> createLeftRightDistanceToTargetInnerPolicy()
	{
		return new NoOpLeftRightPolicy();
	}
	
	protected Policy<PositionVelocityWithRecentActions<UpDownAction>,UpDownAction> createUpDownDistanceToTargetInnerPolicy()
	{
		return new NoOpUpDownPolicy();
	}
	
	protected Policy<PositionVelocityWithRecentActions<ForwardBackAction>,ForwardBackAction> createForwardBackDistanceToTargetInnerPolicy()
	{
		return new NoOpForwardBackPolicy();
	}

	@Override
	public void updatePolicies() {
		// No-op
	}

}
