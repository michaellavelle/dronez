package org.machinelearning4j.dronez;

import org.machinelearning4j.dronez.commands.CommandFactory;
import org.machinelearning4j.dronez.commands.DiscreteStateMdpInnerPolicyCommandFactoryImpl;
import org.machinelearning4j.dronez.commands.HoverCommand;
import org.machinelearning4j.dronez.commands.TargetTrajectoryCommand;
import org.machinelearning4j.dronez.domain.Drone;
import org.machinelearning4j.dronez.domain.ODCDrone;
import org.machinelearning4j.dronez.mock.MockDrone;
import org.machinelearning4j.dronez.mock.MockWebCamObserver;
import org.machinelearning4j.dronez.tracking.AbstractWebCamObserver;
import org.machinelearning4j.dronez.tracking.WebCamObserver;
import org.machinelearning4j.dronez.trajectory.CircleTrajectory;
import org.ml4j.dronez.DroneAction;
import org.ml4j.dronez.DroneState;
import org.ml4j.dronez.PositionVelocity;
import org.ml4j.mdp.Model;
import org.ml4j.mdp.StateObserver;
import org.ml4j.mdp.Trajectory;

public class DroneFlyerPolicyLearnerTestHarness extends AbstractDroneFlyer {

	public static DroneState HOVER_POSITION = new DroneState(new PositionVelocity(0, 0), new PositionVelocity(0.3, 0),
			new PositionVelocity(2.5, 0), new PositionVelocity(0, 0));

	private static AbstractWebCamObserver webCamObserver = null;

	public DroneFlyerPolicyLearnerTestHarness(Drone drone, StateObserver<DroneState> droneObserver,
			CommandFactory commandFactory) {
		super(drone, droneObserver, commandFactory);
	}

	public static void main(String[] args) {

		boolean useMocks = true;
		boolean loadPreviousRunState = false;
		
		int recentActionCount = 2;

		CommandFactory commandFactory = new DiscreteStateMdpInnerPolicyCommandFactoryImpl(
				DroneFlyerPolicyLearnerTestHarness.class.getClassLoader(), "serialized",loadPreviousRunState,recentActionCount);
		commandFactory.init();

		if (useMocks) {

			// Create a mock web cam observer, which observes the drone using a
			// simulated model,
			// based on actions taken. Set the initial drone position to be at
			// pixel 200,200,
			// with an initial velocity of 10px/iteration

			PositionVelocity initialLeftRightState = new PositionVelocity(-0.1, 0.1);
			PositionVelocity initialUpDownState = new PositionVelocity(-0.1, 0.1);
			PositionVelocity initialForwardBackState = new PositionVelocity(-0.1, 0);
			PositionVelocity initialSpinState = new PositionVelocity(0, 0);

			DroneState initialState = new DroneState(initialLeftRightState, initialUpDownState,
					initialForwardBackState, initialSpinState);

			MockWebCamObserver mockWebCamObserver = new MockWebCamObserver(initialState);
			webCamObserver = mockWebCamObserver;

			// Create a mock drone instance, with 0 iteration delay between
			// action instruction and
			// action effect

			Model<DroneState, DroneState, DroneAction> mockModel = createMockDroneModel();

			Drone drone = new MockDrone(mockWebCamObserver, mockModel, 0, 30);

			// Start observing
			webCamObserver.startObserving();

			// Fly
			DroneFlyerPolicyLearnerTestHarness droneFlyer = new DroneFlyerPolicyLearnerTestHarness(drone,
					webCamObserver, commandFactory);
			droneFlyer.flyDrone();
		} else {
			// Create a web cam observer
			webCamObserver = new WebCamObserver();

			// Create a drone instance
			Drone drone = new ODCDrone(webCamObserver);

			// Start observing
			webCamObserver.startObserving();

			// Fly
			DroneFlyerPolicyLearnerTestHarness droneFlyer = new DroneFlyerPolicyLearnerTestHarness(drone,
					webCamObserver, commandFactory);
			droneFlyer.flyDrone();
		}
	}

	@Override
	protected void executeMainFlight(Drone drone) {

		drone.addStateActionSequenceListener(commandFactory);

		HoverCommand hoverCommand = null;

		for (int i = 0; i < 50; i++) {
			hoverCommand = commandFactory.createHoverCommand(HOVER_POSITION, 100);

			// Hover
			System.out.println("Hovering");

			// Display the target trajectory alongside the drone position
			// (optional)
			getDronePositionPrinter().setTargetTrajectory(hoverCommand.getTrajectory());
			webCamObserver.setTargetTrajectory(hoverCommand.getTrajectory());
			drone.executeCommand(hoverCommand);
			commandFactory.updatePolicies();
		}

		// Follow trajectory
		System.out.println("Following trajectory");

		Trajectory<DroneState> trajectory = new CircleTrajectory(HOVER_POSITION);
		TargetTrajectoryCommand targetTrajectoryCommand = commandFactory.createTargetTrajectoryCommand(trajectory, 90);

		// Display the target trajectory alongside the drone position (optional)
		getDronePositionPrinter().setTargetTrajectory(targetTrajectoryCommand.getTrajectory());
		webCamObserver.setTargetTrajectory(targetTrajectoryCommand.getTrajectory());

		drone.executeCommand(targetTrajectoryCommand);

		// Hover
		System.out.println("Hovering");

		// Display the target trajectory alongside the drone position (optional)
		getDronePositionPrinter().setTargetTrajectory(hoverCommand.getTrajectory());

		webCamObserver.setTargetTrajectory(hoverCommand.getTrajectory());

		drone.executeCommand(hoverCommand);

		// Clear the target trajectory from drone state printing
		getDronePositionPrinter().setTargetTrajectory(null);

	}
}
