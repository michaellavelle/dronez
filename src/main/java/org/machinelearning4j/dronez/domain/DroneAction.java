package org.machinelearning4j.dronez.domain;

import java.io.Serializable;


public class DroneAction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final LeftRightAction leftRightAction;
	private final UpDownAction upDownAction;
	private final ForwardBackAction forwardBackAction;
	private final SpinAction spinAction;

	public boolean isNoOp()
	{
		return leftRightAction == LeftRightAction.NO_OP && 
				upDownAction == UpDownAction.NO_OP
				&& forwardBackAction == ForwardBackAction.NO_OP
				&& spinAction == SpinAction.NO_OP;
	}
	
	public DroneAction(LeftRightAction leftRightAction,
			UpDownAction upDownAction,ForwardBackAction forwardBackAction,SpinAction spinAction)
	{
		this.leftRightAction = leftRightAction;
		this.upDownAction = upDownAction;
		this.forwardBackAction = forwardBackAction;
		this.spinAction = spinAction;
	}
	
	

	public UpDownAction getUpDownAction() {
		return upDownAction;
	}

	public ForwardBackAction getForwardBackAction() {
		return forwardBackAction;
	}

	


	public SpinAction getSpinAction() {
		return spinAction;
	}



	public LeftRightAction getLeftRightAction() {
		return leftRightAction;
	}
	
	public String toString()
	{
		return leftRightAction.toString() + "," + upDownAction.toString() + "," + forwardBackAction.toString() + "," + spinAction.toString();
	}
	
}
