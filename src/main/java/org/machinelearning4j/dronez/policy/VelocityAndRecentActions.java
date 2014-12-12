package org.machinelearning4j.dronez.policy;

import java.io.Serializable;

import org.machinelearning4j.dronez.domain.LeftRightAction;
import org.machinelearning4j.dronez.domain.PositionVelocityWithRecentActions;

public class VelocityAndRecentActions implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PositionVelocityWithRecentActions<LeftRightAction> helper;
	
	
	public VelocityAndRecentActions(double vel,LeftRightAction[] recentActions) {
		this.helper = new PositionVelocityWithRecentActions<LeftRightAction>(0,vel,recentActions);
	}

	
	public double getVelocity()
	{
		return helper.getVelocity();
	}
	
	public LeftRightAction[] getRecentActions()
	{
		return helper.getRecentActions();
	}

}
