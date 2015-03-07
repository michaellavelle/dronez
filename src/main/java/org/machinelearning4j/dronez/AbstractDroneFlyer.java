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
import org.ml4j.dronez.DroneStateWithRecentActions;
import org.ml4j.dronez.ForwardBackAction;
import org.ml4j.dronez.LeftRightAction;
import org.ml4j.dronez.PositionVelocity;
import org.ml4j.dronez.UpDownAction;
import org.ml4j.dronez.models.DroneModel;
import org.ml4j.dronez.models.StatefulDroneStateWithoutActionsModelAdapter;
import org.ml4j.dronez.models.factories.ModelFactory;
import org.ml4j.dronez.models.factories.SerializedModelFactory;
import org.ml4j.dronez.models.learning.DroneModelLearner;
import org.ml4j.mdp.Model;
import org.ml4j.mdp.StateObserver;

public abstract class AbstractDroneFlyer {

	protected abstract void executeMainFlight(Drone drone);

	private Drone drone;
	private StateObserver<DroneState> droneObserver;
	private DronePositionPrinter dronePositionPrinter;

	protected CommandFactory commandFactory;

	static {
		// Set AR Drone Logging to not log WARNING messages
		for (Handler handler : Logger.getLogger("").getHandlers()) {
			handler.setLevel(Level.SEVERE);
		}
	}
	
	
	private static ModelFactory<DroneStateWithRecentActions,DroneStateWithRecentActions,DroneAction> createModelFactory() {
		
		// Create a serialized model factory
		SerializedModelFactory<DroneStateWithRecentActions,DroneStateWithRecentActions,DroneAction> modelFactory = new SerializedModelFactory<DroneStateWithRecentActions,DroneStateWithRecentActions,DroneAction>();
		// Register a serialized model with the model factory
		modelFactory.registerModel(DroneModelLearner.MODEL_CLASS, "droneModel");
		modelFactory.registerModel(DroneModelLearner.MODEL_CLASS, "droneModelExtendedRun");

		return modelFactory;
	}

	protected static Model<DroneState, DroneState, DroneAction> createMockDroneModel() {
		
		boolean useActualFlightModel = false;
		
		if (useActualFlightModel)
		{
			int modelRecentActionCount = 10;
			return new StatefulDroneStateWithoutActionsModelAdapter((DroneModel)createModelFactory().createModel("droneModel"),modelRecentActionCount);
		}
		else
		{
		
		Model<PositionVelocity, PositionVelocity, LeftRightAction> mockLeftRightModel = new MockDroneDimensionModel<LeftRightAction>(
				-2.5, 2.5, false);

		Model<PositionVelocity, PositionVelocity, UpDownAction> mockUpDownModel = new MockDroneDimensionModel<UpDownAction>(
				-2.5, 2.5, false);

		Model<PositionVelocity, PositionVelocity, ForwardBackAction> mockForwardBackModel = new MockDroneDimensionModel<ForwardBackAction>(
				0, 4, false);

		return new MockDroneModel(mockLeftRightModel, mockUpDownModel, mockForwardBackModel);
		}
	}

	public DronePositionPrinter getDronePositionPrinter() {
		return dronePositionPrinter;
	}

	public AbstractDroneFlyer(Drone drone, StateObserver<DroneState> droneObserver, CommandFactory commandFactory) {
		this.drone = drone;
		this.droneObserver = droneObserver;
		this.dronePositionPrinter = new DronePositionPrinter(-2.5, 2.5, false, 0.05, "Flying:");
		this.commandFactory = commandFactory;
	}

	public void flyDrone() {

		// Log state action sequence events
		drone.addStateActionSequenceListener(dronePositionPrinter);

		// Launch drone
		drone.executeLaunchSequence();

		DroneState droneState = droneObserver.getCurrentState();

		while (droneState == null || droneState.getLeftRightPositionVelocity() == null) {
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
