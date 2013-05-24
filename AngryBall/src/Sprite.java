
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public abstract class Sprite 
{
		
	public Sprite() throws SlickException
	{
	}
	
	public abstract void drop(float x, float y, float scale);

	public abstract void setRotation(float hip);
	
	public abstract float getRotation();

	public abstract int getX();

	public abstract int getY();	
		
}
