package org.machinelearning4j.dronez.policy;

import java.util.List;

import org.machinelearning4j.dronez.domain.DroneAction;
import org.machinelearning4j.dronez.domain.LeftRightAction;

public class LeftRightActionExtractor implements ActionExtractor<LeftRightAction> {

	@Override
	public LeftRightAction[] getActions(List<DroneAction> droneActions) {

		LeftRightAction[] actions = new LeftRightAction[droneActions.size()];
		for (int i = 0; i < actions.length; i++)
		{
			actions[i] = droneActions.get(i).getLeftRightAction();
		}
		return actions;
	}

}
