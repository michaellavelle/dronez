package org.machinelearning4j.dronez.commands;

import java.util.ArrayList;
import java.util.List;

import org.ml4j.mdp.StateActionSequenceListener;

public abstract class AbstractPolicyCommand<O, S, A> implements PolicyCommand<O, S, A> {

	private int iterations;
	private List<StateActionSequenceListener<S, S, A>> policyListeners;

	public AbstractPolicyCommand(int iterations) {
		this.policyListeners = new ArrayList<StateActionSequenceListener<S, S, A>>();
		this.iterations = iterations;
	}

	@Override
	public long getIterations() {
		return iterations;
	}

	@Override
	public void addPolicySequenceListener(StateActionSequenceListener<S, S, A> policySequenceListener) {
		policyListeners.add(policySequenceListener);
	}

	@Override
	public List<StateActionSequenceListener<S, S, A>> getPolicySequenceListeners() {
		return policyListeners;
	}

}
