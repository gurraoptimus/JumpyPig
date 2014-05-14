package jumpypig;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Player {
	
	public static final int JUMPING_STATE = 1;
	public static final int STANDING_STATE = 0;
	public static final int FALLING_STATE = 2;
	
	private final float GRAVITY = 0.2f;
	
	private int STATE;
	private Animation currentAnimation,jumpAnimation,standingAnimation,explodeAnimation;
	private int posX, posY;
	private float dy, dx;
	private boolean exploded;
	
	/**
	 * Create new pig player at (x,y)
	 * @param x
	 * @param y
	 */
	public Player(int x, int y) {
		//Set coords
		posX = x;
		posY = y;
		
		dy = 0.0f;
		dx = 0.0f;
		
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
		
		//Explode Animation
		explodeAnimation = new Animation(new String[]{"/Sprites/blood_splat_1.png",
				"/Sprites/blood_splat_2.png",
				"/Sprites/blood_splat_3.png",
				"/Sprites/blood_splat_4.png",
				"/Sprites/blood_splat_5.png",
				"/Sprites/blood_splat_6.png",
				"/Sprites/blood_splat_7.png",
				"/Sprites/blood_splat_8.png",
				"/Sprites/blood_splat_9.png",
				"/Sprites/blood_splat_10.png",
				"/Sprites/blood_splat_11.png"}, 20);
		
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
		
		/*DRAW HITBOX
		g.setPaint(Color.cyan);
		g.fill(getJumpRect());
		*/
		
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
		dy -= GRAVITY;
		//Fix max gravity
		if( dy < -10 ) {
			dy = -10;
		}
		if(STATE != STANDING_STATE) {
			posY -= dy;
		}
		posX += dx;
		//END POSITION 
	}
	
	/**
	 * Get a rectangle representive of player
	 * @return
	 */
	public Rectangle getRect() {
		return new Rectangle(posX, posY, currentAnimation.getSprite().getWidth(null), currentAnimation.getSprite().getHeight(null));
	}
	
	/**
	 * Get a jump hitbox for player
	 * @return
	 */
	public Rectangle getJumpRect() {
		int margin = -12;
		int width = getWidth() + margin;
		int height = 1;
		return new Rectangle(posX-margin/2, posY+getHeight(), width, height);
	}
	
	/**
	 * Make player jump
	 */
	public void jump() {
		if(STATE == STANDING_STATE) {
			posY -= getJumpRect().height+1;
			dy = 15;
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
		if(STATE != STANDING_STATE) {
			//ANIMATION
			currentAnimation = standingAnimation;
			currentAnimation.reset();
		}
		STATE = STANDING_STATE;
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
	 * Make little piggy explode in blood
	 */
	public void explode() {
		STATE = FALLING_STATE;
		if(!exploded) {
			currentAnimation = explodeAnimation;
			currentAnimation.reset();
			
			//PLAY SPLAT SOUND
			SoundManager.getInstance().SPLAT_SOUND.play();
			
			exploded = true;
		}
	}
	
	/**
	 * Get current state
	 * @return
	 */
	public int getState() {
		return STATE;
	}
	
	/**
	 * Set current state
	 * @param state
	 */
	public void setState(int state) {
		STATE = state;
	}
	
	/**
	 * returns true if player is outside frame
	 */
	public boolean isDead() {
		//Check if is on screen
		if(posX + currentAnimation.getSprite().getWidth(null) < 0 || posY > GameFrame.SCREENSIZE.height + 100) {
			return true;
		}
		return false;
	}
	
	/**
	 * Return if player has exploded or not
	 * @return
	 */
	public boolean hasExploded() {
		return exploded;
	}
}
