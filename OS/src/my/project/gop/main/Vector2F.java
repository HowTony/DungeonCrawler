package my.project.gop.main;

public class Vector2F {

	public float mXPosition;
	public float mYPosition;

	public static float mWorldXPosition;
	public static float mWorldYPosition;

	public Vector2F() {
		this.mXPosition = 0.0f;
		this.mYPosition = 0.0f;

	}

	public Vector2F(float xPos, float yPos) {
		this.mXPosition = xPos;
		this.mYPosition = yPos;
	}

	public static Vector2F zero() {
		return new Vector2F(0, 0);
	}

	public void normalize() {
		double length = Math.sqrt((mXPosition * mXPosition) + (mYPosition * mYPosition));

		if (length != 0.0) {
			float s = 1.0f / (float) length;
			mXPosition = mXPosition * s;
			mYPosition = mYPosition * s;
		}
	}

	public Vector2F getScreenLocation() {
		return new Vector2F(mXPosition, mYPosition);
	}

	public Vector2F getWorldLocation() {
		return new Vector2F(mXPosition - mWorldXPosition, mYPosition - mWorldYPosition);
	}

	public boolean equals(Vector2F vector) {
		return (this.mXPosition == vector.mXPosition && this.mYPosition == vector.mYPosition);

	}

	public Vector2F copy(Vector2F vectorToCopy) {
		mXPosition = vectorToCopy.mXPosition;
		mYPosition = vectorToCopy.mYPosition;

		return new Vector2F(mXPosition, mYPosition);
	}

	public Vector2F add(Vector2F vector) {
		mXPosition += vector.mXPosition;
		mYPosition += vector.mYPosition;

		return new Vector2F(mXPosition, mYPosition);
	}

	public static void setWorldVariables(float worldXPosition, float worldYPosition) {
		mWorldXPosition = worldXPosition;
		mWorldYPosition = worldYPosition;
	}

	public static double getDistanceOnScreen(Vector2F firstVector, Vector2F secondVector) {
		float v1 = firstVector.mXPosition - secondVector.mXPosition;
		float v2 = firstVector.mYPosition - secondVector.mYPosition;
		return Math.sqrt((v1 * v1) + (v2 * v2));
	}

	public double getDistanceBetweenWorldVectors(Vector2F vector) {
		float dx = Math.abs(getWorldLocation().mXPosition - vector.getWorldLocation().mXPosition);
		float dy = Math.abs(getWorldLocation().mYPosition - vector.getWorldLocation().mYPosition);
		return Math.abs(dx * dx - dy * dy);
	}

}
