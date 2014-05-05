package jumpypig;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class PlatformOfDeath extends Platform {

	public PlatformOfDeath(int x, int y, int length) {
		super(x, y, length);
	}
	
	/**
	 * Is platform on the screen
	 */
	@Override
	public boolean isVisible() {
		if(super.getX() + getWidth() < 0) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean intersecting(Rectangle rec) {
		return rec.intersects(getCollideRect());
	}
	
	@Override
	public void collidePlayer(Player player) {
		player.explode();
	}
	
	@Override
	public void paint(Graphics2D g) {
		g.drawImage(SpriteManager.getInstance().IMAGE_PLATFORMOFDEATH,super.getX(),super.getY(),null);
	}
	
	private Rectangle getCollideRect() {
		int width = getWidth();
		int height = getHeight();
		return new Rectangle(super.getX(), super.getY(), width, height);
	}
	
	@Override
	public int getWidth() {
		return SpriteManager.getInstance().IMAGE_PLATFORMOFDEATH.getWidth(null);
	}
	
	@Override
	public int getHeight() {
		return SpriteManager.getInstance().IMAGE_PLATFORMOFDEATH.getWidth(null);
	}

}
