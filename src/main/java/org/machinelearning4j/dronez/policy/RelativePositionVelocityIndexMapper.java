package org.machinelearning4j.dronez.policy;

import java.io.Serializable;

import org.machinelearning4j.dronez.domain.PositionVelocity;
import org.ml4j.mdp.ContinuousDimension;
import org.ml4j.mdp.Dimension;
import org.ml4j.mdp.IndexMapper;

public class RelativePositionVelocityIndexMapper implements IndexMapper<PositionVelocity>,Serializable{


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

	
	public PositionVelocity[] getIndexedValues()
	{
		Double[] velocities = velocityDimension.getIndexedValues();
		Double[] positions = positionDimension.getIndexedValues();
		PositionVelocity[] indexedValues = new PositionVelocity[velocities.length * positions.length];
		int index =0;
		for (Double position : positions)
		{
			for (Double vel : velocities)
			{
				indexedValues[index++] = new PositionVelocity(position,vel);
			}
		}
		return indexedValues;
	}
	
	
	
	@Override
	public int getIndex(PositionVelocity data) {
		
		int posIndex = positionDimension.getIndex(data.getPosition());
		int velIndex = velocityDimension.getIndex(data.getVelocity());
		return posIndex * velocityDimension.getIndexedValues().length + velIndex;

	}
	
	

}
