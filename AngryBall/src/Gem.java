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
	Image blockImg = null;
	Image blockSolidImg = null;
	Image emptyImg = null;
	int x=0;
	int y=0;
	int blockMode=1;
	
	public Gem() throws SlickException
	{
		blockSolidImg = new Image("images/gem1.jpg");
		emptyImg = new Image("images/blockempty.png");
		//blockImg = emptyImg;
	}		
	
	public void addBlock(float x, float y, int blockMode)
	{
				
		if( blockMode == 0 )
			blockImg=emptyImg;
		else
			blockImg=blockSolidImg;
		
		blockImg.draw(x,y);
		
		this.x = (int) x;
		this.y = (int) y;
	}

	public int getBlockMode()
	{
		return blockMode;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public Image getBlockImage()
	{
		return blockImg;
	}
	
	public int getImageWidth()
	{
		return blockImg.getWidth();
	}
	
	public int getImageHeight()
	{
		return blockImg.getHeight();
	}
	
	public void destroy(int blockMode)
	{
		this.blockMode = blockMode; 
	}
	

}
