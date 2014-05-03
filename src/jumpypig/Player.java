package jumpypig;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Player {
	
	public final int JUMPING_STATE = 1;
	public final int STANDING_STATE = 0;
	
	private final float GRAVITY = 0.05f;
	
	private int STATE;
	private Animation currentAnimation,jumpAnimation,standingAnimation;
	private int posX, posY;
	private float dy, dx;
	
	/**
	 * Create new pig player at (x,y)
	 * @param x
	 * @param y
	 */
	public Player(int x, int y) {
		//Set coords
		posX = x;
		posY = y;
		
		dy = 0;
		dx = 0;
		
		//Set state
		STATE = JUMPING_STATE;
		
		//Init. animations
		initAnimations();
	}
	
	/**
	 * Initiate animations.
	 */
	private void initAnimations() {
		//Jump Animation
		jumpAnimation = new Animation(new String[]{"/Sprites/pig_jumping_1.png",
				"/Sprites/pig_jumping_2.png",
				"/Sprites/pig_jumping_3.png",
				"/Sprites/pig_jumping_4.png"}, 30);
		
		//Standing Animation
		standingAnimation = new Animation(new String[]{"/Sprites/pig_standing_1.png",
				"/Sprites/pig_standing_2.png",
				"/Sprites/pig_standing_3.png",
				"/Sprites/pig_standing_4.png",
				"/Sprites/pig_standing_5.png",
				"/Sprites/pig_standing_6.png",
				"/Sprites/pig_standing_7.png",
				"/Sprites/pig_standing_8.png"}, 70);
		
		
		//Set current animation
		currentAnimation = standingAnimation;
		
	}
	
	/**
	 * Set X coord
	 * @param x
	 */
	public void setX(int x) {
		posX = x;
	}
	
	/**
	 * Set Y coord
	 * @param y
	 */
	public void setY(int y) {
		posY = y;
	}
	
	/**
	 * Get height of current sprite
	 * @return
	 */
	public int getHeight() {
		return currentAnimation.getSprite().getHeight(null);
	}
	
	/**
	 * Get width of current sprite
	 * @return
	 */
	public int getWidth() {
		return currentAnimation.getSprite().getWidth(null);
	}
	
	public void paint(Graphics2D g) {
		g.drawImage(currentAnimation.getSprite(),posX,posY,null);
	}

	public void update(GameView gameView) {
		
		//ANIMATION
		//If standing animation - reset when finished
		if(currentAnimation == standingAnimation && !currentAnimation.isPlaying()) {
			currentAnimation.reset();
		}
		
		//Update animation
		currentAnimation.update();
		//END ANIMATION
		
		
		//POSITION
		//if(STATE == JUMPING_STATE) {
			dy -= GRAVITY;
		//}
		
		posY -= dy;
		posX += dx;
		//END POSITION
	}
	
	public Rectangle getRect() {
		return new Rectangle(posX, posY, currentAnimation.getSprite().getWidth(null), currentAnimation.getSprite().getHeight(null));
	}
	
	/**
	 * Make player jump
	 */
	public void jump() {
		if(STATE == STANDING_STATE) {
			dy = 10;
			STATE = JUMPING_STATE;

			//ANIMATION
			currentAnimation = jumpAnimation;
			currentAnimation.reset();
			
		}
	}
	
	/**
	 * Make player stand
	 */
	public void stand() {
		dy = 0;
		if(STATE == JUMPING_STATE) {
			STATE = STANDING_STATE;
			
			//ANIMATION
			currentAnimation = standingAnimation;
			currentAnimation.reset();
		}
	}
	
	/**
	 * Make player fall(jump stop)
	 */
	public void fall() {
		if(dy > 0) {
			dy /= 5;
		}
	}
	
	/**
	 * Get current state
	 * @return
	 */
	public int getState() {
		return STATE;
	}

}
