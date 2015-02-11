package org.machinelearning4j.dronez.mock;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Date;

import org.machinelearning4j.dronez.tracking.AbstractWebCamObserver;
import org.ml4j.dronez.DroneAction;
import org.ml4j.dronez.DroneState;
import org.ml4j.imaging.SerializableBufferedImageAdapter;
import org.ml4j.imaging.targets.ImageDisplay;
import org.ml4j.imaging.tracking.Position3D;
import org.ml4j.imaging.tracking.Velocity3D;

public class MockWebCamObserver extends AbstractWebCamObserver {

	private DroneState droneState;
	private ImageDisplay<Long> imageDisplay;	
	
	public MockWebCamObserver(DroneState initialDroneState)
	{
		super();
		this.droneState = initialDroneState;
	}
	
	
	public void stateUpdatedAfterAction(DroneState droneState,DroneAction droneAction)
	{
		this.droneState = droneState;
		BufferedImage image = new BufferedImage(640,480,BufferedImage.TYPE_INT_RGB);
		
		image.getGraphics().setColor(Color.BLACK);
		Position3D droneLocation = getDroneLocation();
		if (droneLocation != null)
		{
			image.getGraphics().fillRect((int)droneLocation.getPointOnCamera().getX() - 5,(int)droneLocation.getPointOnCamera().getY() - 5,10,10);
		}
		
		image.getGraphics().drawString(targetTrajectoryIteration + " : " + droneAction.toString(), 50, 50);
		
		if (targetTrajectory != null)
		{ 
			image.getGraphics().drawLine(getX(targetTrajectory.getTarget(targetTrajectoryIteration).getLeftRightPositionVelocity().getPosition()), 0, getX(targetTrajectory.getTarget(targetTrajectoryIteration).getLeftRightPositionVelocity().getPosition()), 480);
			image.getGraphics().drawLine(0,getY(targetTrajectory.getTarget(targetTrajectoryIteration).getUpDownPositionVelocity().getPosition()), 640, getY(targetTrajectory.getTarget(targetTrajectoryIteration).getUpDownPositionVelocity().getPosition()));
			int r= (int)(0.25 * 640/5);
			image.getGraphics().drawRect(getX(targetTrajectory.getTarget(targetTrajectoryIteration).getLeftRightPositionVelocity().getPosition()) - r,getY(targetTrajectory.getTarget(targetTrajectoryIteration).getUpDownPositionVelocity().getPosition()) - r , 2 * r,2 * r);
			image.getGraphics().drawRect(getX(targetTrajectory.getTarget(targetTrajectoryIteration).getLeftRightPositionVelocity().getPosition()) - 2 * r,getY(targetTrajectory.getTarget(targetTrajectoryIteration).getUpDownPositionVelocity().getPosition()) - 2 * r , 4 * r,4 * r);

		}
		
		
		imageDisplay.onFrameUpdate(new SerializableBufferedImageAdapter(image), new Date().getTime());
		targetTrajectoryIteration++;
	}
	
	@Override
	public Position3D getDroneLocation() {
		Point point =   new Point((int)((droneState.getLeftRightPositionVelocity().getPosition() + 2.5) * 640/5),(int)((2.5 - droneState.getUpDownPositionVelocity().getPosition()) * 480/5));
		return new Position3D(droneState.getLeftRightPositionVelocity().getPosition(),droneState.getUpDownPositionVelocity().getPosition(),droneState.getForwardBackPositionVelocity().getPosition(),point);
	}
	
	public Velocity3D getDroneVelocity()
	{
		return new Velocity3D(droneState.getLeftRightPositionVelocity().getVelocity(),droneState.getUpDownPositionVelocity().getVelocity(),droneState.getForwardBackPositionVelocity().getVelocity());
	}

	

	@Override
	public void startObserving() {
		imageDisplay = new ImageDisplay<Long>(new Dimension(640,480));

	}

}
