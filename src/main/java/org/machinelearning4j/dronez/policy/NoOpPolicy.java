package org.machinelearning4j.dronez.policy;

import org.machinelearning4j.dronez.domain.DroneAction;
import org.machinelearning4j.dronez.domain.DroneStateWithRecentActions;
import org.machinelearning4j.dronez.domain.ForwardBackAction;
import org.machinelearning4j.dronez.domain.LeftRightAction;
import org.machinelearning4j.dronez.domain.SpinAction;
import org.machinelearning4j.dronez.domain.UpDownAction;
import org.ml4j.mdp.Policy;

public class NoOpPolicy implements Policy<DroneStateWithRecentActions, DroneAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public DroneAction getAction(DroneStateWithRecentActions droneState) {
		return new DroneAction(LeftRightAction.NO_OP,UpDownAction.NO_OP,ForwardBackAction.NO_OP,SpinAction.NO_OP);
	}

}
