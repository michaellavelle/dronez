package org.machinelearning4j.dronez.commands;

import org.machinelearning4j.dronez.policy.RelativePositionVelocityIndexMapper;
import org.ml4j.dronez.DroneState;
import org.ml4j.dronez.ForwardBackAction;
import org.ml4j.dronez.ForwardBackActionIndexMapper;
import org.ml4j.dronez.LeftRightAction;
import org.ml4j.dronez.LeftRightActionIndexMapper;
import org.ml4j.dronez.NumericAction;
import org.ml4j.dronez.TargetRelativePositionWithVelocity;
import org.ml4j.dronez.TargetRelativePositionWithVelocityAndRecentActions;
import org.ml4j.dronez.UpDownAction;
import org.ml4j.dronez.UpDownActionIndexMapper;
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

	private DiscreteIndexedStateMarkovDecisionProcessAdapter<TargetRelativePositionWithVelocity,LeftRightAction> learnedLeftRightMdp;
	private DiscreteIndexedStateMarkovDecisionProcessAdapter<TargetRelativePositionWithVelocity,UpDownAction> learnedUpDownMdp;
	private DiscreteIndexedStateMarkovDecisionProcessAdapter<TargetRelativePositionWithVelocity,ForwardBackAction> learnedForwardBackMdp;
	
	private SerializationHelper serializationHelper;
	
	public DiscreteStateMdpInnerPolicyCommandFactoryImpl(String savedPolicyDirectory)
	{
		if (savedPolicyDirectory != null)
		{
			serializationHelper = new SerializationHelper(savedPolicyDirectory);
		}
	}
	
	public DiscreteStateMdpInnerPolicyCommandFactoryImpl(ClassLoader classLoader,String savedPolicyDirectory)
	{
		if (savedPolicyDirectory != null && classLoader != null)
		{
			serializationHelper = new SerializationHelper(classLoader,savedPolicyDirectory);
		}
	}
	

	private Probabilities4D probabilities4D;
	
	@Override
	public void init() {
		this.probabilities4D = new Probabilities4D();
		this.learnedLeftRightMdp = createDiscreteIndexedStateSingleDimensionMarkovDecisionProcess(LeftRightAction.ALL_ACTIONS,new LeftRightActionIndexMapper(),probabilities4D.getLeftRightProbabilitiesBuilder());
		this.learnedUpDownMdp = createDiscreteIndexedStateSingleDimensionMarkovDecisionProcess(UpDownAction.ALL_ACTIONS,new UpDownActionIndexMapper(),probabilities4D.getUpDownProbabilitiesBuilder());
		this.learnedForwardBackMdp = createDiscreteIndexedStateSingleDimensionMarkovDecisionProcess(ForwardBackAction.ALL_ACTIONS,new ForwardBackActionIndexMapper(),probabilities4D.getForwardBackProbabilitiesBuilder());		super.init();
		super.init();
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
			serializationHelper.serialize(learnedLeftRightMdp, "leftRightMdp");
			serializationHelper.serialize(learnedUpDownMdp, "upDownMdp");
			serializationHelper.serialize(learnedForwardBackMdp, "forwardBackMdp");
			serializationHelper.serialize(probabilities4D, "probabilities");
			serializationHelper.serialize(history, "stateActionHistory");

		}
	}


		@Override
		protected Policy<TargetRelativePositionWithVelocityAndRecentActions<LeftRightAction>, LeftRightAction> createLeftRightDistanceToTargetInnerPolicy() {
			return createSingleDimensionDistanceToTargetPolicy(this.learnedLeftRightMdp,LeftRightAction.ALL_ACTIONS);
		}

		@Override
		protected Policy<TargetRelativePositionWithVelocityAndRecentActions<UpDownAction>, UpDownAction> createUpDownDistanceToTargetInnerPolicy() {
			return createSingleDimensionDistanceToTargetPolicy(this.learnedUpDownMdp,UpDownAction.ALL_ACTIONS);
		}

		@Override
		protected Policy<TargetRelativePositionWithVelocityAndRecentActions<ForwardBackAction>, ForwardBackAction> createForwardBackDistanceToTargetInnerPolicy() {
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




		private <A extends NumericAction> DiscreteIndexedStateMarkovDecisionProcessAdapter<TargetRelativePositionWithVelocity, A> createDiscreteIndexedStateSingleDimensionMarkovDecisionProcess(A[] actions,IndexMapper<A> indexMapper,IndexedProbabilitiesBuilder<TargetRelativePositionWithVelocity,A> probabilitiesBuilder) {
			
			RelativePositionVelocityIndexMapper stateIndexMapper
			 = new RelativePositionVelocityIndexMapper();
			
			RewardFunction<TargetRelativePositionWithVelocity> rewardFunction
			 = new MinimiseTargetRelativePositionRewardFunction();
			
			Policy<TargetRelativePositionWithVelocity,A> initialPolicy
			 = new RandomPolicy<TargetRelativePositionWithVelocity,A>(actions);
			//= new SimpleLeftRightPolicy2();
		
			return new DiscreteIndexedStateMarkovDecisionProcessAdapter<TargetRelativePositionWithVelocity,A>(stateIndexMapper,indexMapper,rewardFunction,new ZeroValueFunction<Integer>(),probabilitiesBuilder,initialPolicy);
		}
		
		protected <A extends NumericAction> Policy<TargetRelativePositionWithVelocityAndRecentActions<A>,A> createSingleDimensionDistanceToTargetPolicy(final DiscreteStateMarkovDecisionProcess<TargetRelativePositionWithVelocity,A> mdp,A[] actions)
		{
			final Policy<TargetRelativePositionWithVelocity, A> mdpPolicy = mdp.getPolicy();
			return new Policy<TargetRelativePositionWithVelocityAndRecentActions<A>,A>()
					{
						private static final long serialVersionUID = 1L;

						@Override
						public A getAction(
								TargetRelativePositionWithVelocityAndRecentActions<A> arg0) {
							return mdpPolicy.getAction(arg0);
						}

						
				
					};
		}
		
}
