package org.machinelearning4j.dronez.policy;

import org.ml4j.dronez.LeftRightAction;
import org.ml4j.mdp.IndexMapper;

public class LeftRightActionIndexMapper implements IndexMapper<LeftRightAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int getIndex(LeftRightAction arg0) {
		return arg0.ordinal();
	}

	@Override
	public LeftRightAction[] getIndexedValues() {
		return LeftRightAction.ALL_ACTIONS;
	}

}
