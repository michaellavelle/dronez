package org.machinelearning4j.dronez.policy;

import org.machinelearning4j.dronez.domain.LeftRightAction;
import org.machinelearning4j.dronez.domain.PositionVelocityWithRecentActions;
import org.ml4j.mdp.Policy;

public class SimpleLeftRightPolicy implements Policy<PositionVelocityWithRecentActions<LeftRightAction>,LeftRightAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public LeftRightAction getAction(PositionVelocityWithRecentActions<LeftRightAction> positionVelocityRecentActions) {
		return Math.abs(positionVelocityRecentActions.getPosition()) < 0.25 ? LeftRightAction.NO_OP : positionVelocityRecentActions.getPosition() < 0 ? LeftRightAction.LEFT : LeftRightAction.RIGHT;
	}

}
