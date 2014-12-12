package org.machinelearning4j.dronez.policy;

import org.machinelearning4j.dronez.domain.ForwardBackAction;
import org.ml4j.mdp.IndexMapper;

public class ForwardBackActionIndexMapper implements IndexMapper<ForwardBackAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int getIndex(ForwardBackAction arg0) {
		return arg0.ordinal();
	}

	@Override
	public ForwardBackAction[] getIndexedValues() {
		return ForwardBackAction.ALL_ACTIONS;
	}

}
