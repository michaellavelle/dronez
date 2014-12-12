package org.machinelearning4j.dronez.policy;

import org.machinelearning4j.dronez.domain.PositionVelocityWithRecentActions;
import org.machinelearning4j.dronez.domain.SpinAction;
import org.ml4j.mdp.Policy;

public class NoOpSpinPolicy implements Policy<PositionVelocityWithRecentActions<SpinAction>, SpinAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public SpinAction getAction(PositionVelocityWithRecentActions<SpinAction> arg0) {
		return SpinAction.NO_OP;
	}

}
