package org.machinelearning4j.dronez.policy;

import org.machinelearning4j.dronez.domain.ForwardBackAction;
import org.machinelearning4j.dronez.domain.PositionVelocityWithRecentActions;
import org.ml4j.mdp.Policy;

public class NoOpForwardBackPolicy implements Policy<PositionVelocityWithRecentActions<ForwardBackAction>, ForwardBackAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public ForwardBackAction getAction(PositionVelocityWithRecentActions<ForwardBackAction> arg0) {
		return ForwardBackAction.NO_OP;
	}

}
