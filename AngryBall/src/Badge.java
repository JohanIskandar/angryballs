import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * The Class Badge will show achievement.
 */
public class Badge {
	float scale = 1f;
	Image starImg = null;
	Image starOne = null;
	Image starTwo = null;
	Image starThree = null;
	Image starFour = null;
	Image starFive = null;
	Image starSix = null;
	Image starSeven = null;

	int x = 0;
	int y = 0;
	// blockMode 1 is for wood
	// 2 is for ice
	int starMode = 1;

	/**
	 * Instantiates a new badge.
	 * 
	 * @throws SlickException
	 *             the slick exception
	 */
	public Badge() throws SlickException {
		starOne = new Image("images/star1.png");
		starTwo = new Image("images/star2.png");
		starThree = new Image("images/star3.png");
		starFour = new Image("images/star4.png");
		starFive = new Image("images/star5.png");
		starSix = new Image("images/star6.png");
		starSeven = new Image("images/star7.png");

		starImg = starOne;
	}

	/**
	 * Sets the star mode.
	 * 
	 * @param starMode
	 *            the new star mode
	 */
	public void setStarMode(int starMode) {
		this.starMode = starMode;
	}

	/**
	 * Adds the star.
	 * 
	 * @param x
	 *            the x position
	 * @param y
	 *            the y position
	 * @param starMode
	 *            the star mode
	 */
	public void addStar(float x, float y, int starMode) {

		if (starMode == 1)
			starImg = starOne;
		else if (starMode == 2)
			starImg = starTwo;
		else if (starMode == 3)
			starImg = starThree;
		else if (starMode == 4)
			starImg = starFour;
		else if (starMode == 5)
			starImg = starFive;
		else if (starMode == 6)
			starImg = starSix;
		else if (starMode == 7)
			starImg = starSeven;

		starImg.draw(x, y);

		this.x = (int) x;
		this.y = (int) y;
	}

	/**
	 * Gets the star mode.
	 * 
	 * @return the star mode
	 */
	public int getStarMode() {
		return starMode;
	}

	/**
	 * Gets the x position
	 * 
	 * @return the x position
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y position
	 * 
	 * @return the y position
	 */
	public int getY() {
		return y;
	}

	/**
	 * Gets the badge image.
	 * 
	 * @return the badge image
	 */
	public Image getBadgeImage() {
		return starImg;
	}

	/**
	 * Gets the image width.
	 * 
	 * @return the image width
	 */
	public int getImageWidth() {
		return starImg.getWidth();
	}

	/**
	 * Gets the image height.
	 * 
	 * @return the image height
	 */
	public int getImageHeight() {
		return starImg.getHeight();
	}

}
