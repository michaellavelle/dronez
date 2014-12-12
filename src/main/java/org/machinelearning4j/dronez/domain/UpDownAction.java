package org.machinelearning4j.dronez.domain;

public enum UpDownAction implements NumericAction {
	
	DOWN(1),NO_OP(0),UP(-1);
	
	UpDownAction(double value)
	{
		this.value = value;
	}
	
	private double value;
	
	public static UpDownAction[] ALL_ACTIONS = UpDownAction.values();

	public double getValue() {
		return value;
	}
	
	
	
}
