package org.machinelearning4j.dronez.tracking;

import java.awt.Dimension;

import org.ml4j.dronez.DroneAction;
import org.ml4j.dronez.DroneState;
import org.ml4j.imaging.FrameSequenceSource;
import org.ml4j.imaging.SerializableBufferedImageAdapter;
import org.ml4j.imaging.labeling.LabeledDataFrameUpdateListenerAdapter;
import org.ml4j.imaging.labeling.LabelingFrameSequenceSource;
import org.ml4j.imaging.sources.Position3DLabelingFrameSequenceSourceFactory;
import org.ml4j.imaging.sources.WebcamImageExtractor;
import org.ml4j.imaging.targets.ImageDisplay;
import org.ml4j.imaging.tracking.FIRVelocityFilter;
import org.ml4j.imaging.tracking.MovingTargetPositionEstimator;
import org.ml4j.imaging.tracking.Position3D;
import org.ml4j.imaging.tracking.Velocity3D;
import org.ml4j.imaging.tracking.VelocityFilter;
import org.ml4j.mapping.LabelAssigner;
import org.ml4j.mdp.StateActionController;
import org.ml4j.mdp.Trajectory;

// TODO
public class WebCamObserver extends AbstractWebCamObserver implements StateActionController<DroneState, DroneAction> {

	private MovingTargetPositionEstimator movingTargetPositionEstimator;
	//private Runnable r = null;
	private String directoryPath;
	private Trajectory<DroneState> targetTrajectory;

	private FrameSequenceSource<SerializableBufferedImageAdapter,Long> webcamImageExtractor;

	
	private int iteration = 0;

	public Trajectory<DroneState> getTargetTrajectory() {
		return targetTrajectory;
	}

	public int getIteration() {
		return iteration;
	}

	public void setTargetTrajectory(Trajectory<DroneState> targetTrajectory) {
		this.targetTrajectory = targetTrajectory;

		this.iteration = 0;
	}

	public WebCamObserver() {
		this(null);
	}
	
	

	@Override
	public DroneState getCurrentState() {
		try {
			((WebcamImageExtractor)webcamImageExtractor).extractFrames(1, 0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return super.getCurrentState();
	}

	public WebCamObserver(String directoryPath) {
		super();
		VelocityFilter leftRightVelocityFilter = new FIRVelocityFilter(0, 5, 150d / 1000d);
		VelocityFilter upDownVelocityFilter = new FIRVelocityFilter(0, 5, 150d / 1000d);
		VelocityFilter forwardBackVelocityFilter = new FIRVelocityFilter(0, 5, 150d / 1000d);

		this.movingTargetPositionEstimator = new MovingTargetPositionEstimator(leftRightVelocityFilter,
				upDownVelocityFilter, forwardBackVelocityFilter);
		this.directoryPath = directoryPath;
	}

	@Override
	public Position3D getDroneLocation() {
		Position3D position3D = movingTargetPositionEstimator.getEstimatedPosition();
		;
		return position3D;
	}

	@Override
	public Velocity3D getDroneVelocity() {
		Velocity3D velocity3D = movingTargetPositionEstimator.getEstimatedVelocity();
		return velocity3D;
	}

	private FrameSequenceSource<SerializableBufferedImageAdapter, Long> createWebcamFrameSequenceSource() {
		return new WebcamImageExtractor(new Dimension(640, 480));
	}

	@Override
	public void startObserving() {

		System.out.println("Starting webcam");
		webcamImageExtractor = createWebcamFrameSequenceSource();

		boolean stickyCog = true;
		LabelAssigner<SerializableBufferedImageAdapter, Position3D> motionCOGLabelAssigner =
		// new MotionCOGPointLabelAssigner(webcamImageExtractor.getFrameWidth(),
		// webcamImageExtractor.getFrameHeight(),
		// stickyCog);

		new DronePositionDetectionPointLabelAssigner(webcamImageExtractor.getFrameWidth(),
				webcamImageExtractor.getFrameHeight(), stickyCog);

		// Create a point-labeling frame sequence source for the webcam images
		// and motion cog label assigner
		// Decorate the images by adding a tracking box for the labeled point
		Position3DLabelingFrameSequenceSourceFactory<Long> application = new Position3DLabelingFrameSequenceSourceFactory<Long>(
				webcamImageExtractor, motionCOGLabelAssigner, 10, 10, true);

		LabelingFrameSequenceSource<SerializableBufferedImageAdapter, Long, Position3D> labeledFrameSequenceSource = application
				.getLabeledFrameSequenceSource();

		// Configure the moving target position estimator to listen to the
		// motion-COG-labeled frame sequence source
		labeledFrameSequenceSource.addFrameUpdateListener(movingTargetPositionEstimator);

		labeledFrameSequenceSource.addFrameDecorator(new TargetDisplayer(this));

		labeledFrameSequenceSource
				.addFrameUpdateListener(new LabeledDataFrameUpdateListenerAdapter<SerializableBufferedImageAdapter, Position3D, Long>(
						new ImageDisplay<Long>(labeledFrameSequenceSource.getFrameWidth(), labeledFrameSequenceSource
								.getFrameHeight())));

		// Displaying the images when they are extracted
		if (directoryPath != null) {
			// labeledFrameSequenceSource.addFrameUpdateListener(
			// new
			// DirectoryLabeledBufferedImageWriter<Long,PointInSpace>(directoryPath,new
			// FilenameFromTimestampAndPointGenerator("jpg")));

		}
		/*
		r = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					((WebcamImageExtractor) webcamImageExtractor).extractFrames(10000, 20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		};

		new Thread(r).start();
		*/

	}

	@Override
	public void takeAction(DroneAction arg0) {
		// TODO Auto-generated method stub
		iteration++;
	}

}
