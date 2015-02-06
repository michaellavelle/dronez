package org.machinelearning4j.dronez.policy;

import org.ml4j.dronez.SpinAction;
import org.ml4j.mdp.IndexMapper;

public class SpinActionIndexMapper implements IndexMapper<SpinAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int getIndex(SpinAction arg0) {
		return arg0.ordinal();
	}

	@Override
	public SpinAction[] getIndexedValues() {
		return SpinAction.ALL_ACTIONS;
	}

}
