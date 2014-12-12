package org.machinelearning4j.dronez.domain;

import java.io.Serializable;


public class DroneStateWithRecentActions implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final PositionVelocityWithRecentActions<LeftRightAction> leftRightPositionVelocity;
	private final PositionVelocityWithRecentActions<UpDownAction> upDownPositionVelocity;
	private final PositionVelocityWithRecentActions<ForwardBackAction>  forwardBackPositionVelocity;
	private final PositionVelocityWithRecentActions<SpinAction>  spinPositionVelocity;
	

	
	
	public DroneStateWithRecentActions(PositionVelocityWithRecentActions<LeftRightAction> leftRightPositionVelocity,
			PositionVelocityWithRecentActions<UpDownAction> upDownPositionVelocity,
			PositionVelocityWithRecentActions<ForwardBackAction>  forwardBackPositionVelocity,
			PositionVelocityWithRecentActions<SpinAction>  spinPositionVelocity)
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

	public PositionVelocityWithRecentActions<LeftRightAction> getLeftRightPositionVelocity() {
		return leftRightPositionVelocity;
	}

	public PositionVelocityWithRecentActions<UpDownAction> getUpDownPositionVelocity() {
		return upDownPositionVelocity;
	}

	public PositionVelocityWithRecentActions<ForwardBackAction> getForwardBackPositionVelocity() {
		return forwardBackPositionVelocity;
	}

	public PositionVelocityWithRecentActions<SpinAction> getSpinPositionVelocity() {
		return spinPositionVelocity;
	}
	
	
	
}
