import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import java.util.*;

public class AngryBall extends BasicGame{
  
	//set the screen size for display
	static int screenHeight = 800;
	static int screenWidth = 600;
  
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
    float initX = screenWidth/2;
    float initY = 250;
    
    Rectangle enemyRectangle[][];
    Rectangle playerRectangle;
    
    
    //-----------    	
    float rotation=0f;
	float hip=0f;   
    float velocity = 0.1f;
    boolean isBallMoving = false;
    
    Badge badge;
    Ball powerBall;
    Block [][]block;
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
        
        createItem();        
        
    }
  
    public void createItem()
    {
        //creating objects
        try {
        	badge = new Badge();
            powerBall = new Ball();
            playerRectangle = new Rectangle(powerBall.getX(),powerBall.getY(),
            								powerBall.getBallImage().getWidth(),
            								powerBall.getBallImage().getHeight());
            
            block = new Block[row][column];
            enemyRectangle = new Rectangle[row][column];
             
            for (int i=0; i<row; i++)
            {
            	for(int j=0; j<column; j++)
            	{   	            	 
					block[i][j] = new Block();
					enemyRectangle[i][j] = new Rectangle(0,0,0,0);  //initilise it first with the dummy boundary
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
        
        if(input.isKeyDown(Input.KEY_A))
        {
            //plane.rotate(-0.2f * delta);
        }        
        
        if(isBallMoving)
        { 
        	//set the boundary for a balls, so it has to bound back
        	if (x >= 0 && x < (screenWidth - powerBall.getBallImage().getWidth() ) )
        	{
		        //calculate the drawing position for the ball
		        hip = 0.1f * delta;
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
        
        playerRectangle.setBounds(x,y, powerBall.getBallImage().getWidth(), powerBall.getBallImage().getHeight());        

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
  
    public boolean intersectBallBlock(Rectangle player, Rectangle enemy)
    {
        if(player.intersects(enemy))
        {
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
        badge.addStar(screenWidth-badge.getBadgeImage().getWidth(),0,1);
        
        showBlock();

        //display the score
        g.drawString("Score: ", 0, 0 );
        
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
	    		enemyRectangle[i][j].setBounds(blockX,blockY,
											blockWidth,
											blockHeight);
				blockX = blockX + blockWidth;				
	    	}
    		blockX = 0;
        	blockY = blockY - blockHeight;	    	
    	}    	
    }
    
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
				if(intersectBallBlock(playerRectangle, enemyRectangle[i][j]))
	        	{
					isBallMoving  = false;
					powerBall.randomBallMode();
					//blockMode = 0; //0 means empty

					if(blockMode == 1) 						
						block[i][j].setBlockMode(--blockMode);
					else if(blockMode == 6)  //convert from solid ice to half solid						
						block[i][j].setBlockMode(--blockMode);
					else if(blockMode == 5)
					{
						block[i][j].setBlockMode(0); //the block is gone when it is 0					
					}						
					
					//allows the penetration, block is destroyed
					if(blockMode == 0 )					
						enemyRectangle[i][j].setBounds(0,0,0,0);	//remove the boundary, so collision wont happen
		    		
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
}