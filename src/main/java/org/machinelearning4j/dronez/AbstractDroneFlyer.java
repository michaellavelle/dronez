package org.machinelearning4j.dronez;

import org.machinelearning4j.dronez.commands.CommandFactory;
import org.machinelearning4j.dronez.domain.Drone;
import org.machinelearning4j.dronez.domain.DroneState;
import org.machinelearning4j.dronez.util.DronePositionPrinter;
import org.ml4j.mdp.StateObserver;


public abstract class AbstractDroneFlyer {
	
	protected abstract void executeMainFlight(Drone drone);
	
	private Drone drone;
	private StateObserver<DroneState> droneObserver;
	private DronePositionPrinter dronePositionPrinter;
	
	protected CommandFactory commandFactory;
	
	
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
