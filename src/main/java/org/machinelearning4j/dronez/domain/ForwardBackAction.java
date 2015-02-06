package org.machinelearning4j.dronez.domain;

public enum ForwardBackAction implements NumericAction {
	
	BACK,NO_OP,FORWARD;
	
	public static ForwardBackAction[] ALL_ACTIONS = ForwardBackAction.values();

	public double getValue() {
		return -(ordinal() - 1);
	}
	
	
	
}
