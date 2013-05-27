import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import java.util.*;

public class Gem
{
	float scale=1f;
	Image emptyImg = null;
	Image gemImg = null;
	Image gem1 = null;
	Image gem2 = null;
	Image gem3 = null;
	Image gem4 = null;
	Image gem5 = null;

	int x=0;
	int y=0;
	int gemMode=1;
	
	public Gem() throws SlickException
	{
		emptyImg = new Image("images/blockempty.png");
		gem1 = new Image("images/gem1.png");
		gem2 = new Image("images/gem2.png");
		gem3 = new Image("images/gem3.png");
		gem4 = new Image("images/gem4.png");
		gem5 = new Image("images/gem5.png");		
		emptyImg = new Image("images/blockempty.png");
		//set the default gem image to nothing
		gemImg = emptyImg;
	}		
	
	public void addGem(float x, float y, int gemMode)
	{
		if (gemMode == 0)
			gemImg = emptyImg;
		else if( gemMode == 1 )
			gemImg = gem1;
		else if( gemMode == 2 )
			gemImg = gem2;
		else if( gemMode == 3 )
			gemImg = gem3;
		else if( gemMode == 4 )
			gemImg = gem4;
		else if( gemMode == 5 )
			gemImg = gem5;
		
		gemImg.draw(x,y);
		
		this.x = (int) x;
		this.y = (int) y;
	}

	public void setGemMode(int gemMode)
	{
		this.gemMode = gemMode;
	}
	
	public int getGemMode()
	{
		return gemMode;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public Image getGemImage()
	{
		return gemImg;
	}
	
	public int getImageWidth()
	{
		return gemImg.getWidth();
	}
	
	public int getImageHeight()
	{
		return gemImg.getHeight();
	}
	
	public void destroy(int gemMode)
	{
		this.gemMode = gemMode; 
	}
	

}
