package org.machinelearning4j.dronez.commands;

import org.ml4j.dronez.ForwardBackAction;
import org.ml4j.dronez.LeftRightAction;
import org.ml4j.dronez.TargetRelativePositionWithVelocityAndRecentActions;
import org.ml4j.dronez.UpDownAction;
import org.ml4j.mdp.ContinuousStateValueFunctionGreedyPolicy;
import org.ml4j.mdp.Policy;
import org.ml4j.util.SerializationHelper;

public class LearnedContinuousInnerPolicyCommandFactoryImpl extends
		AbstractInnerOuterRegionPolicyCommandFactory {

	private static SerializationHelper serializationHelper;

	public static LearnedContinuousInnerPolicyCommandFactoryImpl create(String savedPolicyDirectory) {
		serializationHelper = new SerializationHelper(savedPolicyDirectory);
		return new LearnedContinuousInnerPolicyCommandFactoryImpl();
	}

	public static LearnedContinuousInnerPolicyCommandFactoryImpl create(ClassLoader classLoader,
			String savedPolicyDirectory) {
		serializationHelper = new SerializationHelper(classLoader, savedPolicyDirectory);
		return new LearnedContinuousInnerPolicyCommandFactoryImpl();
	}

	private LearnedContinuousInnerPolicyCommandFactoryImpl() {
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Policy<TargetRelativePositionWithVelocityAndRecentActions<LeftRightAction>, LeftRightAction> createLeftRightDistanceToTargetInnerPolicy() {
		return serializationHelper.deserialize(ContinuousStateValueFunctionGreedyPolicy.class,
				"minimiseDistanceToTargetLeftRightPolicy");
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Policy<TargetRelativePositionWithVelocityAndRecentActions<UpDownAction>, UpDownAction> createUpDownDistanceToTargetInnerPolicy() {
		return serializationHelper.deserialize(ContinuousStateValueFunctionGreedyPolicy.class, "minimiseDistanceToTargetUpDownPolicy");

	}

	@SuppressWarnings("unchecked")
	@Override
	protected Policy<TargetRelativePositionWithVelocityAndRecentActions<ForwardBackAction>, ForwardBackAction> createForwardBackDistanceToTargetInnerPolicy() {
		return serializationHelper.deserialize(ContinuousStateValueFunctionGreedyPolicy.class, "minimiseDistanceToTargetForwardBackPolicy");
	}

	@Override
	public void updatePolicies() {
		// Polices are static, no-op

	}

}
