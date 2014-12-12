package org.machinelearning4j.dronez.policy;

import org.machinelearning4j.dronez.domain.LeftRightAction;
import org.machinelearning4j.dronez.domain.PositionVelocityWithRecentActions;
import org.ml4j.mdp.Policy;

public class NoOpLeftRightPolicy implements Policy<PositionVelocityWithRecentActions<LeftRightAction>, LeftRightAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public LeftRightAction getAction(PositionVelocityWithRecentActions<LeftRightAction> arg0) {
		return LeftRightAction.NO_OP;
	}

}
