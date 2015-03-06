package org.machinelearning4j.dronez.commands;

import org.machinelearning4j.dronez.policy.HybridPolicy;
import org.machinelearning4j.dronez.policy.SimpleForwardBackPolicy;
import org.machinelearning4j.dronez.policy.SimpleLeftRightPolicy;
import org.machinelearning4j.dronez.policy.SimpleUpDownPolicy;
import org.ml4j.dronez.ForwardBackAction;
import org.ml4j.dronez.LeftRightAction;
import org.ml4j.dronez.TargetRelativePositionWithVelocityAndRecentActions;
import org.ml4j.dronez.UpDownAction;
import org.ml4j.mdp.Policy;

public abstract class AbstractInnerOuterRegionPolicyCommandFactory extends AbstractIndependentDimensionsCommandFactory {

	private Policy<TargetRelativePositionWithVelocityAndRecentActions<LeftRightAction>, LeftRightAction> outerLeftRightPolicy;
	private Policy<TargetRelativePositionWithVelocityAndRecentActions<UpDownAction>, UpDownAction> outerUpDownPolicy;
	private Policy<TargetRelativePositionWithVelocityAndRecentActions<ForwardBackAction>, ForwardBackAction> outerForwardBackPolicy;

	private double boundary;

	@Override
	public void init() {
		outerLeftRightPolicy = new SimpleLeftRightPolicy();
		outerUpDownPolicy = new SimpleUpDownPolicy();
		outerForwardBackPolicy = new SimpleForwardBackPolicy();
		boundary = 2.5;
		super.init();
	}

	protected abstract Policy<TargetRelativePositionWithVelocityAndRecentActions<LeftRightAction>, LeftRightAction> createLeftRightDistanceToTargetInnerPolicy();

	protected abstract Policy<TargetRelativePositionWithVelocityAndRecentActions<UpDownAction>, UpDownAction> createUpDownDistanceToTargetInnerPolicy();

	protected abstract Policy<TargetRelativePositionWithVelocityAndRecentActions<ForwardBackAction>, ForwardBackAction> createForwardBackDistanceToTargetInnerPolicy();

	protected Policy<TargetRelativePositionWithVelocityAndRecentActions<LeftRightAction>, LeftRightAction> createLeftRightDistanceToTargetPolicy() {
		return new HybridPolicy<LeftRightAction>(createLeftRightDistanceToTargetInnerPolicy(), outerLeftRightPolicy,
				boundary);
	}

	protected Policy<TargetRelativePositionWithVelocityAndRecentActions<UpDownAction>, UpDownAction> createUpDownDistanceToTargetPolicy() {
		return new HybridPolicy<UpDownAction>(createUpDownDistanceToTargetInnerPolicy(), outerUpDownPolicy, boundary);
	}

	protected Policy<TargetRelativePositionWithVelocityAndRecentActions<ForwardBackAction>, ForwardBackAction> createForwardBackDistanceToTargetPolicy() {
		return new HybridPolicy<ForwardBackAction>(createForwardBackDistanceToTargetInnerPolicy(),
				outerForwardBackPolicy, boundary);
	}

	@Override
	public void updatePolicies() {
		// Polices are static, no-op
	}

}
