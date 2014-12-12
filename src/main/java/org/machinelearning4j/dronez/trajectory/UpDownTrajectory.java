package org.machinelearning4j.dronez.trajectory;

import org.machinelearning4j.dronez.domain.DroneState;
import org.ml4j.mdp.Trajectory;

public class UpDownTrajectory implements Trajectory<Double> {

	private Trajectory<DroneState> droneStateTrajectory;
	
	public UpDownTrajectory(Trajectory<DroneState> droneStateTrajectory)
	{
		this.droneStateTrajectory = droneStateTrajectory;
	}
	
	@Override
	public Double getTarget(long iteration) {
		DroneState targetState = droneStateTrajectory.getTarget(iteration);
		return targetState.getUpDownPositionVelocity().getPosition();
	}

}
