package org.machinelearning4j.dronez.domain;

import org.ml4j.dronez.LeftRightAction;
import org.ml4j.dronez.PositionVelocityWithRecentActions;

public class PositionVelocityWithRecentLeftRightActions extends
		PositionVelocityWithRecentActions<LeftRightAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PositionVelocityWithRecentLeftRightActions(double position,
			double vel, LeftRightAction[] recentActions) {
		super(position, vel, recentActions);
	}

}
