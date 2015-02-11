package org.machinelearning4j.dronez.tracking;

import java.awt.Point;

import org.ml4j.dronez.DroneState;
import org.ml4j.dronez.PositionVelocity;
import org.ml4j.imaging.tracking.Position3D;
import org.ml4j.imaging.tracking.Velocity3D;
import org.ml4j.mdp.StateObserver;
import org.ml4j.mdp.Trajectory;

public abstract class AbstractWebCamObserver implements StateObserver<DroneState>{

	
	public abstract Position3D getDroneLocation();
	
	public abstract Velocity3D getDroneVelocity();
	
	
	protected Trajectory<DroneState> targetTrajectory;
	protected int targetTrajectoryIteration;
	
	public AbstractWebCamObserver()
	{
	}
	
	
	
	public void setTargetTrajectory(Trajectory<DroneState> targetTrajectory) {
		this.targetTrajectory = targetTrajectory;
		this.targetTrajectoryIteration = 0;
	}
	
	
	public double getSpinVelocity()
	{
		return 0d;
	}

	
	public double getLeftRightPosition(Position3D point)
	{
		return point.getLeftRightDist();
	}
	
	public Position3D getPosition3D(Point point,double halfDistanceBetweenLeds)
	{
		PixelToPositionEstimator estimator = new PixelToPositionEstimator(halfDistanceBetweenLeds);
		return new Position3D(estimator.getX(point.getX()),point.getY(),2d,point);
	}
	
	protected int getX(double leftRightPosition)
	{
		return (int)((leftRightPosition + 2.5) * 640/5);
	}
	
	protected int getY(double upDownPosition)
	{
		return (int)((2.5 - upDownPosition) * 480/5);
	}
	
	public double getUpDownPosition(Position3D point)
	{
		return point.getUpDownDist();
	}
	
	public double getSpinPosition(Position3D position)
	{
		return 0d;
	}
	
	public double getForwardBackPosition(Position3D point)
	{
		return point.getForwardDist();
	}
	
	public abstract void startObserving();
	
	
	@Override
	public DroneState getCurrentState() {
		Position3D position = getDroneLocation();
		Velocity3D velocity = getDroneVelocity();

		if (position != null && velocity != null)
		{
			
			
			return new DroneState(new PositionVelocity(getLeftRightPosition(position),velocity.getLeftRightVel()),
					new PositionVelocity(getUpDownPosition(position),velocity.getUpDownVel()),
					new PositionVelocity(getForwardBackPosition(position),velocity.getForwardVel()), new PositionVelocity(this.getSpinPosition(position),this.getSpinVelocity()));
		}
		else
		{
			return null;
		}
	}


}
