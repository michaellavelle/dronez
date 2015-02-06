package org.machinelearning4j.dronez.domain;

import java.io.Serializable;

import org.ml4j.dronez.ForwardBackAction;
import org.ml4j.dronez.LeftRightAction;
import org.ml4j.dronez.SpinAction;
import org.ml4j.dronez.TargetRelativePositionWithVelocityAndRecentActions;
import org.ml4j.dronez.UpDownAction;


public class TargetRelativeDroneStateWithRecentActions implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final TargetRelativePositionWithVelocityAndRecentActions<LeftRightAction> leftRightPositionVelocity;
	private final TargetRelativePositionWithVelocityAndRecentActions<UpDownAction> upDownPositionVelocity;
	private final TargetRelativePositionWithVelocityAndRecentActions<ForwardBackAction>  forwardBackPositionVelocity;
	private final TargetRelativePositionWithVelocityAndRecentActions<SpinAction>  spinPositionVelocity;
	

	
	
	public TargetRelativeDroneStateWithRecentActions(TargetRelativePositionWithVelocityAndRecentActions<LeftRightAction> leftRightPositionVelocity,
			TargetRelativePositionWithVelocityAndRecentActions<UpDownAction> upDownPositionVelocity,
			TargetRelativePositionWithVelocityAndRecentActions<ForwardBackAction>  forwardBackPositionVelocity,
			TargetRelativePositionWithVelocityAndRecentActions<SpinAction>  spinPositionVelocity)
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

	public TargetRelativePositionWithVelocityAndRecentActions<LeftRightAction> getLeftRightPositionVelocity() {
		return leftRightPositionVelocity;
	}

	public TargetRelativePositionWithVelocityAndRecentActions<UpDownAction> getUpDownPositionVelocity() {
		return upDownPositionVelocity;
	}

	public TargetRelativePositionWithVelocityAndRecentActions<ForwardBackAction> getForwardBackPositionVelocity() {
		return forwardBackPositionVelocity;
	}

	public TargetRelativePositionWithVelocityAndRecentActions<SpinAction> getSpinPositionVelocity() {
		return spinPositionVelocity;
	}
	
	
	
}
