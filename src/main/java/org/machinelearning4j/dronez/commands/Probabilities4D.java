package org.machinelearning4j.dronez.commands;

import java.io.Serializable;

import org.machinelearning4j.dronez.domain.DroneAction;
import org.machinelearning4j.dronez.domain.DroneStateWithRecentActions;
import org.machinelearning4j.dronez.domain.ForwardBackAction;
import org.machinelearning4j.dronez.domain.LeftRightAction;
import org.machinelearning4j.dronez.domain.PositionVelocity;
import org.machinelearning4j.dronez.domain.SpinAction;
import org.machinelearning4j.dronez.domain.UpDownAction;
import org.machinelearning4j.dronez.policy.ForwardBackActionIndexMapper;
import org.machinelearning4j.dronez.policy.LeftRightActionIndexMapper;
import org.machinelearning4j.dronez.policy.RelativePositionVelocityIndexMapper;
import org.machinelearning4j.dronez.policy.SpinActionIndexMapper;
import org.machinelearning4j.dronez.policy.UpDownActionIndexMapper;
import org.ml4j.mdp.IndexedProbabilitiesBuilder;
import org.ml4j.mdp.StateActionSequenceListener;

public class Probabilities4D implements StateActionSequenceListener<DroneStateWithRecentActions,DroneStateWithRecentActions, DroneAction>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IndexedProbabilitiesBuilder<PositionVelocity,LeftRightAction> leftRightProbabilitiesBuilder;
	private IndexedProbabilitiesBuilder<PositionVelocity,UpDownAction> upDownProbabilitiesBuilder;
	private IndexedProbabilitiesBuilder<PositionVelocity,ForwardBackAction> forwardBackProbabilitiesBuilder;
	private IndexedProbabilitiesBuilder<PositionVelocity,SpinAction> spinProbabilitiesBuilder;

	
	public Probabilities4D()
	{
		leftRightProbabilitiesBuilder = new IndexedProbabilitiesBuilder<PositionVelocity,LeftRightAction>(new RelativePositionVelocityIndexMapper().getIndexedValues().length, LeftRightAction.ALL_ACTIONS.length, new RelativePositionVelocityIndexMapper(), new LeftRightActionIndexMapper());
		upDownProbabilitiesBuilder = new IndexedProbabilitiesBuilder<PositionVelocity,UpDownAction>(new RelativePositionVelocityIndexMapper().getIndexedValues().length, UpDownAction.ALL_ACTIONS.length, new RelativePositionVelocityIndexMapper(), new UpDownActionIndexMapper());
		forwardBackProbabilitiesBuilder = new IndexedProbabilitiesBuilder<PositionVelocity,ForwardBackAction>(new RelativePositionVelocityIndexMapper().getIndexedValues().length, ForwardBackAction.ALL_ACTIONS.length, new RelativePositionVelocityIndexMapper(), new ForwardBackActionIndexMapper());
		spinProbabilitiesBuilder = new IndexedProbabilitiesBuilder<PositionVelocity,SpinAction>(new RelativePositionVelocityIndexMapper().getIndexedValues().length, SpinAction.ALL_ACTIONS.length, new RelativePositionVelocityIndexMapper(), new SpinActionIndexMapper());

	}
	
	public IndexedProbabilitiesBuilder<PositionVelocity, LeftRightAction> getLeftRightProbabilitiesBuilder() {
		return leftRightProbabilitiesBuilder;
	}




	public void setLeftRightProbabilitiesBuilder(
			IndexedProbabilitiesBuilder<PositionVelocity, LeftRightAction> leftRightProbabilitiesBuilder) {
		this.leftRightProbabilitiesBuilder = leftRightProbabilitiesBuilder;
	}

	public void setUpDownProbabilitiesBuilder(
			IndexedProbabilitiesBuilder<PositionVelocity, UpDownAction> upDownProbabilitiesBuilder) {
		this.upDownProbabilitiesBuilder = upDownProbabilitiesBuilder;
	}

	public void setForwardBackProbabilitiesBuilder(
			IndexedProbabilitiesBuilder<PositionVelocity, ForwardBackAction> forwardBackProbabilitiesBuilder) {
		this.forwardBackProbabilitiesBuilder = forwardBackProbabilitiesBuilder;
	}

	public void setSpinProbabilitiesBuilder(
			IndexedProbabilitiesBuilder<PositionVelocity, SpinAction> spinProbabilitiesBuilder) {
		this.spinProbabilitiesBuilder = spinProbabilitiesBuilder;
	}

	public IndexedProbabilitiesBuilder<PositionVelocity, UpDownAction> getUpDownProbabilitiesBuilder() {
		return upDownProbabilitiesBuilder;
	}




	public IndexedProbabilitiesBuilder<PositionVelocity, ForwardBackAction> getForwardBackProbabilitiesBuilder() {
		return forwardBackProbabilitiesBuilder;
	}




	public IndexedProbabilitiesBuilder<PositionVelocity, SpinAction> getSpinProbabilitiesBuilder() {
		return spinProbabilitiesBuilder;
	}




	@Override
	public void onStateActionStateSequence(long iteration, DroneStateWithRecentActions initial,
			DroneAction action, DroneStateWithRecentActions end) {
		
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

