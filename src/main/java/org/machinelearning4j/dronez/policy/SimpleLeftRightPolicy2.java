package org.machinelearning4j.dronez.policy;

import org.machinelearning4j.dronez.domain.LeftRightAction;
import org.machinelearning4j.dronez.domain.PositionVelocity;
import org.ml4j.mdp.Policy;

public class SimpleLeftRightPolicy2 implements Policy<PositionVelocity,LeftRightAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public LeftRightAction getAction(PositionVelocity positionVelocityRecentActions) {
		return Math.abs(positionVelocityRecentActions.getPosition()) < 0.25 ? LeftRightAction.NO_OP : positionVelocityRecentActions.getPosition() < 0 ? LeftRightAction.LEFT : LeftRightAction.RIGHT;
	}

}
