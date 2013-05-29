import org.newdawn.slick.SlickException;

/**
 * The Class Sprite deals with all the images to be drawned
 */
public abstract class Sprite {

	/**
	 * Instantiates a new sprite.
	 * 
	 * @throws SlickException
	 *             the slick exception
	 */
	public Sprite() throws SlickException {
	}

	/**
	 * Drop the object
	 * 
	 * @param x
	 *            the x position
	 * @param y
	 *            the y position
	 * @param scale
	 *            the scale
	 */
	public abstract void drop(float x, float y, float scale);

	/**
	 * Sets the rotation.
	 * 
	 * @param hip
	 *            the new rotation
	 */
	public abstract void setRotation(float hip);

	/**
	 * Gets the rotation.
	 * 
	 * @return the rotation
	 */
	public abstract float getRotation();

	/**
	 * Gets the x position
	 * 
	 * @return the x position
	 */
	public abstract int getX();

	/**
	 * Gets the y position
	 * 
	 * @return the y position
	 */
	public abstract int getY();

}
