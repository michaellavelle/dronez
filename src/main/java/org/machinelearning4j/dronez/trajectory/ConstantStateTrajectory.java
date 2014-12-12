package org.machinelearning4j.dronez.trajectory;

import org.ml4j.mdp.Trajectory;

public class ConstantStateTrajectory<S> implements Trajectory<S> {

	private S constantState;
	
	public ConstantStateTrajectory(S constantState)
	{
		this.constantState = constantState;
	}
	
	@Override
	public S getTarget(long iteration) {
		return constantState;
	}

}
