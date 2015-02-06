package org.machinelearning4j.dronez.policy;

import org.ml4j.dronez.ForwardBackAction;
import org.ml4j.dronez.TargetRelativePositionWithVelocityAndRecentActions;
import org.ml4j.mdp.Policy;

public class SimpleForwardBackPolicy implements Policy<TargetRelativePositionWithVelocityAndRecentActions<ForwardBackAction>,ForwardBackAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public ForwardBackAction getAction(TargetRelativePositionWithVelocityAndRecentActions<ForwardBackAction> positionVelocityRecentActions) {
		return Math.abs(positionVelocityRecentActions.getPosition()) < 0.25 ? ForwardBackAction.NO_OP : positionVelocityRecentActions.getPosition() < 0 ? ForwardBackAction.BACK : ForwardBackAction.FORWARD;
	}

}
