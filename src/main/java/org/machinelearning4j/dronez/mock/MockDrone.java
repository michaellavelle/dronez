package org.machinelearning4j.dronez.mock;

import java.util.ArrayList;
import java.util.List;

import org.machinelearning4j.dronez.domain.Drone;
import org.ml4j.dronez.DroneAction;
import org.ml4j.dronez.DroneState;
import org.ml4j.mdp.Model;

public class MockDrone extends Drone 
{
	private int actionDelayIterations;
	private List<DroneAction> requestedActions;
	private Model<DroneState, DroneState,DroneAction> droneModel;
	private MockWebCamObserver mockWebCamObserver;
	private int actionMillisecondInterval;
	
	public MockDrone(MockWebCamObserver mockWebCamObserver,Model<DroneState, DroneState,DroneAction> droneModel,int actionDelayIterations,int actionMillisecondInterval) {
		super(mockWebCamObserver);
		this.mockWebCamObserver = mockWebCamObserver;
		this.droneModel = droneModel;
		this.actionDelayIterations = actionDelayIterations;
		this.requestedActions = new ArrayList<DroneAction>();
		this.actionMillisecondInterval = actionMillisecondInterval;
	}

	@Override
	public void executeLaunchSequence() {
		System.out.println("Executing Launch Sequence");
	}

	@Override
	public void executeLandingSequence() {
		System.out.println("Executing Landing Sequence");
		
	}

	@Override
	public DroneState getCurrentState() {
		return stateObserver.getCurrentState();
	}

	@Override
	public void takeAction(DroneAction action) {
		requestedActions.add(action);
		if (requestedActions.size() > actionDelayIterations)
		{
			int iteration = requestedActions.size() - 1;
			DroneAction takenAction = requestedActions.get(iteration - actionDelayIterations);
			DroneState currentState = stateObserver.getCurrentState();
			if (currentState != null)
			{
				mockWebCamObserver.stateUpdatedAfterAction(droneModel.getState(currentState,takenAction),takenAction );
			}
		}
		
		try {
			Thread.sleep(actionMillisecondInterval);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
