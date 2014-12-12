	package org.machinelearning4j.dronez.commands;

import org.machinelearning4j.dronez.domain.DroneAction;
import org.machinelearning4j.dronez.domain.DroneState;
import org.machinelearning4j.dronez.domain.DroneStateWithRecentActions;
import org.machinelearning4j.dronez.trajectory.ConstantStateTrajectory;
import org.ml4j.mdp.Policy;
import org.ml4j.mdp.StateActionSequenceHistory;

public class HoverCommand extends TargetTrajectoryCommand{

	public HoverCommand(DroneState hoverState,int iterations,Policy<DroneStateWithRecentActions,DroneAction> distanceToTargetPolicy,StateActionSequenceHistory<?,?,DroneAction> history) {
		super(new ConstantStateTrajectory<DroneState>(hoverState),iterations,distanceToTargetPolicy,history);
	}

}
