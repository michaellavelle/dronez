package org.machinelearning4j.dronez.policy;

import java.util.List;

import org.ml4j.dronez.DroneAction;
import org.ml4j.dronez.UpDownAction;

public class UpDownActionExtractor implements ActionExtractor<UpDownAction> {

	@Override
	public UpDownAction[] getActions(List<DroneAction> droneActions) {

		UpDownAction[] actions = new UpDownAction[droneActions.size()];
		for (int i = 0; i < actions.length; i++)
		{
			actions[i] = droneActions.get(i).getUpDownAction();
		}
		return actions;
	}

}
