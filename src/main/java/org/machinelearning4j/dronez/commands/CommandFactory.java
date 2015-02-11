package org.machinelearning4j.dronez.commands;

import org.ml4j.dronez.DroneAction;
import org.ml4j.dronez.DroneState;
import org.ml4j.mdp.StateActionSequenceListener;
import org.ml4j.mdp.Trajectory;

public interface CommandFactory extends StateActionSequenceListener<DroneState,DroneState,DroneAction> {

	public HoverCommand createHoverCommand(DroneState hoverState,int iterations);
	public TargetTrajectoryCommand createTargetTrajectoryCommand(Trajectory<DroneState> trajectory,int iterations);
	public NoOpCommand createNoOpCommand(int iterations);
	public void updatePolicies();
	public void init();
}
