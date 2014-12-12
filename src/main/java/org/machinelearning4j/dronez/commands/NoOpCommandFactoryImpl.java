package org.machinelearning4j.dronez.commands;

import org.machinelearning4j.dronez.domain.ForwardBackAction;
import org.machinelearning4j.dronez.domain.LeftRightAction;
import org.machinelearning4j.dronez.domain.PositionVelocityWithRecentActions;
import org.machinelearning4j.dronez.domain.UpDownAction;
import org.machinelearning4j.dronez.policy.NoOpForwardBackPolicy;
import org.machinelearning4j.dronez.policy.NoOpLeftRightPolicy;
import org.machinelearning4j.dronez.policy.NoOpUpDownPolicy;
import org.ml4j.mdp.Policy;

public class NoOpCommandFactoryImpl extends AbstractIndependentDimensionsCommandFactory {

	@Override
	public void updatePolicies() {
		// No-op
	}

	@Override
	protected Policy<PositionVelocityWithRecentActions<LeftRightAction>, LeftRightAction> createLeftRightDistanceToTargetPolicy() {
		return new NoOpLeftRightPolicy();
	}

	@Override
	protected Policy<PositionVelocityWithRecentActions<UpDownAction>, UpDownAction> createUpDownDistanceToTargetPolicy() {
		return new NoOpUpDownPolicy();
	}

	@Override
	protected Policy<PositionVelocityWithRecentActions<ForwardBackAction>, ForwardBackAction> createForwardBackDistanceToTargetPolicy() {
		return new NoOpForwardBackPolicy();
	}

	
}
