package org.machinelearning4j.dronez.policy;

import java.util.List;

import org.machinelearning4j.dronez.domain.DroneAction;
import org.machinelearning4j.dronez.domain.SpinAction;

public class SpinActionExtractor implements ActionExtractor<SpinAction> {

	@Override
	public SpinAction[] getActions(List<DroneAction> droneActions) {

		SpinAction[] actions = new SpinAction[droneActions.size()];
		for (int i = 0; i < actions.length; i++)
		{
			actions[i] = droneActions.get(i).getSpinAction();
		}
		return actions;
	}

}
