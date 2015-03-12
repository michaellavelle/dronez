package org.machinelearning4j.dronez.commands;

import org.machinelearning4j.dronez.trajectory.ConstantStateTrajectory;
import org.ml4j.dronez.DroneAction;
import org.ml4j.dronez.DroneState;
import org.ml4j.dronez.TargetRelativeDroneStateWithRecentActions;
import org.ml4j.mdp.Policy;
import org.ml4j.mdp.StateActionSequenceHistory;

public class HoverCommand extends TargetTrajectoryCommand {

	public HoverCommand(DroneState hoverState, int iterations,
			Policy<TargetRelativeDroneStateWithRecentActions, DroneAction> distanceToTargetPolicy,
			StateActionSequenceHistory<?, ?, DroneAction> history,int recentActionCount) {
		super(new ConstantStateTrajectory<DroneState>(hoverState), iterations, distanceToTargetPolicy, history,recentActionCount);
	}

}
