
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Ball extends Sprite
{

	float scale=1f;
	//float rotation=0f;  //face downward
	float hip=180f;
	int x;
	int y;
	int ballMode = 0;
	int ballType = 0;
	
	Image ball = null;
	Image ball1 = null;
	Image ball2= null;
	
	public Ball() throws SlickException
	{
		ball1 = new Image("images/yellowball1.png");
		ball2 = new Image("images/yellowball2.png");
		
		randomBallMode();
		ball.rotate(hip);
	}
	
	public void randomBallMode()
	{
		ballType = 1 + (int)Math.round(Math.random()) * 1;
		if(ballType == 1)
			ball = ball1;
		else if(ballType ==2)
			ball = ball2;				
	}
	
	public void getBallMode(int ballMode)
	{
		this.ballMode = ballMode;
	}
	
	public void drop(float x, float y, float scale)
	{				
		ball.draw(x,y,scale);
		this.x = (int) x;
		this.y = (int) y;
	}

	public void setRotation(float hip)
	{				
		this.hip = hip;
		ball.rotate(hip);
	}
	
	public float getRotation() {
		return ball.getRotation();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public Image getBallImage()
	{
		return ball;
	}
	
}


