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

public class AngryBall extends BasicGame implements KeyListener{
  
	//set the screen size for display
	static int screenHeight = 800;
	static int screenWidth = 600;
	boolean hasSetPos = false;

    Image background = null;
       
    float x = screenWidth/2;
    float y = 250;    
    float scale = 1;

    float blockX =  0;
    float blockY = 0;    
    float blockScale = 1;

    //set the block rows and columns
    final int row=5;
    final int column=6;
    int maxNoBlock=row * column;
    int blockMode;
    int gemMode;
    float initX = screenWidth/2;
    float initY = 250;
    
    Rectangle blockRectangle[][];
    Rectangle powerBallRectangle;
    Rectangle gemRectangle[][];
    
    //score calculation
    int totalScore=0;
    int hitBlockScore = 10;
    int hitGemScore = 10;
    int noGemHit = 0;
    int level=1;
    int starMode=1;
    
    //-----------    	
    float rotation=0f;
	float hip=0f;   
    float velocity = 0.1f;
    boolean isBallMoving = false;
    
    Score score;
    Badge badge;
    Ball powerBall;
    Block[][] block;
    Gem[][] gem;
    Thunder thunder;
    
	int maxGem=5;
	int posX=0;
	int posY=0;	
	int[][] gemPos;
	int noOfThunder;
	boolean canCreateThunder=false;
	
	Music backgroundMusic;
	Music shootBallMusic;
	Music collideBlockMusic;
	Music collideGemMusic;
	
    //Rectangle enemy = new Rectangle(e.X,e.Y,e.Width,e.Height);
      
    public AngryBall()
    {
        super("Angry Balls");
    }    
    
    @Override
    public void init(GameContainer gc)
            throws SlickException {
    	
    	gc.setShowFPS(false); 
    	
        background = new Image("images/gokung.png");
        
        			//plane = new Image("images/plane.png");
        			//planeRectangle = new Rectangle(100,300,plane.getWidth(),plane.getHeight());
    	score = new Score();        
        createItem();        
        
        //play music
        backgroundMusic = new Music("/music/dgtheme.wav");
        backgroundMusic.play();
        backgroundMusic.loop();
        
    	shootBallMusic = new Music("music/gunfire.wav");
    	collideGemMusic = new Music("music/gemCollected.wav");

    }
  
