package org.machinelearning4j.dronez.policy;

import org.machinelearning4j.dronez.domain.UpDownAction;
import org.ml4j.mdp.IndexMapper;

public class UpDownActionIndexMapper implements IndexMapper<UpDownAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int getIndex(UpDownAction arg0) {
		return arg0.ordinal();
	}

	@Override
	public UpDownAction[] getIndexedValues() {
		return UpDownAction.ALL_ACTIONS;
	}

}
