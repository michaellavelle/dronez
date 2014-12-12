package org.machinelearning4j.dronez.commands;

import org.machinelearning4j.dronez.domain.DroneAction;
import org.machinelearning4j.dronez.domain.DroneState;
import org.machinelearning4j.dronez.domain.DroneStateWithRecentActions;
import org.ml4j.mdp.Policy;
import org.ml4j.mdp.StateActionSequenceHistory;
import org.ml4j.mdp.Trajectory;

public abstract class AbstractCommandFactory implements CommandFactory {
	
	protected Policy<DroneStateWithRecentActions,DroneAction> distanceToTargetPolicy;
	
	private StateActionSequenceHistory<DroneState,DroneState,DroneAction> history;
	
	
	protected void init()
	{
		this.distanceToTargetPolicy = createDistanceToTargetPolicy();
	}
	
	public AbstractCommandFactory()
	{
		this.history = new StateActionSequenceHistory<DroneState,DroneState,DroneAction>();
		init();
	}

	
	
		
	
	
	protected abstract Policy<DroneStateWithRecentActions, DroneAction> createDistanceToTargetPolicy();





	@Override
	public HoverCommand createHoverCommand(DroneState hoverState,int iterations) {

		HoverCommand hoverCommand =  new HoverCommand(hoverState,iterations,distanceToTargetPolicy,history);

		return hoverCommand;
	}

	@Override
	public TargetTrajectoryCommand createTargetTrajectoryCommand(
			Trajectory<DroneState> trajectory, int iterations) {


		TargetTrajectoryCommand targetTrajectoryCommand =  new TargetTrajectoryCommand(trajectory,iterations,distanceToTargetPolicy,history);
		return targetTrajectoryCommand;
	}


	@Override
	public NoOpCommand createNoOpCommand(int iterations) {
		NoOpCommand noOpCommand =  new NoOpCommand(iterations,history);
		return noOpCommand;
	}


	@Override
	public void onStateActionStateSequence(long arg0, DroneState arg1,
			DroneAction arg2, DroneState arg3) {
		history.onStateActionStateSequence(arg0, arg1, arg2, arg3);	
	}


	
}
