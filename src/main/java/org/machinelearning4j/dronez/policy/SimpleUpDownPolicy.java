package org.machinelearning4j.dronez.policy;

import org.ml4j.dronez.TargetRelativePositionWithVelocityAndRecentActions;
import org.ml4j.dronez.UpDownAction;
import org.ml4j.mdp.Policy;

public class SimpleUpDownPolicy implements
		Policy<TargetRelativePositionWithVelocityAndRecentActions<UpDownAction>, UpDownAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public UpDownAction getAction(
			TargetRelativePositionWithVelocityAndRecentActions<UpDownAction> positionVelocityRecentActions) {
		return Math.abs(positionVelocityRecentActions.getPosition()) < 0.25 ? UpDownAction.NO_OP
				: positionVelocityRecentActions.getPosition() < 0 ? UpDownAction.DOWN : UpDownAction.UP;
	}

}
