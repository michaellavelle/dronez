package org.machinelearning4j.dronez.commands;

import org.machinelearning4j.dronez.domain.ForwardBackAction;
import org.machinelearning4j.dronez.domain.LeftRightAction;
import org.machinelearning4j.dronez.domain.PositionVelocityWithRecentActions;
import org.machinelearning4j.dronez.domain.UpDownAction;
import org.ml4j.mdp.IndexedStatePolicyAdapter;
import org.ml4j.mdp.Policy;
import org.ml4j.util.SerializationHelper;

public class LearnedInnerPolicyCommandFactoryImpl extends AbstractInnerOuterRegionPolicyCommandFactory {

	private static SerializationHelper serializationHelper;
	
	public static LearnedInnerPolicyCommandFactoryImpl create(String savedPolicyDirectory)
	{
		serializationHelper = new SerializationHelper(savedPolicyDirectory);
		return new LearnedInnerPolicyCommandFactoryImpl();
	}
	
	private LearnedInnerPolicyCommandFactoryImpl()
	{
 	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Policy<PositionVelocityWithRecentActions<LeftRightAction>,LeftRightAction> createLeftRightDistanceToTargetInnerPolicy()
	{
		return serializationHelper.deserialize(IndexedStatePolicyAdapter.class, "leftRightPolicy");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Policy<PositionVelocityWithRecentActions<UpDownAction>,UpDownAction> createUpDownDistanceToTargetInnerPolicy()
	{
		return serializationHelper.deserialize(IndexedStatePolicyAdapter.class, "upDownPolicy");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Policy<PositionVelocityWithRecentActions<ForwardBackAction>,ForwardBackAction> createForwardBackDistanceToTargetInnerPolicy()
	{
		return serializationHelper.deserialize(IndexedStatePolicyAdapter.class, "forwardBackPolicy");
	}

	@Override
	public void updatePolicies() {
		// Polices are static, no-op

	}

}
