package org.machinelearning4j.dronez.commands;

import java.io.Serializable;

import org.machinelearning4j.dronez.domain.DroneState;
import org.machinelearning4j.dronez.domain.ForwardBackAction;
import org.machinelearning4j.dronez.domain.LeftRightAction;
import org.machinelearning4j.dronez.domain.PositionVelocity;
import org.machinelearning4j.dronez.domain.PositionVelocityWithRecentActions;
import org.machinelearning4j.dronez.domain.UpDownAction;
import org.machinelearning4j.dronez.policy.ForwardBackActionIndexMapper;
import org.machinelearning4j.dronez.policy.LeftRightActionIndexMapper;
import org.machinelearning4j.dronez.policy.RelativePositionVelocityIndexMapper;
import org.machinelearning4j.dronez.policy.UpDownActionIndexMapper;
import org.ml4j.mdp.DiscreteIndexedStateMarkovDecisionProcessAdapter;
import org.ml4j.mdp.DiscreteStateMarkovDecisionProcess;
import org.ml4j.mdp.IndexMapper;
import org.ml4j.mdp.IndexedProbabilitiesBuilder;
import org.ml4j.mdp.Policy;
import org.ml4j.mdp.RandomPolicy;
import org.ml4j.mdp.RewardFunction;
import org.ml4j.mdp.Trajectory;
import org.ml4j.mdp.ZeroValueFunction;
import org.ml4j.util.SerializationHelper;

public class DiscreteStateMdpInnerPolicyCommandFactoryImpl extends AbstractInnerOuterRegionPolicyCommandFactory {

	private DiscreteIndexedStateMarkovDecisionProcessAdapter<PositionVelocity,LeftRightAction> learnedLeftRightMdp;
	private DiscreteIndexedStateMarkovDecisionProcessAdapter<PositionVelocity,UpDownAction> learnedUpDownMdp;
	private DiscreteIndexedStateMarkovDecisionProcessAdapter<PositionVelocity,ForwardBackAction> learnedForwardBackMdp;
	
	private SerializationHelper serializationHelper;
	
	public DiscreteStateMdpInnerPolicyCommandFactoryImpl(String savedPolicyDirectory)
	{
		if (savedPolicyDirectory != null)
		{
			serializationHelper = new SerializationHelper(savedPolicyDirectory);
		}
	}
	

	private Probabilities4D probabilities4D;
	
	@Override
	public void init() {
		this.probabilities4D = new Probabilities4D();
		this.learnedLeftRightMdp = createDiscreteIndexedStateSingleDimensionMarkovDecisionProcess(LeftRightAction.ALL_ACTIONS,new LeftRightActionIndexMapper(),probabilities4D.getLeftRightProbabilitiesBuilder());
		this.learnedUpDownMdp = createDiscreteIndexedStateSingleDimensionMarkovDecisionProcess(UpDownAction.ALL_ACTIONS,new UpDownActionIndexMapper(),probabilities4D.getUpDownProbabilitiesBuilder());
		this.learnedForwardBackMdp = createDiscreteIndexedStateSingleDimensionMarkovDecisionProcess(ForwardBackAction.ALL_ACTIONS,new ForwardBackActionIndexMapper(),probabilities4D.getForwardBackProbabilitiesBuilder());		super.init();
	}




