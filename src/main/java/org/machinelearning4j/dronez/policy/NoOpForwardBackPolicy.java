package org.machinelearning4j.dronez.policy;

import org.ml4j.dronez.ForwardBackAction;
import org.ml4j.dronez.TargetRelativePositionWithVelocityAndRecentActions;
import org.ml4j.mdp.Policy;

public class NoOpForwardBackPolicy implements
		Policy<TargetRelativePositionWithVelocityAndRecentActions<ForwardBackAction>, ForwardBackAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public ForwardBackAction getAction(TargetRelativePositionWithVelocityAndRecentActions<ForwardBackAction> arg0) {
		return ForwardBackAction.NO_OP;
	}

}
