package org.machinelearning4j.dronez.commands;

import java.io.Serializable;

import org.machinelearning4j.dronez.domain.TargetRelativeDroneStateWithRecentActions;
import org.machinelearning4j.dronez.policy.ForwardBackActionIndexMapper;
import org.machinelearning4j.dronez.policy.LeftRightActionIndexMapper;
import org.machinelearning4j.dronez.policy.RelativePositionVelocityIndexMapper;
import org.machinelearning4j.dronez.policy.SpinActionIndexMapper;
import org.machinelearning4j.dronez.policy.UpDownActionIndexMapper;
import org.ml4j.dronez.DroneAction;
import org.ml4j.dronez.ForwardBackAction;
import org.ml4j.dronez.LeftRightAction;
import org.ml4j.dronez.SpinAction;
import org.ml4j.dronez.TargetRelativePositionWithVelocity;
import org.ml4j.dronez.UpDownAction;
import org.ml4j.mdp.IndexedProbabilitiesBuilder;
import org.ml4j.mdp.StateActionSequenceListener;

public class Probabilities4D implements StateActionSequenceListener<TargetRelativeDroneStateWithRecentActions,TargetRelativeDroneStateWithRecentActions, DroneAction>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IndexedProbabilitiesBuilder<TargetRelativePositionWithVelocity,LeftRightAction> leftRightProbabilitiesBuilder;
	private IndexedProbabilitiesBuilder<TargetRelativePositionWithVelocity,UpDownAction> upDownProbabilitiesBuilder;
	private IndexedProbabilitiesBuilder<TargetRelativePositionWithVelocity,ForwardBackAction> forwardBackProbabilitiesBuilder;
	private IndexedProbabilitiesBuilder<TargetRelativePositionWithVelocity,SpinAction> spinProbabilitiesBuilder;

	
	public Probabilities4D()
	{
		leftRightProbabilitiesBuilder = new IndexedProbabilitiesBuilder<TargetRelativePositionWithVelocity,LeftRightAction>(new RelativePositionVelocityIndexMapper().getIndexedValues().length, LeftRightAction.ALL_ACTIONS.length, new RelativePositionVelocityIndexMapper(), new LeftRightActionIndexMapper());
		upDownProbabilitiesBuilder = new IndexedProbabilitiesBuilder<TargetRelativePositionWithVelocity,UpDownAction>(new RelativePositionVelocityIndexMapper().getIndexedValues().length, UpDownAction.ALL_ACTIONS.length, new RelativePositionVelocityIndexMapper(), new UpDownActionIndexMapper());
		forwardBackProbabilitiesBuilder = new IndexedProbabilitiesBuilder<TargetRelativePositionWithVelocity,ForwardBackAction>(new RelativePositionVelocityIndexMapper().getIndexedValues().length, ForwardBackAction.ALL_ACTIONS.length, new RelativePositionVelocityIndexMapper(), new ForwardBackActionIndexMapper());
		spinProbabilitiesBuilder = new IndexedProbabilitiesBuilder<TargetRelativePositionWithVelocity,SpinAction>(new RelativePositionVelocityIndexMapper().getIndexedValues().length, SpinAction.ALL_ACTIONS.length, new RelativePositionVelocityIndexMapper(), new SpinActionIndexMapper());

	}
	
	public IndexedProbabilitiesBuilder<TargetRelativePositionWithVelocity, LeftRightAction> getLeftRightProbabilitiesBuilder() {
		return leftRightProbabilitiesBuilder;
	}




	public void setLeftRightProbabilitiesBuilder(
			IndexedProbabilitiesBuilder<TargetRelativePositionWithVelocity, LeftRightAction> leftRightProbabilitiesBuilder) {
		this.leftRightProbabilitiesBuilder = leftRightProbabilitiesBuilder;
	}

	public void setUpDownProbabilitiesBuilder(
			IndexedProbabilitiesBuilder<TargetRelativePositionWithVelocity, UpDownAction> upDownProbabilitiesBuilder) {
		this.upDownProbabilitiesBuilder = upDownProbabilitiesBuilder;
	}

	public void setForwardBackProbabilitiesBuilder(
			IndexedProbabilitiesBuilder<TargetRelativePositionWithVelocity, ForwardBackAction> forwardBackProbabilitiesBuilder) {
		this.forwardBackProbabilitiesBuilder = forwardBackProbabilitiesBuilder;
	}

	public void setSpinProbabilitiesBuilder(
			IndexedProbabilitiesBuilder<TargetRelativePositionWithVelocity, SpinAction> spinProbabilitiesBuilder) {
		this.spinProbabilitiesBuilder = spinProbabilitiesBuilder;
	}

	public IndexedProbabilitiesBuilder<TargetRelativePositionWithVelocity, UpDownAction> getUpDownProbabilitiesBuilder() {
		return upDownProbabilitiesBuilder;
	}




	public IndexedProbabilitiesBuilder<TargetRelativePositionWithVelocity, ForwardBackAction> getForwardBackProbabilitiesBuilder() {
		return forwardBackProbabilitiesBuilder;
	}




	public IndexedProbabilitiesBuilder<TargetRelativePositionWithVelocity, SpinAction> getSpinProbabilitiesBuilder() {
		return spinProbabilitiesBuilder;
	}




	@Override
	public void onStateActionStateSequence(long iteration, TargetRelativeDroneStateWithRecentActions initial,
			DroneAction action, TargetRelativeDroneStateWithRecentActions end) {
		
		leftRightProbabilitiesBuilder.onStateActionStateSequence(iteration, initial.getLeftRightPositionVelocity(), action.getLeftRightAction(), end.getLeftRightPositionVelocity());
		upDownProbabilitiesBuilder.onStateActionStateSequence(iteration, initial.getUpDownPositionVelocity(), action.getUpDownAction(), end.getUpDownPositionVelocity());
		forwardBackProbabilitiesBuilder.onStateActionStateSequence(iteration, initial.getForwardBackPositionVelocity(), action.getForwardBackAction(), end.getForwardBackPositionVelocity());
		spinProbabilitiesBuilder.onStateActionStateSequence(iteration, initial.getSpinPositionVelocity(), action.getSpinAction(), end.getSpinPositionVelocity());

	}

	public void print() {

		int s1 = 0;
		for (double[][] d : leftRightProbabilitiesBuilder.getProbabilities())
		{
			int a = 0;
			for (double[] d1 : d)
			{
				int s2 = 0;
				for (double d2 : d1)
				{
					if (d2 != 0.01 && d2 != 0)
					{
					System.out.println(s1 + "," + a + "," + s2 + "=" + d2);
					}
					s2++;
				}
				
				a++;
			}
			s1++;
		}
		
	}

}

