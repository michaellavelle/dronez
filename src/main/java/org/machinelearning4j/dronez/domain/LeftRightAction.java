package org.machinelearning4j.dronez.domain;

public enum LeftRightAction implements NumericAction {
	
	LEFT(-1),NO_OP(0),RIGHT(1);
	
	private double value;
	
	LeftRightAction(double value)
	{
		this.value = value;
	}
	
	public static LeftRightAction[] ALL_ACTIONS = LeftRightAction.values();

	public double getValue() {
		return value;
	}
	
	
	
}
