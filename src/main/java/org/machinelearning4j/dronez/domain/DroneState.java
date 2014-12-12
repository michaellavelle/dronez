package org.machinelearning4j.dronez.domain;

import java.io.Serializable;


public class DroneState implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final PositionVelocity leftRightPositionVelocity;
	private final PositionVelocity upDownPositionVelocity;
	private final PositionVelocity  forwardBackPositionVelocity;
	private final PositionVelocity  spinPositionVelocity;


	
	public DroneState(PositionVelocity leftRightPositionVelocity,
			PositionVelocity upDownPositionVelocity,
			PositionVelocity  forwardBackPositionVelocity,
			PositionVelocity  spinPositionVelocity)
	{
		this.leftRightPositionVelocity = leftRightPositionVelocity;
		this.upDownPositionVelocity = upDownPositionVelocity;
		this.forwardBackPositionVelocity = forwardBackPositionVelocity;
		this.spinPositionVelocity = spinPositionVelocity;
	}
	
	public String toString()
	{
		return leftRightPositionVelocity.getPosition() + "," + leftRightPositionVelocity.getVelocity();
	}

	public PositionVelocity getLeftRightPositionVelocity() {
		return leftRightPositionVelocity;
	}

	public PositionVelocity getUpDownPositionVelocity() {
		return upDownPositionVelocity;
	}

	public PositionVelocity getForwardBackPositionVelocity() {
		return forwardBackPositionVelocity;
	}

	public PositionVelocity getSpinPositionVelocity() {
		return spinPositionVelocity;
	}
	
	
	
}
