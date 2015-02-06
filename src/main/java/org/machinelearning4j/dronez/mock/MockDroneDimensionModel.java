package org.machinelearning4j.dronez.mock;

import java.util.Random;

import org.ml4j.dronez.NumericAction;
import org.ml4j.dronez.PositionVelocity;
import org.ml4j.mdp.Model;

public class MockDroneDimensionModel<A extends NumericAction> implements Model<PositionVelocity,PositionVelocity,A>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	// TODO
	public PositionVelocity getInitialState() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private double minValue;
	private double maxValue;
	private boolean reverseActionValue;
	
	public MockDroneDimensionModel(double minValue,double maxValue,boolean reverseActionValue)
	{
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.reverseActionValue = reverseActionValue;
	}
	

	@Override
	public PositionVelocity getState(PositionVelocity currentState, A action) {

		
		double previousPosition = currentState.getPosition();
		
		double previousVelocity = currentState.getVelocity();
	
		double newVelocity = 0;
		if (reverseActionValue)
		{
			newVelocity = 0.9 * previousVelocity - 0.35
					* action.getValue();
		}
		else
		{
			newVelocity = 0.9 * previousVelocity + 0.35
					* action.getValue();
		}
		
		
		double time = 1;

		double newPosition = previousPosition + time
				* (newVelocity + previousVelocity) / 2;
		
		newPosition = newPosition + generateNoise(0, 0.04);
		
		
		if (newPosition > maxValue) {
			newPosition = maxValue;
		}

		if (newPosition < minValue) {
			newPosition = minValue;
		}
		
		
		return new PositionVelocity(newPosition,newVelocity);
	
	}
	
	
	private double generateNoise(double noiseMean, double noiseStdDev) {
		Random random = new Random();

		double randomNoise = random.nextGaussian() * noiseStdDev + noiseMean;
		return randomNoise;
	}

}
