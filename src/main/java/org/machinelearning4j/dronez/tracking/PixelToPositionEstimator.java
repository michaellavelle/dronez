package org.machinelearning4j.dronez.tracking;

public class PixelToPositionEstimator {

	private double Z;

	public static void main(String[] args) {
		PixelToPositionEstimator positionEstimator = new PixelToPositionEstimator(42);
		double x = 640 / 2 + 1;
		double y = 480 / 2;
		System.out
				.println(positionEstimator.getX(x) + "," + positionEstimator.getY(y) + "," + positionEstimator.getZ());
	}

	public PixelToPositionEstimator(double halfDroneLedDistanceInPx) {
		this.Z = getZ(halfDroneLedDistanceInPx);
	}

	public double getZ(double halfDroneLedDistanceInPx) {
		return 84 / halfDroneLedDistanceInPx;
	}

	public double getX(double x) {
		return (x - (640 / 2)) * Z / getScaledFocalLength();
	}

	public double getY(double y) {
		return ((480 / 2) - y) * Z / getScaledFocalLength();
	}

	public double getScaledFocalLength() {
		return 730;
	}

	public double getZ() {
		return Z;
	}

}
