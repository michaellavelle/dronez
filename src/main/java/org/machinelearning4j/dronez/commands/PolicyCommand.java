package org.machinelearning4j.dronez.commands;

import java.util.List;

import org.ml4j.mdp.Policy;
import org.ml4j.mdp.PolicyStateMapper;
import org.ml4j.mdp.StateActionSequenceListener;

// Observed state, policy state, action
public interface PolicyCommand<O, S, A> {

	public Policy<S, A> getPolicy();

	public PolicyStateMapper<O, S> getPolicyStateMapper();

	public long getIterations();

	public void addPolicySequenceListener(StateActionSequenceListener<S, S, A> policySequenceListener);

	public List<StateActionSequenceListener<S, S, A>> getPolicySequenceListeners();
}
