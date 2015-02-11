package org.machinelearning4j.dronez.trajectory;

import org.ml4j.dronez.DroneState;
import org.ml4j.mdp.Trajectory;

public class SpinTrajectory implements Trajectory<Double> {

	private Trajectory<DroneState> droneStateTrajectory;
	
	public SpinTrajectory(Trajectory<DroneState> droneStateTrajectory)
	{
		this.droneStateTrajectory = droneStateTrajectory;
	}
	
	@Override
	public Double getTarget(long iteration) {
		DroneState targetState = droneStateTrajectory.getTarget(iteration);
		return targetState.getSpinPositionVelocity().getPosition();
	}

}
