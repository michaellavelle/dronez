package org.machinelearning4j.dronez.mock;

import java.util.ArrayList;
import java.util.List;

import org.ml4j.dronez.DeltaPositionVelocityLeftRightModelAdapter;
import org.ml4j.dronez.LeftRightAction;
import org.ml4j.dronez.PositionVelocity;
import org.ml4j.dronez.PositionVelocityWithRecentActions;
import org.ml4j.dronez.TargetRelativePositionWithVelocityAndRecentActions;
import org.ml4j.mdp.Model;

public class LearnedMockDroneLeftRightModel implements Model<PositionVelocity,PositionVelocity,LeftRightAction>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DeltaPositionVelocityLeftRightModelAdapter deltaModel;
	
	
	private List<LeftRightAction> actions = new ArrayList<LeftRightAction>();

	@Override
	// TODO
	public PositionVelocity getInitialState() {
		return null;
	}
	
	
	private double minValue;
	private double maxValue;
	
	public LearnedMockDroneLeftRightModel(double minValue,double maxValue,DeltaPositionVelocityLeftRightModelAdapter deltaModel)
	{
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.deltaModel = deltaModel;
	}
	

	@Override
	public PositionVelocity getState(PositionVelocity currentState, LeftRightAction action) {

		
		
		
		double target = 0;
		
		double targetRelativePosition = target - currentState.getPosition();
		double targetRelativeVelocity = currentState.getVelocity();
		
		//double previousVelocity = currentState.getVelocity();
		LeftRightAction[] leftRightActions = new LeftRightAction[PositionVelocityWithRecentActions.RECENT_ACTION_COUNT];  
		int specifiedActions = Math.min(actions.size(),PositionVelocityWithRecentActions.RECENT_ACTION_COUNT);
		int unspeficiedActions = PositionVelocityWithRecentActions.RECENT_ACTION_COUNT - specifiedActions;
		int ind = 0;
		for (int i = 0; i < unspeficiedActions; i++)
		{
			leftRightActions[ind++] = LeftRightAction.NO_OP;
		}
		for (int i = 0; i < specifiedActions; i++)
		{
			leftRightActions[ind++] = actions.get(actions.size() - specifiedActions + i);
		}
		
		TargetRelativePositionWithVelocityAndRecentActions<LeftRightAction> start = new TargetRelativePositionWithVelocityAndRecentActions<LeftRightAction>(targetRelativePosition,targetRelativeVelocity, leftRightActions);
		TargetRelativePositionWithVelocityAndRecentActions<LeftRightAction> end = deltaModel.getState(start, action);
		
		actions.add(action);
		
		double position = target - end.getPosition();
		
		if (position > maxValue)
		{
			position = maxValue;
		}
		if (position < minValue)
		{
			position = minValue;
		}
		
		return new PositionVelocity(position,end.getVelocity());
	
	}
	
	

}
