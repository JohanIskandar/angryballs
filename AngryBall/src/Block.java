import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import java.util.*;

public class Block 
{
	float scale=1f;
	Image blockImg = null;
	Image blockSolidImg = null;
	Image iceSolidImg = null;
	Image iceHalfImg = null;
	Image emptyImg = null;
	int x=0;
	int y=0;
	//blockMode 1 is for wood
	//          2 is for ice
	int blockMode=0; 
	
	public Block() throws SlickException
	{
		blockSolidImg = new Image("images/woodblocksmall.jpg");
		iceSolidImg = new Image("images/iceblockfull.png");
		iceHalfImg = new Image("images/iceblockhalf.png");
		emptyImg = new Image("images/blockempty.png");
		//blockImg = emptyImg;
	}		
	
	public void setBlockMode(int blockMode)
	{
		this.blockMode = blockMode;
	}
	
	public void addBlock(float x, float y, int blockMode)
	{
				
		if( blockMode == 0 )
			blockImg=emptyImg;
		else if( blockMode == 1)
			blockImg=blockSolidImg;
		else if( blockMode == 5)
			blockImg=iceHalfImg;
		else if( blockMode == 6)
			blockImg=iceSolidImg;

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
