package org.machinelearning4j.dronez.policy;

import java.util.List;

import org.machinelearning4j.dronez.domain.DroneAction;
import org.machinelearning4j.dronez.domain.ForwardBackAction;

public class ForwardBackActionExtractor implements ActionExtractor<ForwardBackAction> {

	@Override
	public ForwardBackAction[] getActions(List<DroneAction> droneActions) {

		ForwardBackAction[] actions = new ForwardBackAction[droneActions.size()];
		for (int i = 0; i < actions.length; i++)
		{
			actions[i] = droneActions.get(i).getForwardBackAction();
		}
		return actions;
	}

}
