package org.machinelearning4j.dronez.commands;

import org.machinelearning4j.dronez.policy.NoOpForwardBackPolicy;
import org.machinelearning4j.dronez.policy.NoOpLeftRightPolicy;
import org.machinelearning4j.dronez.policy.NoOpUpDownPolicy;
import org.ml4j.dronez.ForwardBackAction;
import org.ml4j.dronez.LeftRightAction;
import org.ml4j.dronez.TargetRelativePositionWithVelocityAndRecentActions;
import org.ml4j.dronez.UpDownAction;
import org.ml4j.mdp.Policy;

public class NoOpCommandFactoryImpl extends AbstractIndependentDimensionsCommandFactory {

	public NoOpCommandFactoryImpl(int recentActionCount) {
		super(recentActionCount);
	}

	@Override
	public void updatePolicies() {
		// No-op
	}

	@Override
	protected Policy<TargetRelativePositionWithVelocityAndRecentActions<LeftRightAction>, LeftRightAction> createLeftRightDistanceToTargetPolicy() {
		return new NoOpLeftRightPolicy();
	}

	@Override
	protected Policy<TargetRelativePositionWithVelocityAndRecentActions<UpDownAction>, UpDownAction> createUpDownDistanceToTargetPolicy() {
		return new NoOpUpDownPolicy();
	}

	@Override
	protected Policy<TargetRelativePositionWithVelocityAndRecentActions<ForwardBackAction>, ForwardBackAction> createForwardBackDistanceToTargetPolicy() {
		return new NoOpForwardBackPolicy();
	}

}
