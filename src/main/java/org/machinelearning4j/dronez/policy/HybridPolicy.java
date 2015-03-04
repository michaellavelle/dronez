package org.machinelearning4j.dronez.policy;

import org.ml4j.dronez.NumericAction;
import org.ml4j.dronez.TargetRelativePositionWithVelocityAndRecentActions;
import org.ml4j.mdp.Policy;

public class HybridPolicy<A extends NumericAction> implements
		Policy<TargetRelativePositionWithVelocityAndRecentActions<A>, A> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Policy<TargetRelativePositionWithVelocityAndRecentActions<A>, A> outer;
	private Policy<TargetRelativePositionWithVelocityAndRecentActions<A>, A> inner;
	private double boundary;

	public HybridPolicy(Policy<TargetRelativePositionWithVelocityAndRecentActions<A>, A> inner,
			Policy<TargetRelativePositionWithVelocityAndRecentActions<A>, A> outer, double boundary) {
		this.inner = inner;
		this.outer = outer;
		this.boundary = boundary;
	}

	@Override
	public A getAction(TargetRelativePositionWithVelocityAndRecentActions<A> state) {
		return Math.abs(state.getPosition()) < boundary ? inner.getAction(state) : outer.getAction(state);
	}

}