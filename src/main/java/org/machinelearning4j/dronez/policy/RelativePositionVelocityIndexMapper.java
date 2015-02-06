package org.machinelearning4j.dronez.policy;

import java.io.Serializable;

import org.ml4j.dronez.TargetRelativePositionWithVelocity;
import org.ml4j.mdp.ContinuousDimension;
import org.ml4j.mdp.Dimension;
import org.ml4j.mdp.IndexMapper;

public class RelativePositionVelocityIndexMapper implements IndexMapper<TargetRelativePositionWithVelocity>,Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Dimension<Double> velocityDimension;
	private Dimension<Double> positionDimension;
	
	public RelativePositionVelocityIndexMapper()
	{
		this.velocityDimension = new ContinuousDimension(-0.5,0.5,11);
		this.positionDimension =  new ContinuousDimension(-1,1,21);
	}

	
	public TargetRelativePositionWithVelocity[] getIndexedValues()
	{
		Double[] velocities = velocityDimension.getIndexedValues();
		Double[] positions = positionDimension.getIndexedValues();
		TargetRelativePositionWithVelocity[] indexedValues = new TargetRelativePositionWithVelocity[velocities.length * positions.length];
		int index =0;
		for (Double position : positions)
		{
			for (Double vel : velocities)
			{
				indexedValues[index++] = new TargetRelativePositionWithVelocity(position,vel);
			}
		}
		return indexedValues;
	}
	
	
	
	@Override
	public int getIndex(TargetRelativePositionWithVelocity data) {
		
		int posIndex = positionDimension.getIndex(data.getPosition());
		int velIndex = velocityDimension.getIndex(data.getVelocity());
		return posIndex * velocityDimension.getIndexedValues().length + velIndex;

	}
	
	

}
