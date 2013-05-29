import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.KeyListener;

/**
 * The Class AngryBall will display the main play screen
 */
public class AngryBall extends BasicGame implements KeyListener {

	// set the screen size for display
	static int screenHeight = 800;
	static int screenWidth = 600;
	boolean hasSetPos = false;

	Image background = null;

	float x = screenWidth / 2;
	float y = 250;
	float scale = 1;

	float blockX = 0;
	float blockY = 0;
	float blockScale = 1;

	// set the block rows and columns
	final int row = 5;
	final int column = 6;
	int maxNoBlock = row * column;
	int blockMode;
	int gemMode;
	float initX = screenWidth / 2;
	float initY = 250;

	Rectangle blockRectangle[][];
	Rectangle powerBallRectangle;
	Rectangle gemRectangle[][];

	// score calculation
	int totalScore = 0;
	int hitBlockScore = 10;
	int hitGemScore = 10;
	int noGemHit = 0;
	int level = 1;
	int starMode = 1;

	// -----------
	float rotation = 0f;
	float hip = 0f;
	float velocity = 0.1f;
	boolean isBallMoving = false;

	Score score;
	Badge badge;
	Ball powerBall;
	Block[][] block;
	Gem[][] gem;
	Thunder thunder;

	int maxGem = 5;
	int posX = 0;
	int posY = 0;
	int[][] gemPos;
	int noOfThunder;
	boolean canCreateThunder = false;

	Music backgroundMusic;
	Music shootBallMusic;
	Music collideBlockMusic;
	Music collideGemMusic;

