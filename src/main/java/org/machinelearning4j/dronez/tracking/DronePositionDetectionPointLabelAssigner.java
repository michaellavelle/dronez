package org.machinelearning4j.dronez.tracking;

import java.awt.Point;

import org.ml4j.imaging.SerializableBufferedImageAdapter;
import org.ml4j.imaging.tracking.Position3D;
import org.ml4j.mapping.LabelAssigner;
import org.ml4j.mapping.LabeledData;

public class DronePositionDetectionPointLabelAssigner implements
		LabelAssigner<SerializableBufferedImageAdapter, Position3D> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Point position;
	private boolean sticky;

	private int halfDroneDistanceXY;

	public DronePositionDetectionPointLabelAssigner(int frameWidth,
			int frameHeight, boolean stickyCog) {
		this.sticky = stickyCog;
	}

	public Position3D getPointInSpace(Point point,
			double halfDistanceBetweenLeds) {
		if (point == null)
			return null;
		if (halfDistanceBetweenLeds == 0)
			throw new RuntimeException("no leds");
		PixelToPositionEstimator estimator = new PixelToPositionEstimator(
				halfDistanceBetweenLeds);
		return new Position3D(estimator.getX(point.getX()),
				estimator.getY(point.getY()),
				estimator.getZ(halfDistanceBetweenLeds), point);
	}

	@Override
	public LabeledData<SerializableBufferedImageAdapter, Position3D> assignLabel(
			SerializableBufferedImageAdapter data) {

		Point newPos = null;
		Point left = null;
		Point right = null;
		int w = data.getImage().getWidth();
		int h = data.getImage().getHeight();
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {

				int pixelCol = data.getImage().getRGB(x, y);

				int pixelColGreen = pixelCol;
				pixelCol &= 0x0FFFF0000;
				pixelColGreen &= 0x0FF00FF00;

				int r = (pixelCol >> 16) & 0xff;
				int g = (pixelColGreen >> 8) & 0xff;
				if (g > 242 && r < 210) {
					left = new Point(x, y);
				}
				if (r > 249 && g < 210) {
					right = new Point(x, y);
				}

			}
		}

		if (left != null && right == null) {
			if (position != null) {
				newPos = new Point((int) (left.getX() + halfDroneDistanceXY),
						(int) left.getY());
			}
		}
		if (left == null && right != null) {
			if (position != null) {
				newPos = new Point((int) (right.getX() - halfDroneDistanceXY),
						(int) right.getY());
			}
		}
		if (left != null && right != null) {
			halfDroneDistanceXY = (int) (right.getX() - left.getX()) / 2;
			newPos = new Point((int) (right.getX() + left.getX()) / 2,
					(int) (right.getY() + left.getY()) / 2);

		}
		if (newPos != null) {
			position = newPos;
			Position3D pointInSpace = getPointInSpace(newPos,
					halfDroneDistanceXY);
			return new LabeledData<SerializableBufferedImageAdapter, Position3D>(
					data, pointInSpace);
		} else {
			if (sticky) {
				Position3D pointInSpace = getPointInSpace(position,
						halfDroneDistanceXY);

				return new LabeledData<SerializableBufferedImageAdapter, Position3D>(
						data, pointInSpace);
			} else {

				return new LabeledData<SerializableBufferedImageAdapter, Position3D>(
						data, null);

			}
		}

	}

}
