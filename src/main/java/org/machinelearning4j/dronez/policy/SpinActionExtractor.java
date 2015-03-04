package org.machinelearning4j.dronez.policy;

import java.util.ArrayList;
import java.util.List;

import org.ml4j.dronez.ActionExtractor;
import org.ml4j.dronez.DroneAction;
import org.ml4j.dronez.SpinAction;

public class SpinActionExtractor implements ActionExtractor<SpinAction> {

	@Override
	public List<SpinAction> getActions(List<DroneAction> droneActions) {

		List<SpinAction> actions = new ArrayList<SpinAction>();
		for (int i = 0; i < droneActions.size(); i++) {
			actions.add(droneActions.get(i).getSpinAction());
		}
		return actions;
	}

}
