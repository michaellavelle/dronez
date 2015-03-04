package org.machinelearning4j.dronez.policy;

import org.ml4j.dronez.DroneAction;
import org.ml4j.dronez.ForwardBackAction;
import org.ml4j.dronez.LeftRightAction;
import org.ml4j.dronez.SpinAction;
import org.ml4j.dronez.TargetRelativeDroneStateWithRecentActions;
import org.ml4j.dronez.UpDownAction;
import org.ml4j.mdp.Policy;

public class NoOpPolicy implements Policy<TargetRelativeDroneStateWithRecentActions, DroneAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public DroneAction getAction(TargetRelativeDroneStateWithRecentActions droneState) {
		return new DroneAction(LeftRightAction.NO_OP, UpDownAction.NO_OP, ForwardBackAction.NO_OP, SpinAction.NO_OP);
	}

}
