package org.machinelearning4j.dronez.policy;

import org.ml4j.dronez.TargetRelativePositionWithVelocityAndRecentActions;
import org.ml4j.dronez.UpDownAction;
import org.ml4j.mdp.Policy;

public class NoOpUpDownPolicy implements
		Policy<TargetRelativePositionWithVelocityAndRecentActions<UpDownAction>, UpDownAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public UpDownAction getAction(TargetRelativePositionWithVelocityAndRecentActions<UpDownAction> arg0) {
		return UpDownAction.NO_OP;
	}

}
