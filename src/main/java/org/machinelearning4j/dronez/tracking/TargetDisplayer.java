package org.machinelearning4j.dronez.tracking;

import java.awt.image.BufferedImage;

import org.ml4j.dronez.DroneState;
import org.ml4j.imaging.FrameDecorator;
import org.ml4j.imaging.SerializableBufferedImageAdapter;
import org.ml4j.imaging.tracking.Position3D;
import org.ml4j.mapping.LabeledData;
import org.ml4j.mdp.Trajectory;

public class TargetDisplayer implements FrameDecorator<LabeledData<SerializableBufferedImageAdapter, Position3D>, Long> {
	private WebCamObserver webCamObserver;

	public TargetDisplayer(WebCamObserver webCamObserver) {
		this.webCamObserver = webCamObserver;
	}

	@Override
	public LabeledData<SerializableBufferedImageAdapter, Position3D> decorateFrame(
			LabeledData<SerializableBufferedImageAdapter, Position3D> arg0, Long arg1) {
		if (arg0 != null && arg0.getData() != null && webCamObserver.getTargetTrajectory() != null) {
			BufferedImage image = arg0.getData().getImage();
			int iteration = webCamObserver.getIteration();
			Trajectory<DroneState> targetTrajectory = webCamObserver.getTargetTrajectory();
			image.getGraphics()
					.drawLine(
							(int) (targetTrajectory.getTarget(iteration).getLeftRightPositionVelocity().getPosition() * 640 / 5 + 640 / 2),
							0,
							(int) (targetTrajectory.getTarget(iteration).getLeftRightPositionVelocity().getPosition() * 640 / 5 + 640 / 2),
							480);
			image.getGraphics()
					.drawLine(
							0,
							480 / 2 - (int) (targetTrajectory.getTarget(iteration).getUpDownPositionVelocity()
									.getPosition() * 480 / 5),
							640,
							480 / 2 - (int) (targetTrajectory.getTarget(iteration).getUpDownPositionVelocity()
									.getPosition() * 480 / 5));
			image.getGraphics()
					.drawRect(
							(int) (targetTrajectory.getTarget(iteration).getLeftRightPositionVelocity().getPosition() * 640 / 5) - 100 + 640 / 2,
							480 / 2 - 100 - (int) (targetTrajectory.getTarget(iteration).getUpDownPositionVelocity()
									.getPosition() * 480 / 5), 200, 200);
		}
		return arg0;
	}

	@Override
	public double getScaleFactor() {
		// TODO Auto-generated method stub
		return 1;
	}

}
