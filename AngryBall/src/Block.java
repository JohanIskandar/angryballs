import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * The Class Block displays different blocks that has different strength
 */
public class Block {
	float scale = 1f;
	Image blockImg = null;
	Image blockSolidImg = null;
	Image iceSolidImg = null;
	Image iceHalfImg = null;
	Image emptyImg = null;
	int x = 0;
	int y = 0;
	// blockMode 1 is for wood
	// 2 is for ice
	int blockMode = 0;

	/**
	 * Instantiates a new block.
	 * 
	 * @throws SlickException
	 *             the slick exception
	 */
	public Block() throws SlickException {
		blockSolidImg = new Image("images/woodblocksmall.jpg");
		iceSolidImg = new Image("images/iceblockfull.png");
		iceHalfImg = new Image("images/iceblockhalf.png");
		emptyImg = new Image("images/blockempty.png");
		// blockImg = emptyImg;
	}

	/**
	 * Sets the block mode.
	 * 
	 * @param blockMode
	 *            the new block mode
	 */
	public void setBlockMode(int blockMode) {
		this.blockMode = blockMode;
	}

	/**
	 * Adds the block.
	 * 
	 * @param x
	 *            the x position
	 * @param y
	 *            the y position
	 * @param blockMode
	 *            the block mode
	 */
	public void addBlock(float x, float y, int blockMode) {

		if (blockMode == 0)
			blockImg = emptyImg;
		else if (blockMode == 1)
			blockImg = blockSolidImg;
		else if (blockMode == 5)
			blockImg = iceHalfImg;
		else if (blockMode == 6)
			blockImg = iceSolidImg;

		blockImg.draw(x, y);

		this.x = (int) x;
		this.y = (int) y;
	}

	/**
	 * Gets the block mode.
	 * 
	 * @return the block mode
	 */
	public int getBlockMode() {
		return blockMode;
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
	 * Gets the block image.
	 * 
	 * @return the block image
	 */
	public Image getBlockImage() {
		return blockImg;
	}

	/**
	 * Gets the image width.
	 * 
	 * @return the image width
	 */
	public int getImageWidth() {
		return blockImg.getWidth();
	}

	/**
	 * Gets the image height.
	 * 
	 * @return the image height
	 */
	public int getImageHeight() {
		return blockImg.getHeight();
	}

	/**
	 * Destroy the block
	 * 
	 * @param blockMode
	 *            the block mode
	 */
	public void destroy(int blockMode) {
		this.blockMode = blockMode;
	}

}
