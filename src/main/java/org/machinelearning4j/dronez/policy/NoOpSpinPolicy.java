package org.machinelearning4j.dronez.policy;

import org.ml4j.dronez.SpinAction;
import org.ml4j.dronez.TargetRelativePositionWithVelocityAndRecentActions;
import org.ml4j.mdp.Policy;

public class NoOpSpinPolicy implements
		Policy<TargetRelativePositionWithVelocityAndRecentActions<SpinAction>, SpinAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public SpinAction getAction(TargetRelativePositionWithVelocityAndRecentActions<SpinAction> arg0) {
		return SpinAction.NO_OP;
	}

}
