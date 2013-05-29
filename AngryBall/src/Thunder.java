import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Path;

/**
 * Generates a 2D lightning bolt graphic in Slick.
 * 
 * @author davedes and extended by Johan Iskandar
 */
public class Thunder extends Amulet {

	// play around with these values to fine-tune the aesthetic
	final float HEIGHT_MIN = 250;
	final float HEIGHT_MAX = 400;
	final float X_MIN = -3; // The randomized X offset of each segment
	final float X_MAX = 3;
	final float Y_MIN = 1; // The randomized Y offset to start the next segment
							// (min = 0)
	final float Y_MAX = 8;
	final float BEND_MIN = 0.3f; // Amount the bolt 'bends' in the middle (min =
									// 0)
	final float BEND_MAX = 1.7f;
	final int BRANCHES_MIN = 2; // How many branches connected to the main bolt
	final int BRANCHES_MAX = 7;
	final float BRANCH_SCALE_MIN = .10f; // How much to scale each connecting
											// branch
	final float BRANCH_SCALE_MAX = .45f;
	final float BRANCH_ROTATE_MIN = -20f; // How much to rotate the branch
	final float BRANCH_ROTATE_MAX = 20f;
	final Color GLOW_COLOR = new Color(0.70f, 0.75f, 1f); // Bolt outer-glow
															// colour
	final Color LINE_COLOR = new Color(0.95f, 0.95f, 1f); // Bolt line colour
	final Color BORDER_COLOR = new Color(1f, 0.25f, 0.25f, 0.75f); // Image
																	// border
																	// colour

	Image bolt;
	Graphics g;
	Path main;
	float height;
	List<Branch> children;

	private boolean border = false;
	final Random RND = new Random();
	int x = 200;
	int y = 100;
	int noOfThunder = 3; // set initial

	/**
	 * Instantiates a new thunder.
	 * 
	 * @throws SlickException
	 *             the slick exception
	 */
	public Thunder() throws SlickException {
	}

	/**
	 * Generate thunder.
	 * 
	 * @throws SlickException
	 *             the slick exception
	 */
	public void generateThunder() throws SlickException {
		// Create the image we will use for rendering
		// 256 is good since that's what OpenGL will store it as anyways
		bolt = new Image(256, (int) HEIGHT_MAX);
		bolt.setCenterOfRotation(bolt.getWidth() / 2f, 0f);
		g = bolt.getGraphics();
		refresh();

		// render the bolt image at the given location with 90% scaling to
		// smooth
		// it out even more
		bolt.draw(x, y, 0.9f);

		// render the border / bounding box
		if (border) {
			g.setColor(BORDER_COLOR);
			g.drawRect(x, y, bolt.getWidth(), bolt.getHeight());
		}
		g.setColor(Color.white);
	}

	/**
	 * Random the spark of the thunder
	 * 
	 * @param low
	 *            the low
	 * @param high
	 *            the high
	 * @return the int
	 */
	private int rnd(int low, int high) {
		return RND.nextInt(high - low) + low;
	}

	/**
	 * Random the branch
	 * 
	 * @param low
	 *            the low
	 * @param high
	 *            the high
	 * @return the float
	 */
	private float rnd(float low, float high) {
		return low + (RND.nextFloat() * (high - low));
	}

