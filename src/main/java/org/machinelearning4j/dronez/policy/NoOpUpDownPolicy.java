package org.machinelearning4j.dronez.policy;

import org.machinelearning4j.dronez.domain.PositionVelocityWithRecentActions;
import org.machinelearning4j.dronez.domain.UpDownAction;
import org.ml4j.mdp.Policy;

public class NoOpUpDownPolicy implements Policy<PositionVelocityWithRecentActions<UpDownAction>, UpDownAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public UpDownAction getAction(PositionVelocityWithRecentActions<UpDownAction> arg0) {
		return UpDownAction.NO_OP;
	}

}
