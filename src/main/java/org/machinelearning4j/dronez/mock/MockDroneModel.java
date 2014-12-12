package org.machinelearning4j.dronez.mock;

import java.util.Random;

import org.machinelearning4j.dronez.domain.DroneAction;
import org.machinelearning4j.dronez.domain.DroneState;
import org.machinelearning4j.dronez.domain.PositionVelocity;
import org.ml4j.mdp.Model;

public class MockDroneModel implements
		Model<DroneState, DroneState, DroneAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public DroneState getInitialState() {
		// TODO
		return null;
	}

	private double generateNoise(double noiseMean, double noiseStdDev) {
		Random random = new Random();

		double randomNoise = random.nextGaussian() * noiseStdDev + noiseMean;
		return randomNoise;
	}

	@Override
	public DroneState getState(DroneState currentState, DroneAction action) {
		double previousLeftRightPosition = currentState
				.getLeftRightPositionVelocity().getPosition();

		double previousUpDownPosition = currentState
				.getUpDownPositionVelocity().getPosition();

		double previousForwardBackPosition = currentState
				.getForwardBackPositionVelocity().getPosition();

		double previousLeftRightVelocity = currentState
				.getLeftRightPositionVelocity().getVelocity();
		double previousUpDownVelocity = currentState
				.getUpDownPositionVelocity().getVelocity();

		double previousForwardBackVelocity = currentState
				.getForwardBackPositionVelocity().getVelocity();

		double newLeftRightVelocity = 0.9 * previousLeftRightVelocity + 0.35
				* action.getLeftRightAction().getValue();

		double newUpDownVelocity = 0.9 * previousUpDownVelocity - 0.35
				* action.getUpDownAction().getValue();

		double newForwardBackVelocity = 0.9 * previousForwardBackVelocity + 0.35
				* action.getForwardBackAction().getValue();

		double time = 1;

		double newLeftRightPosition = previousLeftRightPosition + time
				* (newLeftRightVelocity + previousLeftRightVelocity) / 2;

		double newUpDownPosition = previousUpDownPosition + time
				* (newUpDownVelocity + previousUpDownVelocity) / 2;

		double newForwardBackPosition = previousForwardBackPosition + time
				* (newForwardBackVelocity + previousForwardBackVelocity) / 2;

		newLeftRightPosition = newLeftRightPosition + generateNoise(0, 0.04);
		newUpDownPosition = newUpDownPosition + generateNoise(0, 0.04);
		newForwardBackPosition = newForwardBackPosition
				+ generateNoise(0, 0.04);

		if (newLeftRightPosition > 2.5) {
			newLeftRightPosition = 2.5;
		}

		if (newLeftRightPosition < -2.5) {
			newLeftRightPosition = -2.5;
		}

		if (newUpDownPosition > 2.5) {
			newUpDownPosition = 2.5;
		}

		if (newUpDownPosition < -2.5) {
			newUpDownPosition = -2.5;
		}


		if (newForwardBackPosition < 0) {
			newForwardBackPosition = 0;
		}

		if (newForwardBackPosition > 4) {
			newForwardBackPosition = 4;
		}

		return new DroneState(new PositionVelocity(newLeftRightPosition,
				newLeftRightVelocity), new PositionVelocity(newUpDownPosition,
				newUpDownVelocity), new PositionVelocity(
				newForwardBackPosition, newForwardBackVelocity),
				currentState.getSpinPositionVelocity());
	}

}
