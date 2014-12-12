package org.machinelearning4j.dronez.domain;

import org.ml4j.mdp.StateObserver;
import org.opendronecontrol.platforms.ardrone.ARDrone;

public class ODCDrone extends Drone {

	private ARDrone arDrone;

	public ODCDrone(StateObserver<DroneState> stateObserver) {
		super(stateObserver);
		this.arDrone = new ARDrone("192.168.1.1");
	}

	@Override
	public DroneState getCurrentState() {
		return stateObserver.getCurrentState();
	}

	@Override
	public void takeAction(DroneAction action) {

		arDrone.playLed(18, 1, 500);

		arDrone.playLed(18, 1, 500);
		float actionScaleFactor = 0.05f;
		if (action.isNoOp()) {
			arDrone.hover();
		} else {
			// left/right, up/down, back/forward,rotation
			for (int i = 0; i <10; i++)
			{
			arDrone.move(actionScaleFactor
					* (float) action.getLeftRightAction().getValue(),
				- 1 * actionScaleFactor
							* (float) action.getUpDownAction().getValue(),
					 actionScaleFactor
							* (float) action.getForwardBackAction().getValue(),
					actionScaleFactor
							* (float) action.getSpinAction().getValue());
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			}
		}
	}

	@Override
	public void executeLaunchSequence() {

		arDrone.connect();

		while (arDrone.connecting()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			} // sleep to make sure the drone is ready for
				// commands after connecting
		}

		arDrone.clearEmergency();

		arDrone.trim();

		arDrone.takeOff();

		arDrone.trim();

		arDrone.playLed(18, 1, 500);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		} 
		arDrone.playLed(18, 1, 500);

	}

	@Override
	public void executeLandingSequence() {
		arDrone.land();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}

	}

}
