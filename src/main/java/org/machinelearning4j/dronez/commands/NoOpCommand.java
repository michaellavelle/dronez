package org.machinelearning4j.dronez.commands;

import org.machinelearning4j.dronez.policy.DistanceToTargetPositionsPolicyStateMapper;
import org.machinelearning4j.dronez.policy.NoOpPolicy;
import org.ml4j.dronez.DroneAction;
import org.ml4j.dronez.DroneState;
import org.ml4j.dronez.PositionVelocity;
import org.ml4j.dronez.TargetRelativeDroneStateWithRecentActions;
import org.ml4j.mdp.Policy;
import org.ml4j.mdp.PolicyStateMapper;
import org.ml4j.mdp.StateActionSequenceHistory;
import org.ml4j.mdp.Trajectory;

public class NoOpCommand extends
		AbstractPolicyCommand<DroneState, TargetRelativeDroneStateWithRecentActions, DroneAction> {

	private StateActionSequenceHistory<?, ?, DroneAction> history;
	private Trajectory<DroneState> trajectory;

	private int recentActionCount;
	
	public NoOpCommand(int iterations, StateActionSequenceHistory<?, ?, DroneAction> history,int recentActionCount) {
		super(iterations);
		this.trajectory = new Trajectory<DroneState>() {

			@Override
			public DroneState getTarget(long arg0) {
				return new DroneState(new PositionVelocity(0, 0), new PositionVelocity(0, 0),
						new PositionVelocity(0, 0), new PositionVelocity(0, 0));
			}
		};
		;
		this.history = history;
		this.recentActionCount = recentActionCount;
	}

	@Override
	public Policy<TargetRelativeDroneStateWithRecentActions, DroneAction> getPolicy() {
		return new NoOpPolicy();
	}

	@Override
	public PolicyStateMapper<DroneState, TargetRelativeDroneStateWithRecentActions> getPolicyStateMapper() {
		return new DistanceToTargetPositionsPolicyStateMapper(trajectory, history,recentActionCount);
	}

}
