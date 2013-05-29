import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * The Class Gem displays gems
 */
public class Gem {
	float scale = 1f;
	Image emptyImg = null;
	Image gemImg = null;
	Image gem1 = null;
	Image gem2 = null;
	Image gem3 = null;
	Image gem4 = null;
	Image gem5 = null;

	int x = 0;
	int y = 0;
	int gemMode = 1;

	/**
	 * Instantiates a new gem.
	 * 
	 * @throws SlickException
	 *             the slick exception
	 */
	public Gem() throws SlickException {
		emptyImg = new Image("images/blockempty.png");
		gem1 = new Image("images/gem1.png");
		gem2 = new Image("images/gem2.png");
		gem3 = new Image("images/gem3.png");
		gem4 = new Image("images/gem4.png");
		gem5 = new Image("images/gem5.png");
		emptyImg = new Image("images/blockempty.png");
		// set the default gem image to nothing
		gemImg = emptyImg;
	}

	/**
	 * Adds the gem with different kind of gem
	 * 
	 * @param x
	 *            the x position
	 * @param y
	 *            the y position
	 * @param gemMode
	 *            the gem mode
	 */
	public void addGem(float x, float y, int gemMode) {
		if (gemMode == 0)
			gemImg = emptyImg;
		else if (gemMode == 1)
			gemImg = gem1;
		else if (gemMode == 2)
			gemImg = gem2;
		else if (gemMode == 3)
			gemImg = gem3;
		else if (gemMode == 4)
			gemImg = gem4;
		else if (gemMode == 5)
			gemImg = gem5;

		gemImg.draw(x, y);

		this.x = (int) x;
		this.y = (int) y;
	}

	/**
	 * Sets the gem mode.
	 * 
	 * @param gemMode
	 *            the new gem mode
	 */
	public void setGemMode(int gemMode) {
		this.gemMode = gemMode;
	}

	/**
	 * Gets the gem mode.
	 * 
	 * @return the gem mode
	 */
	public int getGemMode() {
		return gemMode;
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
	 * Gets the gem image.
	 * 
	 * @return the gem image
	 */
	public Image getGemImage() {
		return gemImg;
	}

	/**
	 * Gets the image width.
	 * 
	 * @return the image width
	 */
	public int getImageWidth() {
		return gemImg.getWidth();
	}

	/**
	 * Gets the image height.
	 * 
	 * @return the image height
	 */
	public int getImageHeight() {
		return gemImg.getHeight();
	}

	/**
	 * Destroy.
	 * 
	 * @param gemMode
	 *            the gem mode
	 */
	public void destroy(int gemMode) {
		this.gemMode = gemMode;
	}
}
