package org.machinelearning4j.dronez;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.machinelearning4j.dronez.commands.CommandFactory;
import org.machinelearning4j.dronez.domain.Drone;
import org.machinelearning4j.dronez.mock.MockDroneDimensionModel;
import org.machinelearning4j.dronez.mock.MockDroneModel;
import org.machinelearning4j.dronez.util.DronePositionPrinter;
import org.ml4j.dronez.DroneAction;
import org.ml4j.dronez.DroneState;
import org.ml4j.dronez.ForwardBackAction;
import org.ml4j.dronez.LeftRightAction;
import org.ml4j.dronez.PositionVelocity;
import org.ml4j.dronez.UpDownAction;
import org.ml4j.mdp.Model;
import org.ml4j.mdp.StateObserver;


public abstract class AbstractDroneFlyer {
	
	protected abstract void executeMainFlight(Drone drone);
	
	private Drone drone;
	private StateObserver<DroneState> droneObserver;
	private DronePositionPrinter dronePositionPrinter;
	
	protected CommandFactory commandFactory;
	
	static
	{
		// Set AR Drone Logging to not log WARNING messages
		for (Handler handler : Logger.getLogger("").getHandlers())
		{
			handler.setLevel(Level.SEVERE);
		}
	}
	
	protected static Model<DroneState,DroneState,DroneAction> createMockDroneModel()
	{
		Model<PositionVelocity, PositionVelocity, LeftRightAction> mockLeftRightModel 
		 = new MockDroneDimensionModel<LeftRightAction>(-2.5,2.5,false);
		
		Model<PositionVelocity, PositionVelocity, UpDownAction> mockUpDownModel 
		 = new MockDroneDimensionModel<UpDownAction>(-2.5,2.5,true);
		
		Model<PositionVelocity, PositionVelocity, ForwardBackAction> mockForwardBackModel 
		 = new MockDroneDimensionModel<ForwardBackAction>(0,4,false);
		
		return new MockDroneModel(mockLeftRightModel,mockUpDownModel,mockForwardBackModel);
	}
	
	
	public DronePositionPrinter getDronePositionPrinter() {
		return dronePositionPrinter;
	}

	public AbstractDroneFlyer(Drone drone,StateObserver<DroneState> droneObserver,CommandFactory commandFactory)
	{
		this.drone = drone;
		this.droneObserver = droneObserver;
		this.dronePositionPrinter = new DronePositionPrinter( -2.5, 2.5, false, 0.05, "Flying:");
		this.commandFactory = commandFactory;
	}

	public void flyDrone() {
		
		// Log state action sequence events
		drone.addStateActionSequenceListener(dronePositionPrinter);

		// Launch drone
		drone.executeLaunchSequence();
				
		DroneState droneState = droneObserver.getCurrentState();
		
		while (droneState == null || droneState.getLeftRightPositionVelocity() == null )
		{
			try {
				Thread.sleep(1000);
				System.out.println("Looking for drone");
			} catch (InterruptedException e) {
			}
			droneState = droneObserver.getCurrentState();
		}
		System.out.println("Drone aquired");
		
		executeMainFlight(drone);

		// Land
		drone.executeLandingSequence();
	}


}
