package org.machinelearning4j.dronez.domain;

public enum SpinAction {
	
	ANTI_CLOCKWISE,NO_OP,CLOCKWISE;
	
	public static SpinAction[] ALL_ACTIONS = SpinAction.values();

	public double getValue() {
		return ordinal() - 1;
	}
	
	
	
}
