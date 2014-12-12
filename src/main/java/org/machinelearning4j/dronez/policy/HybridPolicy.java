package org.machinelearning4j.dronez.policy;

import java.io.Serializable;

import org.machinelearning4j.dronez.domain.PositionVelocityWithRecentActions;
import org.ml4j.mdp.Policy;

public class HybridPolicy<A extends Serializable> implements Policy<PositionVelocityWithRecentActions<A>, A> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Policy<PositionVelocityWithRecentActions<A>, A> outer;
	private Policy<PositionVelocityWithRecentActions<A>, A> inner;
	private double boundary;
	
	public HybridPolicy(Policy<PositionVelocityWithRecentActions<A>, A> inner,Policy<PositionVelocityWithRecentActions<A>, A> outer,double boundary)
	{
		this.inner = inner;
		this.outer = outer;
		this.boundary = boundary;
	}
	


	@Override
	public A getAction(PositionVelocityWithRecentActions<A> state) {
		return Math.abs(state.getPosition()) < boundary ? inner.getAction(state) : outer.getAction(state);
	}

}