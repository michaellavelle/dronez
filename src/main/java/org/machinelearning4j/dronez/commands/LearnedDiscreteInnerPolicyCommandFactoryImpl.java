package org.machinelearning4j.dronez.commands;

import org.ml4j.dronez.ForwardBackAction;
import org.ml4j.dronez.LeftRightAction;
import org.ml4j.dronez.TargetRelativePositionWithVelocityAndRecentActions;
import org.ml4j.dronez.UpDownAction;
import org.ml4j.mdp.IndexedStatePolicyAdapter;
import org.ml4j.mdp.Policy;
import org.ml4j.util.SerializationHelper;

public class LearnedDiscreteInnerPolicyCommandFactoryImpl extends AbstractInnerOuterRegionPolicyCommandFactory {

	private static SerializationHelper serializationHelper;
	
	public static LearnedDiscreteInnerPolicyCommandFactoryImpl create(String savedPolicyDirectory)
	{
		serializationHelper = new SerializationHelper(savedPolicyDirectory);
		return new LearnedDiscreteInnerPolicyCommandFactoryImpl();
	}
	
	public static LearnedDiscreteInnerPolicyCommandFactoryImpl create(ClassLoader classLoader,String savedPolicyDirectory)
	{
		serializationHelper = new SerializationHelper(classLoader,savedPolicyDirectory);
		return new LearnedDiscreteInnerPolicyCommandFactoryImpl();
	}
	
	private LearnedDiscreteInnerPolicyCommandFactoryImpl()
	{
 	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Policy<TargetRelativePositionWithVelocityAndRecentActions<LeftRightAction>,LeftRightAction> createLeftRightDistanceToTargetInnerPolicy()
	{
		return serializationHelper.deserialize(IndexedStatePolicyAdapter.class, "leftRightPolicy");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Policy<TargetRelativePositionWithVelocityAndRecentActions<UpDownAction>,UpDownAction> createUpDownDistanceToTargetInnerPolicy()
	{
		return serializationHelper.deserialize(IndexedStatePolicyAdapter.class, "upDownPolicy");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Policy<TargetRelativePositionWithVelocityAndRecentActions<ForwardBackAction>,ForwardBackAction> createForwardBackDistanceToTargetInnerPolicy()
	{
		return serializationHelper.deserialize(IndexedStatePolicyAdapter.class, "forwardBackPolicy");
	}

	@Override
	public void updatePolicies() {
		// Polices are static, no-op

	}

}