	/**
	 * Instantiates a new angry ball.
	 */
	public AngryBall() {
		super("Angry Balls");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
	 */
	@Override
	public void init(GameContainer gc) throws SlickException {

		gc.setShowFPS(false);

		background = new Image("images/gokung.png");

		score = new Score();
		createItem();

		// play music
		backgroundMusic = new Music("/music/dgtheme.wav");
		backgroundMusic.play();
		backgroundMusic.loop();

		// shootBallMusic = new Music("music/gunfire.wav");
		// collideGemMusic = new Music("music/gemCollected.wav");
	}

	/**
	 * Creates the item.
	 */
	public void createItem() {
		// creating objects
		try {
			badge = new Badge();
			powerBall = new Ball();
			powerBallRectangle = new Rectangle(powerBall.getX(),
					powerBall.getY(), powerBall.getBallImage().getWidth(),
					powerBall.getBallImage().getHeight());

			block = new Block[row][column];
			gem = new Gem[row][column];
			blockRectangle = new Rectangle[row][column];
			gemRectangle = new Rectangle[row][column];
			thunder = new Thunder();

			// get the number of thunder (amulet)
			noOfThunder = thunder.getNumberOfThunder();

			for (int i = 0; i < row; i++) {
				for (int j = 0; j < column; j++) {
					block[i][j] = new Block();
					gem[i][j] = new Gem();
					//initialise the boundary so collision
					blockRectangle[i][j] = new Rectangle(0, -100, 0, 0); 					
					gemRectangle[i][j] = new Rectangle(0, -100, 0, 0); 
				}
			}

			createBoundary();
			setBlockMode();

		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer,
	 * int)
	 */
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();

		// check for the input key
		if (input.isKeyDown(Input.KEY_ENTER)) {
			isBallMoving = true;
			// shootBallMusic.play();

		}

		if (input.isKeyDown(Input.KEY_LEFT)) {
			hip = +0.1f * delta;
			powerBall.setRotation(hip);
		}

		if (input.isKeyDown(Input.KEY_RIGHT)) {
			hip = -0.1f * delta;
			powerBall.setRotation(hip);
		}

		if (input.isKeyDown(Input.KEY_T)) {
			noOfThunder = thunder.getNumberOfThunder();
			if (noOfThunder > 0)
				canCreateThunder = true;
		}

		if (isBallMoving) {
			// set the boundary for a balls, so it has to bound back
			if (x >= 0
					&& x < (screenWidth - powerBall.getBallImage().getWidth())) {

				// calculate the drawing position for the ball
				hip = 0.25f * delta; // set the velocity
				rotation = powerBall.getRotation();
				x -= hip * Math.sin(Math.toRadians(-1 * rotation));
				y -= hip * Math.cos(Math.toRadians(-1 * rotation));
			}
			// when the image touches the edge of the screen
			else if (x < 0) {
				rotation = powerBall.getRotation();
				hip = 0.1f * delta;
				float minAngle = 30f;
				float maxAngle = 60f;

				// this is the level of difficulty
				// different boundary will give a different angle
				// we use -1 so it will let the ball to face at another side
				rotation = (float) (minAngle + (Math.random() * (maxAngle - minAngle)))
						* -1;
				powerBall.setRotation(rotation);
				x -= Math.sin(Math.toRadians(rotation));
				y -= Math.cos(Math.toRadians(rotation));
			} else if (x > (screenWidth - powerBall.getBallImage().getWidth())) {
				rotation = powerBall.getRotation();
				System.out.println(rotation);
				float minAngle = 30f;
				float maxAngle = 60f;

				// this is the level of difficulty
				// different boundary will give a different angle
				// we use -1 so it will let the ball to face at another side
				rotation = (float) (minAngle + (Math.random() * (maxAngle - minAngle)));
				powerBall.setRotation(rotation);

				x -= Math.sin(Math.toRadians(rotation));
				y -= Math.cos(Math.toRadians(rotation));
				// isBallMoving = false;
				rotation = powerBall.getRotation();
				System.out.println(rotation);
			}

			if (y >= screenHeight || y < 0) // when ball on the bottom of the
											// screen
			{
				isBallMoving = false;
				x = initX;
				y = initY;
			}
		}

		// set the rectangle for powerBall to create an boundary thus can be
		// used
		// for collision detection
		powerBallRectangle.setBounds(x, y, powerBall.getBallImage().getWidth(),
				powerBall.getBallImage().getHeight());
	}

	/**
	 * Intersect ball and block.
	 * 
	 * @param powerBall
	 *            the power ball
	 * @param block
	 *            the block
	 * @return true, if successful
	 */
	public boolean intersectBallBlock(Rectangle powerBall, Rectangle block) {
		if (powerBall.intersects(block)) {
			return true;
		}

		return false;
	}

	/**
	 * Intersect ball and gem.
	 * 
	 * @param powerBall
	 *            the power ball
	 * @param gem
	 *            the gem
	 * @return true, if successful
	 */
	public boolean intersectBallGem(Rectangle powerBall, Rectangle gem) {
		if (powerBall.intersects(gem)) {
			// collideGemMusic.play();
			return true;
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.newdawn.slick.Game#render(org.newdawn.slick.GameContainer,
	 * org.newdawn.slick.Graphics)
	 */
	public void render(GameContainer gc, Graphics g) throws SlickException {

		// render all the objects
		background.draw(0, 0); // draw background
		powerBall.drop(x, y, scale);
		starMode = badge.getStarMode();
		badge.addStar(screenWidth - badge.getBadgeImage().getWidth(), 0,
				starMode);

		// show the blocks and gems
		showBlock();
		showGem();

		// badges changed depend on the score obtained
		totalScore = score.getScore();
		if (totalScore >= 0 && totalScore < 100) {
			badge.setStarMode(1);
		} else if (totalScore >= 100 && totalScore < 200) {
			badge.setStarMode(2);
		} else if (totalScore >= 300 && totalScore < 400) {
			badge.setStarMode(3);
		} else if (totalScore >= 400 && totalScore < 500) {
			badge.setStarMode(4);
		} else if (totalScore >= 500 && totalScore < 600) {
			badge.setStarMode(5);
		} else if (totalScore >= 600 && totalScore < 700) {
			badge.setStarMode(6);
		} else if (totalScore >= 700) {
			badge.setStarMode(7);
		}

		// display the score
		g.drawString("Score: " + totalScore, 0, 0);
		g.drawString("Level: " + level, 100, 0);
		g.drawString("Gem Collected: " + noGemHit, 200, 0);
		g.drawString("Thunder: " + noOfThunder, 0, 30);

		if (canCreateThunder) {
			canCreateThunder = false;

			thunder.generateThunder();
		}
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws SlickException
	 *             the slick exception
	 */
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new AngryBall());

		app.setDisplayMode(screenWidth, screenHeight, false);
		app.start();
	}

	/**
	 * Creates the boundary.
	 */
	public void createBoundary() {
		float blockWidth = 100f;
		float blockHeight = 35f;

		blockX = 0;
		blockY = screenHeight - blockHeight;

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				// create a boundary
				// purpose can be later used for collision detection
				blockRectangle[i][j].setBounds(blockX, blockY, blockWidth,
						blockHeight);
				blockX = blockX + blockWidth;
			}
			blockX = 0;
			blockY = blockY - blockHeight;
		}
	}

	/**
	 * Sets the block mode.
	 */
	public void setBlockMode() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				int k = (int) Math.round(Math.random());
				if (k == 0) {
					// make the block as wood
					blockMode = 1;
					block[i][j].setBlockMode(blockMode);
				} else if (k == 1) {
					// make the block as full ice
					blockMode = 6;
					block[i][j].setBlockMode(blockMode);
				}
			}
		}
	}

	/**
	 * Show gem.
	 */
	public void showGem() {
		if (!hasSetPos) {
			gemPos = new int[row][column];
			for (int i = 0; i < maxGem; i++) {
				posX = (int) Math.round(Math.random() * (row - 1));
				posY = (int) Math.round(Math.random() * (column - 1));

				// set for different gems
				int k = 1 + (int) (Math.round(Math.random() * 4));
				gemMode = k;
				gem[posX][posY].setGemMode(gemMode);

				// set the gem that displays on the screen
				gemPos[posX][posY] = 1;
			}
			hasSetPos = true;
		}

		// blocks and gems has the same size
		float blockWidth = 100f;
		float blockHeight = 35f;

		blockX = 0;
		blockY = screenHeight - blockHeight;

		// check for the gems that meant to be draws on the screen
		// gem[0][0].addGem(0, 0, 1);
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				if (gemPos[i][j] == 1) {
					gemMode = gem[i][j].getGemMode();
					gem[i][j].addGem(blockX, blockY, gemMode);
					// it is not disappeared yet
					if (gemMode != 0)
						gemRectangle[i][j].setBounds(blockX, blockY,
								blockWidth, blockHeight);

					block[i][j].setBlockMode(0); // remove the block because we
													// know that gem takes place
					blockRectangle[i][j].setBounds(0, -100, 0, 0);
				}

				blockX = blockX + blockWidth;
			}

			blockX = 0;
			blockY = blockY - blockHeight;
		}
	}

	/**
	 * Show block.
	 */
	public void showBlock() {
		float blockWidth = 100f;
		float blockHeight = 35f;

		blockX = 0;
		blockY = screenHeight - blockHeight;

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {

				blockMode = block[i][j].getBlockMode();
				// System.out.println("block X " + blockX + " block Y: " +
				// blockY);

				// laying down blocks
				block[i][j].addBlock(blockX, blockY, blockMode);
				// System.out.println("block location: " + blockX + " " +
				// blockY);

				blockX = blockX + blockWidth;

				// check for collision
				if (intersectBallBlock(powerBallRectangle, blockRectangle[i][j])) {
					isBallMoving = false;
					powerBall.randomBallMode();
					// blockMode = 0; //0 means empty
					totalScore = score.getScore();

					if (blockMode == 1) {
						totalScore += hitBlockScore;
						score.setScore(totalScore);
						block[i][j].setBlockMode(0);
						//remove the boundary so collision is disable
						blockRectangle[i][j].setBounds(0, -100, 0, 0); 
					} else if (blockMode == 6) // convert from solid ice to half
												// solid
					{
						totalScore += hitBlockScore;
						score.setScore(totalScore);
						block[i][j].setBlockMode(5);
					} else if (blockMode == 5) {
						totalScore += hitBlockScore;
						score.setScore(totalScore);
						block[i][j].setBlockMode(0); //disappear the block
						//remove the boundary so collision is disable
						blockRectangle[i][j].setBounds(0, -100, 0, 0); 						
					}

					// reset the powerball position
					x = initX;
					y = initY;
					powerBall.drop(x, y, scale);
				}

				// check for collision
				if (intersectBallGem(powerBallRectangle, gemRectangle[i][j])) {
					isBallMoving = false;
					gemMode = gem[i][j].getGemMode();
					hitGemScore = hitGemScore * gemMode;
					totalScore = score.getScore();
					totalScore += hitBlockScore;
					score.setScore(totalScore);

					gem[i][j].setGemMode(0); // the gem is gone when it is 0
					//remove the boundary so collision is disable
					gemRectangle[i][j].setBounds(0, -100, 0, 0); 					
					noGemHit++;

					// level is finished, we make it 3 hits for each level
					if (noGemHit >= 3) {
						// reset and move to the next level
						createItem();
						level++;
						noGemHit = 0;
					}

					// reset the powerball position
					x = initX;
					y = initY;
					powerBall.drop(x, y, scale);
				}

			}
			blockX = 0;
			blockY = blockY - blockHeight;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.newdawn.slick.BasicGame#keyReleased(int, char)
	 */
	public void keyReleased(int key, char c) {
		// the thunder key is released
		if ((key == Input.KEY_T)) {
			noOfThunder = thunder.getNumberOfThunder();
			if (noOfThunder > 0) {
				thunder.setNumberOfThunder(--noOfThunder);
				noGemHit++;
			}
		}
	}

}