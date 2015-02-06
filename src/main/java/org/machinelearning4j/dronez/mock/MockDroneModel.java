package org.machinelearning4j.dronez.mock;

import org.machinelearning4j.dronez.domain.DroneAction;
import org.machinelearning4j.dronez.domain.DroneState;
import org.machinelearning4j.dronez.domain.ForwardBackAction;
import org.machinelearning4j.dronez.domain.LeftRightAction;
import org.machinelearning4j.dronez.domain.PositionVelocity;
import org.machinelearning4j.dronez.domain.UpDownAction;
import org.ml4j.mdp.Model;

public class MockDroneModel implements Model<DroneState, DroneState, DroneAction>{

	
	private Model<PositionVelocity, PositionVelocity, LeftRightAction> leftRightModel;
	private Model<PositionVelocity, PositionVelocity, UpDownAction> upDownModel;
	private Model<PositionVelocity, PositionVelocity, ForwardBackAction> forwardBackModel;

	/*
	public MockDroneModel2()
	{
		this(new MockDroneDimensionModel<LeftRightAction>(-2.5,2.5,false),new MockDroneDimensionModel<UpDownAction>(-2.5,2.5,true),new MockDroneDimensionModel<ForwardBackAction>(0,4,false));
	}
	*/

	public MockDroneModel(Model<PositionVelocity, PositionVelocity, LeftRightAction> leftRightModel,Model<PositionVelocity, PositionVelocity, UpDownAction> upDownModel,Model<PositionVelocity, PositionVelocity, ForwardBackAction> forwardBackModel)
	{
		this.leftRightModel = leftRightModel;
		this.upDownModel = upDownModel;
		this.forwardBackModel = forwardBackModel;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	// TODO
	public DroneState getInitialState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DroneState getState(DroneState arg0, DroneAction arg1) {
		
		PositionVelocity leftRightPositionVelocity = leftRightModel.getState(arg0.getLeftRightPositionVelocity(), arg1.getLeftRightAction());
		PositionVelocity upDownPositionVelocity = upDownModel.getState(arg0.getUpDownPositionVelocity(), arg1.getUpDownAction());
		PositionVelocity forwardBackPositionVelocity = forwardBackModel.getState(arg0.getForwardBackPositionVelocity(), arg1.getForwardBackAction());

		
		return new DroneState(leftRightPositionVelocity,upDownPositionVelocity,forwardBackPositionVelocity,arg0.getSpinPositionVelocity());
	}

}
