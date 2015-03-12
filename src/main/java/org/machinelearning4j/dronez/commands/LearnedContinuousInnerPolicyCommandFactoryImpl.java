package org.machinelearning4j.dronez.commands;

import org.machinelearning4j.dronez.policy.SimpleForwardBackPolicy;
import org.machinelearning4j.dronez.policy.SimpleUpDownPolicy;
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

	public static LearnedContinuousInnerPolicyCommandFactoryImpl create(String savedPolicyDirectory,int recentActionCount) {
		serializationHelper = new SerializationHelper(savedPolicyDirectory);
		return new LearnedContinuousInnerPolicyCommandFactoryImpl(recentActionCount);
	}

	public static LearnedContinuousInnerPolicyCommandFactoryImpl create(ClassLoader classLoader,
			String savedPolicyDirectory,int recentActionCount) {
		serializationHelper = new SerializationHelper(classLoader, savedPolicyDirectory);
		return new LearnedContinuousInnerPolicyCommandFactoryImpl(recentActionCount);
	}

	private LearnedContinuousInnerPolicyCommandFactoryImpl(int recentActionCount) {
		super(recentActionCount);

	}

	@SuppressWarnings("unchecked")
	@Override
	protected Policy<TargetRelativePositionWithVelocityAndRecentActions<LeftRightAction>, LeftRightAction> createLeftRightDistanceToTargetInnerPolicy() {
		return serializationHelper.deserialize(ContinuousStateValueFunctionGreedyPolicy.class,
				"minimiseDistanceToTargetLeftRightContinuousStatePolicy_12032015_1");
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Policy<TargetRelativePositionWithVelocityAndRecentActions<UpDownAction>, UpDownAction> createUpDownDistanceToTargetInnerPolicy() {
		//return new SimpleUpDownPolicy();
		return serializationHelper.deserialize(ContinuousStateValueFunctionGreedyPolicy.class, "minimiseDistanceToTargetUpDownContinuousStatePolicy_12032015_1");

	}

	@SuppressWarnings("unchecked")
	@Override
	protected Policy<TargetRelativePositionWithVelocityAndRecentActions<ForwardBackAction>, ForwardBackAction> createForwardBackDistanceToTargetInnerPolicy() {
		//return new SimpleForwardBackPolicy();
		return serializationHelper.deserialize(ContinuousStateValueFunctionGreedyPolicy.class, "minimiseDistanceToTargetForwardBackContinuousStatePolicy_12032015_1");
	}

	@Override
	public void updatePolicies() {
		// Polices are static, no-op

	}

}