    public void createItem()
    {
        //creating objects
        try 
        {
        	badge = new Badge();
            powerBall = new Ball();
            powerBallRectangle = new Rectangle(powerBall.getX(),powerBall.getY(),
            								powerBall.getBallImage().getWidth(),
            								powerBall.getBallImage().getHeight());
            
            block = new Block[row][column];
            gem = new Gem[row][column];
            blockRectangle = new Rectangle[row][column];
            gemRectangle = new Rectangle[row][column];
            thunder = new Thunder();
            
            //get the number of thunder (amulet)
            noOfThunder = thunder.getNumberOfThunder();
            
            for (int i=0; i<row; i++)
            {
            	for(int j=0; j<column; j++)
            	{   	            	 
					block[i][j] = new Block();
					gem[i][j] = new Gem();
					
					blockRectangle[i][j] = new Rectangle(0,-100,0,0);  //initilise it first with the dummy boundary
					gemRectangle[i][j] = new Rectangle(0,-100,0,0);  //initilise it first with the dummy boundary
            	}
            }
            
            createBoundary();
            setBlockMode();
            
            
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    @Override
    public void update(GameContainer gc, int delta)
            throws SlickException
    {
        Input input = gc.getInput();
  
  
        if(input.isKeyDown(Input.KEY_ENTER))
        {
        	isBallMoving = true;
        	shootBallMusic.play();
        	
        	/*
            float hip = 0.4f * delta;    
  
            float rotation = ball.getRotation();
  
            x-= hip * Math.sin(Math.toRadians(rotation));
            y-= hip * Math.cos(Math.toRadians(rotation));
           */
        }
        
        if(input.isKeyDown(Input.KEY_LEFT))
        {        	
            //scale += (scale >= 5.0f) ? 0 : 0.1f;
            //ball.setCenterOfRotation(ball.getWidth()/2.0f*scale, ball.getHeight()/2.0f*scale);
        	   
        	hip = +0.1f * delta;
        	//rotation += hip; 
        	powerBall.setRotation(hip);        	

        	//System.out.println(hip + " delta:" + delta + " x: " + x + "  y : " + y);
	        
        }

        if(input.isKeyDown(Input.KEY_RIGHT))
        {        	
            //scale += (scale >= 5.0f) ? 0 : 0.1f;
            //ball.setCenterOfRotation(ball.getWidth()/2.0f*scale, ball.getHeight()/2.0f*scale);
        	
        	//rotation = powerBall.getRotation();
        	hip = -0.1f * delta;
        	powerBall.setRotation(hip);
        	
        	//System.out.println(hip + " delta:" + delta  + "rotation: " + powerBall.getRotation() );
        }
        
        if(input.isKeyDown(Input.KEY_T))
        {
        	noOfThunder = thunder.getNumberOfThunder();
        	if(noOfThunder>0)
        		canCreateThunder = true;
            //new Thunder();
        }                            

        if(isBallMoving)
        { 
        	//set the boundary for a balls, so it has to bound back
        	if (x >= 0 && x < (screenWidth - powerBall.getBallImage().getWidth() ) )
        	{
        		
		        //calculate the drawing position for the ball
		        hip = 0.25f * delta; //set the velocity
		        rotation = powerBall.getRotation();
		        x-= hip * Math.sin(Math.toRadians(-1 * rotation));
		        y-= hip * Math.cos(Math.toRadians(-1 * rotation));
		        
		        //System.out.println("x : " + x + " y: " + y + "rotation : " + rotation);
        	}        	
	        //when the image touches the edge of the screen
        	else if( x  < 0 )
	        {	    
	        	rotation = powerBall.getRotation();
	        	System.out.println(rotation);
	        	hip = 0.1f * delta;
	        	//rotation =  -45;
	        	float minAngle = 30f;
	        	float maxAngle = 60f;
	        	
	        	//this is the level of difficulty
	        	//different boundary will give a different angle
	        	//we use -1 so it will let the ball to face at another side
	        	rotation = (float) (minAngle + (Math.random() * (maxAngle - minAngle))) * -1; 
	        	powerBall.setRotation(rotation);
		        x-= Math.sin(Math.toRadians(rotation));
		        y-= Math.cos(Math.toRadians(rotation));	        	
	        	//isBallMoving = false;
	        }        	
        	else if( x > (screenWidth - powerBall.getBallImage().getWidth()) )
	        {	    
	        	rotation = powerBall.getRotation();
	        	System.out.println(rotation);
	        	//hip = 0.1f * delta;
	        	//rotation =  -45;
	        	float minAngle = 30f;
	        	float maxAngle = 60f;
	        	
	        	//this is the level of difficulty
	        	//different boundary will give a different angle
	        	//we use -1 so it will let the ball to face at another side
	        	rotation = (float) (minAngle + (Math.random() * (maxAngle - minAngle)) );
	        	powerBall.setRotation(rotation);	        	
	        	
		        x-= Math.sin(Math.toRadians(rotation));
		        y-= Math.cos(Math.toRadians(rotation));	        	
	        	//isBallMoving = false;
	        	rotation = powerBall.getRotation();
	        	System.out.println(rotation);	        
	        }
	        	
	        if(y  >= screenHeight || y < 0)
	        {	        	
	        	isBallMoving = false;
	        	x= initX;
				y= initY;
	        }
        }
                
        
        //if(playerRectangle.intersects(planeRectangle))
        	//isBallMoving = false;
        
        powerBallRectangle.setBounds(x,y, powerBall.getBallImage().getWidth(), powerBall.getBallImage().getHeight());        

        /*
        for(int i=0;i<maxNoBlock;i++)
        {
        	if(intersectBallBlock(playerRectangle, enemyRectangle[i]))
        	{
        		int xPos=0;
        		int yPos=0;
        		
        		xPos = i * block[i].getBlockImage().getWidth();
        		yPos = block[i].getY();
        				
        				System.out.println(block[i].getX() + " : " + block[i].getY() + " i " + i);
        		block[i].destroy(xPos, yPos);
        		isBallMoving = false;
        	}
        }
        */
    }
  
    public boolean intersectBallBlock(Rectangle powerBall, Rectangle block)
    {
        if(powerBall.intersects(block))
        {
        	return true;
        }
        
    	return false;
    }

    public boolean intersectBallGem(Rectangle powerBall, Rectangle gem)
    {
        if(powerBall.intersects(gem))        	
        {
        	collideGemMusic.play();
        	return true;
        }
        
    	return false;
    }

    public void render(GameContainer gc, Graphics g)
            throws SlickException
    {
    	
    	//render all the objects
        background.draw(0, 0); //draw background
        powerBall.drop(x,y,scale);
        starMode = badge.getStarMode();
        badge.addStar(screenWidth-badge.getBadgeImage().getWidth(),0,starMode);
        
        showBlock();
        showGem();

        totalScore = score.getScore();
        if(totalScore >= 0 && totalScore <100 )
        {
        	badge.setStarMode(1);
        }
        else if(totalScore >= 100 && totalScore <200 )
        {
        	badge.setStarMode(2);
        }
        else if(totalScore >= 300 && totalScore <400 )
        {
        	badge.setStarMode(3);
        }
        else if(totalScore >= 400 && totalScore <500 )
        {
        	badge.setStarMode(4);
        }
        else if(totalScore >= 500 && totalScore <600 )
        {
        	badge.setStarMode(5);
        }
        else if(totalScore >= 600 && totalScore <700 )
        {
        	badge.setStarMode(6);
        }
        else if(totalScore >= 700)
        {
        	badge.setStarMode(7);        	
        }
        	
        //display the score
        g.drawString("Score: " + totalScore, 0, 0 );
        g.drawString("Level: " + level, 100, 0 );
        g.drawString("Gem Collected: " + noGemHit, 200, 0 );
        g.drawString("Thunder: " + noOfThunder, 0, 30);
        
        if(canCreateThunder)
        {
        	canCreateThunder = false;

        	thunder.generateThunder();
        }
        
		//plane.draw(100,300);
        //block[10].destroy(500,485);
		//isBallMoving = false;

    }
  
    public static void main(String[] args)
            throws SlickException
    {
         AppGameContainer app =
            new AppGameContainer( new AngryBall() );
  
         app.setDisplayMode(screenWidth, screenHeight, false);
         app.start();
    }
    
    public void createBoundary()
    {
    	float blockWidth = 100f; 
    	float blockHeight = 35f;
    	
    	blockX = 0;
    	blockY = screenHeight - blockHeight;

    	for(int i=0; i<row; i++)
    	{	    	
    		for(int j=0; j<column; j++)
	    	{					
	    		//create a boundary
	    	    //purpose can be later used for collision detection
	    		blockRectangle[i][j].setBounds(blockX,blockY,
											blockWidth,
											blockHeight);
				blockX = blockX + blockWidth;				
	    	}
    		blockX = 0;
        	blockY = blockY - blockHeight;	    	
    	}    	
    }
    
    
    //set the type of block
    public void setBlockMode()
    {    	
    	for(int i=0; i<row; i++)
    	{	    	
    		for(int j=0; j<column; j++)
	    	{	    	    	
    			int k = (int)Math.round(Math.random()); 
    	    	if(k == 0)
    	    	{
	    	    	//make the block as wood
	    	    	blockMode = 1;
	    	    	block[i][j].setBlockMode(blockMode);
    	    	}    	    
    	    	else if(k == 1)
    	    	{
	    	    	//make the block as full ice
	    	    	blockMode = 6;
	    	    	block[i][j].setBlockMode(blockMode);
    	    	}
	    	}
    	}
    }
    
    public void showGem()
    {    	
    	if(!hasSetPos)
    	{
    		gemPos = new int [row][column];
	    	for(int i=0; i<maxGem; i++)
	    	{
	    		posX = (int)Math.round(Math.random() * (row-1));
	    		posY = (int)Math.round(Math.random() * (column-1));
	
	    		//set for different gems
    			int k = 1 + (int)(Math.round(Math.random() * 4)); 
	    	    gemMode = k;
	    	    gem[posX][posY].setGemMode(gemMode);

	    		//set the gem that displays on the screen
	    		gemPos[posX][posY] = 1;
	    	}    	
	    	hasSetPos = true;
    	}
    	
    	//blocks and gems has the same size
    	float blockWidth = 100f; 
    	float blockHeight = 35f;
    	
    	blockX = 0;
    	blockY = screenHeight - blockHeight;

        //check for the gems that meant to be draws on the screen
    	//gem[0][0].addGem(0, 0, 1);
        for(int i=0; i<row; i++)
        {
        	for(int j=0; j<column; j++)
        	{
        		if(gemPos[i][j] == 1)
        		{ 
        			gemMode = gem[i][j].getGemMode();
    	    		gem[i][j].addGem(blockX,blockY,gemMode);
    	    		//it is not disappeared yet
    	    		if(gemMode != 0) 
    	    			gemRectangle[i][j].setBounds(blockX,blockY,blockWidth,blockHeight);
    	    	
    	    		block[i][j].setBlockMode(0);  //remove the block because we know that gem takes place
    	    		blockRectangle[i][j].setBounds(0,-100,0,0);
        		}
        		
        		blockX = blockX + blockWidth;
        	}
        	
        	blockX = 0;
        	blockY = blockY - blockHeight;   
        }
    }
    
    public void showBlock()
    {
    	float blockWidth = 100f; 
    	float blockHeight = 35f;
    	
    	blockX = 0;
    	blockY = screenHeight - blockHeight;
    	
/*    	blockX = 0;
    	blockY = blockY - blockHeight;	    	
*/
    	for(int i=0; i<row; i++)
    	{	    	
    		for(int j=0; j<column; j++)
	    	{	    	    	

	    		blockMode = block[i][j].getBlockMode();
	    		//System.out.println("block X " + blockX + " block Y: " + blockY); 
	    		
	    		//laying down blocks
	    		block[i][j].addBlock(blockX,blockY,blockMode);
System.out.println("block location: " + blockX + " " + blockY);    	    	

				blockX = blockX + blockWidth;
				
				//check for collision
				if(intersectBallBlock(powerBallRectangle, blockRectangle[i][j]))
	        	{										
					isBallMoving  = false;
					powerBall.randomBallMode();
					//blockMode = 0; //0 means empty
					totalScore = score.getScore();
					
					if(blockMode == 1)
					{
						totalScore += hitBlockScore;
						score.setScore(totalScore);
						block[i][j].setBlockMode(0);
						blockRectangle[i][j].setBounds(0,-100,0,0);	//remove the boundary, so collision wont happen
					}					
					else if(blockMode == 6)  //convert from solid ice to half solid
					{
						totalScore += hitBlockScore;
						score.setScore(totalScore);
						block[i][j].setBlockMode(5);
					}					
					else if(blockMode == 5)
					{
						totalScore += hitBlockScore;
						score.setScore(totalScore);
						block[i][j].setBlockMode(0); //the block is gone when it is 0					
						blockRectangle[i][j].setBounds(0,-100,0,0);	//remove the boundary, so collision wont happen
					}						
					
					
/*					//allows the penetration, block is destroyed
					if(blockMode == 0 )
					{
						blockRectangle[i][j].setBounds(0,0,0,0);	//remove the boundary, so collision wont happen
					}
*/		    		
					//reset the powerball position
					x= initX;
					y= initY;
					powerBall.drop(x,y,scale);
	        	}
				
				if(intersectBallGem(powerBallRectangle, gemRectangle[i][j]))
	        	{			
					isBallMoving = false;
					gemMode = gem[i][j].getGemMode();
					hitGemScore = hitGemScore * gemMode;
					totalScore = score.getScore();
					totalScore += hitBlockScore;
					score.setScore(totalScore);

					gem[i][j].setGemMode(0); //the gem is gone when it is 0 
					gemRectangle[i][j].setBounds(0,-100,0,0);	//remove the boundary, so collision wont happen
					noGemHit++;
					
					//level is finished, we make it 3 hits for each level
					if(noGemHit >= 3)
					{							
						//reset and move to the next level
						createItem();
						level++;
						noGemHit = 0;
					}
					
					//reset the powerball position
					x= initX;
					y= initY;				
					powerBall.drop(x,y,scale);
	        	}

	    	}
    		blockX = 0;
        	blockY = blockY - blockHeight;        	
    	}
    	
    	//enemyRectangle[55] = new Rectangle(0,600, Block.getBlockImage().getWidth(), Block.getBlockImage().getHeight());    	 
    	
    }
    
    public void keyReleased(int key, char c) 
    {
    	//the thunder key is released
    	if( (key == Input.KEY_T)  )
    	{
        	noOfThunder = thunder.getNumberOfThunder();
        	if(noOfThunder > 0)
        	{
	        	thunder.setNumberOfThunder(--noOfThunder);
	        	noGemHit++;
        	}
    	}
    }

}