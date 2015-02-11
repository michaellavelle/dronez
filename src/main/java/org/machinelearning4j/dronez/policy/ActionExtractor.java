package org.machinelearning4j.dronez.policy;

import java.util.List;

import org.ml4j.dronez.DroneAction;

public interface ActionExtractor<A> {

	public A[] getActions(List<DroneAction> droneActions);
}
