
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import java.util.*;

public class Badge 
{
	float scale=1f;
	Image starImg = null;
	Image starOne = null;
	Image starTwo = null;
	Image starThree = null;
	Image starFour = null;
	Image starFive = null;
	Image starSix = null;
	Image starSeven = null;
	
	int x=0;
	int y=0;
	//blockMode 1 is for wood
	//          2 is for ice
	int starMode=1; 
	
	public Badge() throws SlickException
	{
		starOne = new Image("images/star1.png");
		starTwo = new Image("images/star2.png");
		starThree = new Image("images/star3.png");
		starFour = new Image("images/star4.png");
		starFive = new Image("images/star5.png");
		starSix = new Image("images/star6.png");
		starSeven = new Image("images/star7.png");
		
		starImg = starOne;
	}		
	
	public void setStarMode(int starMode)
	{
		this.starMode = starMode;
	}
	
	public void addStar(float x, float y, int starMode)
	{
				
		if( starMode == 1 )
			starImg=starOne;
		else if( starMode == 2)
			starImg=starTwo;
		else if( starMode == 3)
			starImg=starThree;
		else if( starMode == 4)
			starImg=starFour;
		else if( starMode == 5)
			starImg=starFive;
		else if( starMode == 6)
			starImg=starSix;
		else if( starMode == 7)
			starImg=starSeven;

		starImg.draw(x,y);
		
		this.x = (int) x;
		this.y = (int) y;
	}

	public int getStarMode()
	{
		return starMode;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public Image getBadgeImage()
	{
		return starImg;
	}
	
	public int getImageWidth()
	{
		return starImg.getWidth();
	}
	
	public int getImageHeight()
	{
		return starImg.getHeight();
	}
		
}
