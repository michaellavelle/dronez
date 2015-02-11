/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.machinelearning4j.dronez.util;

import org.ml4j.dronez.DroneAction;
import org.ml4j.dronez.DroneState;
import org.ml4j.mdp.StateActionSequenceListener;
import org.ml4j.mdp.Trajectory;

/**
 * Can be registered with a drone PolicyExecutor and displays drone's current position and
 * target position for each iteration.  Used for demo purposes only.
 * 
 * @author Michael Lavelle
 *
 */
public class DronePositionPrinter implements StateActionSequenceListener<DroneState, DroneState,DroneAction> {

	private Trajectory<DroneState> targetPositionTrajectory;
	private double minPosition;
	private double maxPosition;
	private boolean visual;
	private double scaleFromPlusMinus50;
	private String prefix;
	
	public void setTargetTrajectory(Trajectory<DroneState> targetPositionTrajectory)
	{
		this.targetPositionTrajectory = targetPositionTrajectory;
	}

	public DronePositionPrinter(double minPosition,double maxPosition,boolean visual,double scaleFromPlusMinus50,String prefix) {
		this.minPosition = minPosition;
		this.maxPosition = maxPosition;
		this.visual = visual;
		this.scaleFromPlusMinus50 =  scaleFromPlusMinus50;
		this.prefix = prefix;
	}

	public void printDronePositionAndTargetPosition(DroneState point,long iteration) {
		Integer targetPosition = null;
		if (targetPositionTrajectory != null)
		{
			targetPosition = (int) (targetPositionTrajectory.getTarget(iteration).getLeftRightPositionVelocity().getPosition()/scaleFromPlusMinus50);
		}
		int dronePosition = (int) (point.getLeftRightPositionVelocity().getPosition()/scaleFromPlusMinus50);
		
		String display = "";
		for (int i = (int)(minPosition/scaleFromPlusMinus50); i <= maxPosition/scaleFromPlusMinus50; i++) {
			if (targetPosition != null && targetPosition== i && i == dronePosition) {
				display = display + "X";
			} else if (targetPosition != null && targetPosition == i) {
				display = display + "x";
			} else if (i == dronePosition) {
				display = display + "*";
			} else {
				display = display + " ";
			}
		}
		System.out.println(prefix + ":" + display);
	}

	@Override
	public void onStateActionStateSequence(long iteration, DroneState initalState, DroneAction action,
			DroneState endState) {
		if (visual)
		{
			printDronePositionAndTargetPosition(endState,iteration);
		}
		else
		{
			Double targetPosition = null;
			Double targetUpPosition = null;
			Double targetForwardPosition = null;
			if (targetPositionTrajectory != null)
			{
				targetPosition = targetPositionTrajectory.getTarget(iteration).getLeftRightPositionVelocity().getPosition();
				targetUpPosition = targetPositionTrajectory.getTarget(iteration).getUpDownPositionVelocity().getPosition();
				targetForwardPosition = targetPositionTrajectory.getTarget(iteration).getForwardBackPositionVelocity().getPosition();

			}
			System.out.println("left:" + iteration + "," + targetPosition + "," + initalState.getLeftRightPositionVelocity().getPosition() + "," + action.getLeftRightAction() + "," + endState.getLeftRightPositionVelocity().getPosition()+ "," + endState.getLeftRightPositionVelocity().getVelocity());
			System.out.println("up:" + iteration + "," + targetUpPosition + "," + initalState.getUpDownPositionVelocity().getPosition() + "," + action.getUpDownAction() + "," + endState.getUpDownPositionVelocity().getPosition()+ "," + endState.getUpDownPositionVelocity().getVelocity());
			System.out.println("forward:" + iteration + "," + targetForwardPosition + "," + initalState.getForwardBackPositionVelocity().getPosition() + "," + action.getForwardBackAction() + "," + endState.getForwardBackPositionVelocity().getPosition()+ "," + endState.getForwardBackPositionVelocity().getVelocity());

		}
	}

}
