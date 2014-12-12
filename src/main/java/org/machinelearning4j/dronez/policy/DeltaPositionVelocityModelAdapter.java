    package org.machinelearning4j.dronez.policy;

import org.machinelearning4j.dronez.domain.LeftRightAction;
import org.machinelearning4j.dronez.domain.PositionDeltaWithVelocity;
import org.machinelearning4j.dronez.domain.PositionVelocityWithRecentActions;
import org.ml4j.mdp.Model;

public class DeltaPositionVelocityModelAdapter implements Model<PositionVelocityWithRecentActions<LeftRightAction>,PositionVelocityWithRecentActions<LeftRightAction>, LeftRightAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double range;
	private Model<VelocityAndRecentActions,PositionDeltaWithVelocity, LeftRightAction> deltaModel;
	
	
	public DeltaPositionVelocityModelAdapter(double range,Model<VelocityAndRecentActions, PositionDeltaWithVelocity,LeftRightAction> deltaModel)
	{
		this.range = range;
		this.deltaModel = deltaModel;
	}
	
	
	@Override
	public PositionVelocityWithRecentActions<LeftRightAction> getState(PositionVelocityWithRecentActions<LeftRightAction> state,
			LeftRightAction action) {
		PositionDeltaWithVelocity deltaPositionVelocity =  deltaModel.getState(new VelocityAndRecentActions(state.getVelocity(),state.getRecentActions()), action);
	
		LeftRightAction[] recentActions = new LeftRightAction[PositionVelocityWithRecentActions.RECENT_ACTION_COUNT];
		int recentActionCount = PositionVelocityWithRecentActions.RECENT_ACTION_COUNT;
		if (recentActionCount > 0)
		{
			for (int i = 0 ; i < recentActions.length - 1; i ++)
			{
				recentActions[i] = state.getRecentActions()[i +1];
			}
			recentActions[recentActions.length - 1] = action;
		}
		
		return new PositionVelocityWithRecentActions<LeftRightAction>(state.getPosition() - deltaPositionVelocity.getPosition(),state.getVelocity(),recentActions);
	}

	@Override
	public PositionVelocityWithRecentActions<LeftRightAction> getInitialState() {
		double vel = Math.random()  - 0.5;
		LeftRightAction[] recentActions = new LeftRightAction[PositionVelocityWithRecentActions.RECENT_ACTION_COUNT];
		for (int i = 0 ; i < recentActions.length; i ++)
		{
			recentActions[i] = LeftRightAction.values()[(int)(Math.random() * 3)];
		}
		return new PositionVelocityWithRecentActions<LeftRightAction>(Math.random() * range * 2 - range,vel,recentActions);
	}

}
