package org.machinelearning4j.dronez.policy;

import org.ml4j.dronez.LeftRightAction;
import org.ml4j.dronez.TargetRelativePositionWithVelocityAndRecentActions;
import org.ml4j.mdp.Policy;

public class SimpleLeftRightPolicy implements Policy<TargetRelativePositionWithVelocityAndRecentActions<LeftRightAction>,LeftRightAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public LeftRightAction getAction(TargetRelativePositionWithVelocityAndRecentActions<LeftRightAction> positionVelocityRecentActions) {
		return Math.abs(positionVelocityRecentActions.getPosition()) < 0.25 ? LeftRightAction.NO_OP : positionVelocityRecentActions.getPosition() < 0 ? LeftRightAction.LEFT : LeftRightAction.RIGHT;
	}

}
