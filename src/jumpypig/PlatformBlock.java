package jumpypig;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class PlatformBlock extends Platform {

	public PlatformBlock(int x, int y, int length) {
		super(x, y, length);
	}
	
	@Override
	public boolean isVisible() {
		if(super.getX() + getWidth() < 0) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean intersecting(Rectangle rec) {
		return rec.intersects(getRectangle());
	}
	
	@Override
	public void collidePlayer(Player player) {
		
		if(isStanding(player.getJumpRect())) {
			
			super.collidePlayer(player);
			
		}else if(isColliding(player.getRect())) {
			//Set pos
			player.setX(super.getX() - player.getWidth());
		}
		
	}
	
	@Override
	public void paint(Graphics2D g) {
		g.drawImage(SpriteManager.getInstance().IMAGE_PLATFORMBLOCK, super.getX(), super.getY(), null);
		
		/*DRAW HIT BOXES
		g.setPaint(Color.BLUE);
		g.fill(getStandingRect());
		g.setPaint(Color.red);
		g.fill(getCollideRect());
		*/
	}
	
	/**
	 * Check if rec is standing on block
	 * @param rec
	 * @return
	 */
	public boolean isStanding(Rectangle rec) {
		return rec.intersects(getStandingRect());
	}
	
	/**
	 * Check if rec is colliding with block
	 * @param rec
	 * @return
	 */
	public boolean isColliding(Rectangle rec) {
		return rec.intersects(getCollideRect());
	}
	
	private Rectangle getRectangle() {
		return new Rectangle(super.getX(), super.getY(), getWidth(), getHeight());
	}
	
	private Rectangle getCollideRect() {
		return new Rectangle(super.getX(), super.getY()+10, getWidth(), getHeight()-10);
	}
	
	private Rectangle getStandingRect() {
		return new Rectangle(super.getX()-5, super.getY(), getWidth()+10, 10);
	}
	
	@Override
	public int getHeight() {
		return SpriteManager.getInstance().IMAGE_PLATFORMBLOCK.getHeight(null);
	}
	
	@Override
	public int getWidth() {
		return SpriteManager.getInstance().IMAGE_PLATFORMBLOCK.getWidth(null);
	}

}
