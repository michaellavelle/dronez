package org.machinelearning4j.dronez.domain;

import java.util.ArrayList;
import java.util.List;

import org.machinelearning4j.dronez.commands.PolicyCommand;
import org.ml4j.dronez.DroneAction;
import org.ml4j.dronez.DroneState;
import org.ml4j.dronez.TargetRelativeDroneStateWithRecentActions;
import org.ml4j.mdp.PolicyExecutor;
import org.ml4j.mdp.StateActionController;
import org.ml4j.mdp.StateActionSequenceListener;
import org.ml4j.mdp.StateObserver;

public abstract class Drone implements StateActionController<DroneState, DroneAction> {

	protected StateObserver<DroneState> stateObserver = null;
	private List<StateActionSequenceListener<DroneState, DroneState, DroneAction>> listeners;

	public static int INSTRUCTION_INTERVAL_MILLISECONDS = 30;

	public void addStateActionSequenceListener(StateActionSequenceListener<DroneState, DroneState, DroneAction> listener) {
		listeners.add(listener);
	}

	public Drone(StateObserver<DroneState> stateObserver) {
		this.stateObserver = stateObserver;
		this.listeners = new ArrayList<StateActionSequenceListener<DroneState, DroneState, DroneAction>>();
	}

	public Drone() {
		this.stateObserver = this;
		this.listeners = new ArrayList<StateActionSequenceListener<DroneState, DroneState, DroneAction>>();
	}

	public abstract void executeLaunchSequence();

	public abstract void executeLandingSequence();

	public  <P> void executeCommand(
			PolicyCommand<DroneState, P, DroneAction> targetTrajectoryCommand) {

		PolicyExecutor<DroneState, P, DroneAction> policyExecutor = new PolicyExecutor<DroneState, P, DroneAction>(
				stateObserver, this, targetTrajectoryCommand.getPolicy(),
				targetTrajectoryCommand.getPolicyStateMapper(), 0);

		for (StateActionSequenceListener<DroneState, DroneState, DroneAction> listener : listeners) {
			policyExecutor.addControllerStateActionSequenceListener(listener);
		}
		for (StateActionSequenceListener<P, P, DroneAction> listener : targetTrajectoryCommand
				.getPolicySequenceListeners()) {
			policyExecutor.addPolicyStateActionSequenceListener(listener);
		}

		policyExecutor.executePolicy(targetTrajectoryCommand.getIterations());
	}

}
