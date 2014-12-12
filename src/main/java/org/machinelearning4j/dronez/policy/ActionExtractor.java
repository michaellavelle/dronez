package org.machinelearning4j.dronez.policy;

import java.util.List;

import org.machinelearning4j.dronez.domain.DroneAction;

public interface ActionExtractor<A> {

	public A[] getActions(List<DroneAction> droneActions);
}
