package org.machinelearning4j.dronez.policy;

import org.machinelearning4j.dronez.domain.ForwardBackAction;
import org.machinelearning4j.dronez.domain.PositionVelocityWithRecentActions;
import org.ml4j.mdp.Policy;

public class SimpleForwardBackPolicy implements Policy<PositionVelocityWithRecentActions<ForwardBackAction>,ForwardBackAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public ForwardBackAction getAction(PositionVelocityWithRecentActions<ForwardBackAction> positionVelocityRecentActions) {
		return Math.abs(positionVelocityRecentActions.getPosition()) < 0.25 ? ForwardBackAction.NO_OP : positionVelocityRecentActions.getPosition() < 0 ? ForwardBackAction.BACK : ForwardBackAction.FORWARD;
	}

}
