package jumpypig;

import java.awt.Rectangle;

/**
 * Platform that elevates
 */
public class PlatformElevator extends Platform {
	
	private static final int ELEVATINGSPEED = 1;
	private static final int BOUND = 20;

	private int high_y,low_y;
	private int currentDirection;
	private boolean elevating;
	
	public PlatformElevator(int x, int y, int length) {
		super(x, y, length);
		elevating = true;
		//Elevating direction
		currentDirection = 1;
		//Set bounds
		high_y = y - BOUND;
		low_y = y + BOUND;
	}
	
	/**
	 * Check if platform is colliding with rec
	 */
	@Override
	public boolean intersecting(Rectangle rec) {
		return rec.intersects(getCollideRect());
	}
	
	/**
	 * Move on x-axis but also elevate
	 */
	@Override
	public void moveX(float x) {
		super.moveX(x);

		//Change elevating direction if outside bounds
		if(super.getY() < high_y) {
			currentDirection = 1;
		}else if(super.getY() > low_y) {
			currentDirection = -1;
		}
		
		//Elevate
		if(elevating) {
			super.moveY(currentDirection * ELEVATINGSPEED);
		}
	}
	
	@Override
	public void collidePlayer(Player player) {
		super.collidePlayer(player);
		//Stop elevating when player stands on it
		elevating = false;
	}
	
	private Rectangle getCollideRect() {
		int margin = 20;
		int width = super.getWidth() + margin;
		int height = 30;
		return new Rectangle(super.getX() - margin/2 + SpriteManager.getInstance().IMAGE_PLATFORM_LEFT.getWidth(null),
				super.getY(), width, height);
	}
	

	
	
}
