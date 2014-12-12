package org.machinelearning4j.dronez.policy;

import org.machinelearning4j.dronez.domain.PositionVelocityWithRecentActions;
import org.machinelearning4j.dronez.domain.UpDownAction;
import org.ml4j.mdp.Policy;

public class SimpleUpDownPolicy implements Policy<PositionVelocityWithRecentActions<UpDownAction>,UpDownAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public UpDownAction getAction(PositionVelocityWithRecentActions<UpDownAction> positionVelocityRecentActions) {
		return Math.abs(positionVelocityRecentActions.getPosition()) < 0.25 ? UpDownAction.NO_OP : positionVelocityRecentActions.getPosition() < 0 ? UpDownAction.DOWN : UpDownAction.UP;
	}

}
