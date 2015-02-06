package org.machinelearning4j.dronez.domain;

import org.ml4j.dronez.PositionVelocity;

public class PositionDeltaWithVelocity extends PositionVelocity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PositionDeltaWithVelocity(double position, double vel) {
		super(position, vel);
	}

}
