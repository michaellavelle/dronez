package org.machinelearning4j.dronez.policy;

import org.ml4j.dronez.LeftRightAction;
import org.ml4j.dronez.TargetRelativePositionWithVelocityAndRecentActions;
import org.ml4j.mdp.Policy;

public class NoOpLeftRightPolicy implements Policy<TargetRelativePositionWithVelocityAndRecentActions<LeftRightAction>, LeftRightAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public LeftRightAction getAction(TargetRelativePositionWithVelocityAndRecentActions<LeftRightAction> arg0) {
		return LeftRightAction.NO_OP;
	}

}