	/**
	 * Refresh the branching
	 * 
	 * @throws SlickException
	 *             the slick exception
	 */
	private void refresh() throws SlickException {
		height = rnd(HEIGHT_MIN, HEIGHT_MAX);

		// create the main bolt based on the given height
		// each bolt is created from origin (0, 0)
		main = createPath(height);

		// create some children to branch out
		int childCount = rnd(BRANCHES_MIN, BRANCHES_MAX);
		children = new ArrayList<Branch>(childCount);
		for (int i = 0; i < childCount; i++) {
			// the "child size" is a scaled amount of the main bolt's height
			float cSize = rnd(BRANCH_SCALE_MIN, BRANCH_SCALE_MAX);

			// here we grab a random point halfway down the main bolt
			float[] p = main.getPoint(RND.nextInt(main.getPointCount() / 2));

			// and we create a new 'branch' that will extend from that point
			Branch b = new Branch(createPath(height * cSize), p[0], p[1]);
			children.add(b);
		}
		// clear the buffer so we can refresh the lightning
		g.clear();

		// render everything in the middle of the image
		g.translate(bolt.getWidth() / 2f, 0);

		// ensure smoothing is on
		g.setAntiAlias(true);

		// render each branch extending from the main arm
		float gT = 3f; // starting glow thickness
		float lT = 1.5f; // starting line thickness
		float a = 0.90f; // starting alpha
		for (int i = 0; i < children.size(); i++) {
			Branch b = children.get(i);

			// translate the branch to the point that we grabbed earlier and
			// rotate it around its origin
			g.translate(b.x, b.y);
			g.rotate(0, 0, b.angle);
			drawPath(g, b.p, lT, gT, a);
			g.rotate(0, 0, -b.angle);
			g.translate(-b.x, -b.y);

			lT = Math.max(1.2f, lT - 0.25f); // reduce thickness a bit per
												// branch
			a = Math.max(0.35f, a - 0.05f); // reduce opacity a bit per branch
		}

		// render the main branch with a nice thickness and full alpha
		drawPath(g, main, 1.8f, 4f, 1f);

		// reset the transform
		g.translate(-bolt.getWidth() / 2f, 0);
		g.flush();
	}

	// draws a path with a simple glow behind it
	/**
	 * Draw path.
	 * 
	 * @param g
	 *            the Graphics
	 * @param p
	 *            the path
	 * @param lineStrength
	 *            the line strength
	 * @param glowStrength
	 *            the glow strength
	 * @param alpha
	 *            the alpha
	 */
	private void drawPath(Graphics g, Path p, float lineStrength,
			float glowStrength, float alpha) {
		if (glowStrength > 0) {
			GLOW_COLOR.a = Math.max(0f, alpha - 0.35f);
			g.setColor(GLOW_COLOR);
			g.setLineWidth(glowStrength);
			g.draw(p);
		}
		LINE_COLOR.a = alpha;
		g.setColor(LINE_COLOR);
		g.setLineWidth(lineStrength);
		g.draw(p);
	}

	/**
	 * Creates a lightning-bolt-like path
	 * 
	 * @param height
	 *            the height
	 * @return the path
	 */
	Path createPath(float height) {
		Path p = new Path(0, 0);

		// how much bend to apply in the middle, and in which direction
		float drift = Math.max(0, rnd(BEND_MIN, BEND_MAX))
				* (RND.nextBoolean() ? -1 : 1);

		float x = 0, y = 0;
		// for each 'segment' (line)
		while (y < height) {
			// don't offset X initially, so that branches line up
			if (y > 1) {
				float mult = y >= (height / 2f) ? -1 : 1;
				x = x + mult * (drift + rnd(X_MIN, X_MAX)); // x offset for new
															// points
			}
			y += Math.max(0, rnd(Y_MIN, Y_MAX)); // Y offset for new points
			if (y >= height) {
				break; // height is reached; end the loop
			} else
				p.lineTo(x, y); // move along the path
		}
		return p;
	}

	/**
	 * Gets the number of thunder.
	 * 
	 * @return the number of thunder
	 */
	public int getNumberOfThunder() {
		return noOfThunder;
	}

	/**
	 * Sets the number of thunder.
	 * 
	 * @param numberOfThunder
	 *            the new number of thunder
	 */
	public void setNumberOfThunder(int numberOfThunder) {
		noOfThunder = numberOfThunder;
	}

	/**
	 * The Class Branch specifies how the branching should be developed
	 */
	private class Branch {
		private Path p;
		private float x, y, angle;

		/**
		 * Instantiates a new branch.
		 * 
		 * @param p
		 *            the p
		 * @param x
		 *            the x
		 * @param y
		 *            the y
		 */
		Branch(Path p, float x, float y) {
			this.p = p;
			this.x = x;
			this.y = y;
			this.angle = rnd(BRANCH_ROTATE_MIN, BRANCH_ROTATE_MAX);
		}
	}

}
