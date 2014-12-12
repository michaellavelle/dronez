package org.machinelearning4j.dronez.commands;

import org.machinelearning4j.dronez.domain.ForwardBackAction;
import org.machinelearning4j.dronez.domain.LeftRightAction;
import org.machinelearning4j.dronez.domain.PositionVelocityWithRecentActions;
import org.machinelearning4j.dronez.domain.UpDownAction;
import org.machinelearning4j.dronez.policy.HybridPolicy;
import org.machinelearning4j.dronez.policy.SimpleForwardBackPolicy;
import org.machinelearning4j.dronez.policy.SimpleLeftRightPolicy;
import org.machinelearning4j.dronez.policy.SimpleUpDownPolicy;
import org.ml4j.mdp.Policy;

public abstract class AbstractInnerOuterRegionPolicyCommandFactory extends AbstractIndependentDimensionsCommandFactory {

	private Policy<PositionVelocityWithRecentActions<LeftRightAction>,LeftRightAction> outerLeftRightPolicy;
	private Policy<PositionVelocityWithRecentActions<UpDownAction>,UpDownAction> outerUpDownPolicy;
	private Policy<PositionVelocityWithRecentActions<ForwardBackAction>,ForwardBackAction> outerForwardBackPolicy;
	
	private double boundary;
	
	@Override
	protected void init()
	{
		outerLeftRightPolicy = new SimpleLeftRightPolicy();
		outerUpDownPolicy = new SimpleUpDownPolicy();
		outerForwardBackPolicy = new SimpleForwardBackPolicy();
		boundary = 1;
		super.init();
	}

	
	protected abstract Policy<PositionVelocityWithRecentActions<LeftRightAction>,LeftRightAction> createLeftRightDistanceToTargetInnerPolicy();
	protected abstract Policy<PositionVelocityWithRecentActions<UpDownAction>,UpDownAction> createUpDownDistanceToTargetInnerPolicy();
	protected abstract Policy<PositionVelocityWithRecentActions<ForwardBackAction>,ForwardBackAction> createForwardBackDistanceToTargetInnerPolicy();

	
	protected Policy<PositionVelocityWithRecentActions<LeftRightAction>,LeftRightAction> createLeftRightDistanceToTargetPolicy()
	{
		return new HybridPolicy<LeftRightAction>(createLeftRightDistanceToTargetInnerPolicy(),outerLeftRightPolicy,boundary);
	}
	
	protected Policy<PositionVelocityWithRecentActions<UpDownAction>,UpDownAction> createUpDownDistanceToTargetPolicy()
	{
		return new HybridPolicy<UpDownAction>(createUpDownDistanceToTargetInnerPolicy(),outerUpDownPolicy,boundary);
	}
	
	protected Policy<PositionVelocityWithRecentActions<ForwardBackAction>,ForwardBackAction> createForwardBackDistanceToTargetPolicy()
	{
		return new HybridPolicy<ForwardBackAction>(createForwardBackDistanceToTargetInnerPolicy(),outerForwardBackPolicy,boundary);
	}

	@Override
	public void updatePolicies() {
		// Polices are static, no-op
	}

}
