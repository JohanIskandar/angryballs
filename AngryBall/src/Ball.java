import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * The Class Ball will shows the different balls that has different power.
 */
public class Ball extends Sprite {

	float scale = 1f;
	// float rotation=0f; //face downward
	float hip = 180f;
	int x;
	int y;
	int ballMode = 0;
	int ballType = 0;

	Image ball = null;
	Image ball1 = null;
	Image ball2 = null;

	/**
	 * Instantiates a new ball.
	 * 
	 * @throws SlickException
	 *             the slick exception
	 */
	public Ball() throws SlickException {
		ball1 = new Image("images/yellowball1.png");
		ball2 = new Image("images/yellowball2.png");

		randomBallMode();
		ball.rotate(hip);
	}

	/**
	 * Random ball mode.
	 */
	public void randomBallMode() {
		ballType = 1 + (int) Math.round(Math.random()) * 1;
		if (ballType == 1)
			ball = ball1;
		else if (ballType == 2)
			ball = ball2;
	}

	/**
	 * Gets the ball mode.
	 * 
	 * @param ballMode
	 *            the ball mode
	 * @return the ball mode
	 */
	public void getBallMode(int ballMode) {
		this.ballMode = ballMode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Sprite#drop(float, float, float)
	 */
	public void drop(float x, float y, float scale) {
		ball.draw(x, y, scale);
		this.x = (int) x;
		this.y = (int) y;
	}

	/*
	 * Set the rotation of the ball
	 * 
	 * @param hip the rotation angle
	 * 
	 * @see Sprite#setRotation(float)
	 */
	public void setRotation(float hip) {
		this.hip = hip;
		ball.rotate(hip);
	}

	/*
	 * Get the rotation of the ball
	 * 
	 * @see Sprite#getRotation()
	 */
	public float getRotation() {
		return ball.getRotation();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Sprite#getX()
	 */
	public int getX() {
		return x;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Sprite#getY()
	 */
	public int getY() {
		return y;
	}

	/**
	 * Gets the ball image.
	 * 
	 * @return the ball image
	 */
	public Image getBallImage() {
		return ball;
	}

}