	@Override
	public void updatePolicies() {
		learnedLeftRightMdp.updateProbabilitiesAndOptionallyValueFunctionAndPolicy(this.probabilities4D.getLeftRightProbabilitiesBuilder(), true);
		learnedUpDownMdp.updateProbabilitiesAndOptionallyValueFunctionAndPolicy(this.probabilities4D.getUpDownProbabilitiesBuilder(), true);
		learnedForwardBackMdp.updateProbabilitiesAndOptionallyValueFunctionAndPolicy(this.probabilities4D.getForwardBackProbabilitiesBuilder(), true);

		this.distanceToTargetPolicy = createDistanceToTargetPolicy();
		
		if (serializationHelper != null)
		{
			
			serializationHelper.serialize(learnedLeftRightMdp.getPolicy(), "leftRightPolicy");
			serializationHelper.serialize(learnedUpDownMdp.getPolicy(), "upDownPolicy");
			serializationHelper.serialize(learnedForwardBackMdp.getPolicy(), "forwardBackPolicy");
			serializationHelper.serialize(learnedLeftRightMdp.getPolicy(), "leftRightMdp");
			serializationHelper.serialize(learnedUpDownMdp.getPolicy(), "upDownMdp");
			serializationHelper.serialize(learnedForwardBackMdp.getPolicy(), "forwardBackMdp");
			serializationHelper.serialize(probabilities4D, "probabilities");

		}
	}


		@Override
		protected Policy<PositionVelocityWithRecentActions<LeftRightAction>, LeftRightAction> createLeftRightDistanceToTargetInnerPolicy() {
			return createSingleDimensionDistanceToTargetPolicy(this.learnedLeftRightMdp,LeftRightAction.ALL_ACTIONS);
		}

		@Override
		protected Policy<PositionVelocityWithRecentActions<UpDownAction>, UpDownAction> createUpDownDistanceToTargetInnerPolicy() {
			return createSingleDimensionDistanceToTargetPolicy(this.learnedUpDownMdp,UpDownAction.ALL_ACTIONS);
		}

		@Override
		protected Policy<PositionVelocityWithRecentActions<ForwardBackAction>, ForwardBackAction> createForwardBackDistanceToTargetInnerPolicy() {
			return createSingleDimensionDistanceToTargetPolicy(this.learnedForwardBackMdp,ForwardBackAction.ALL_ACTIONS);
		}

		

		@Override
		public HoverCommand createHoverCommand(DroneState hoverState,
				int iterations) {
			HoverCommand command = super.createHoverCommand(hoverState, iterations);
			command.addPolicySequenceListener(probabilities4D);
			return command;
		}




		@Override
		public TargetTrajectoryCommand createTargetTrajectoryCommand(
				Trajectory<DroneState> trajectory, int iterations) {
			TargetTrajectoryCommand command =  super.createTargetTrajectoryCommand(trajectory, iterations);
			command.addPolicySequenceListener(probabilities4D);
			return command;
			
		}




		private <A extends Serializable> DiscreteIndexedStateMarkovDecisionProcessAdapter<PositionVelocity, A> createDiscreteIndexedStateSingleDimensionMarkovDecisionProcess(A[] actions,IndexMapper<A> indexMapper,IndexedProbabilitiesBuilder<PositionVelocity,A> probabilitiesBuilder) {
			
			RelativePositionVelocityIndexMapper stateIndexMapper
			 = new RelativePositionVelocityIndexMapper();
			
			RewardFunction<PositionVelocity> rewardFunction
			 = new MinimiseTargetRelativePositionRewardFunction();
			
			Policy<PositionVelocity,A> initialPolicy
			 = new RandomPolicy<PositionVelocity,A>(actions);
			//= new SimpleLeftRightPolicy2();
		
			return new DiscreteIndexedStateMarkovDecisionProcessAdapter<PositionVelocity,A>(stateIndexMapper,indexMapper,rewardFunction,new ZeroValueFunction<Integer>(),probabilitiesBuilder,initialPolicy);
		}
		
		protected <A extends Serializable> Policy<PositionVelocityWithRecentActions<A>,A> createSingleDimensionDistanceToTargetPolicy(final DiscreteStateMarkovDecisionProcess<PositionVelocity,A> mdp,A[] actions)
		{
			final Policy<PositionVelocity, A> mdpPolicy = mdp.getPolicy();
			return new Policy<PositionVelocityWithRecentActions<A>,A>()
					{
						private static final long serialVersionUID = 1L;

						@Override
						public A getAction(
								PositionVelocityWithRecentActions<A> arg0) {
							return mdpPolicy.getAction(arg0);
						}
				
					};
		}
		
}
