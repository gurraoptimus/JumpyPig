package jumpypig;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public interface GameObject {

	public void setX(int x);
	public void setY(int y);
	
	public int getY();
	public int getX();
	
	public void moveX(float x);
	public void moveY(float y);
	
	public int getWidth();
	public int getHeight();
	
	/**
	 * Is object outside screen?
	 * @return
	 */
	public boolean isVisible();
	
	/**
	 * Is object colliding with rec?
	 * @param rec
	 * @return
	 */
	public boolean intersecting(Rectangle rec);
	
	/**
	 * Paint on canvas
	 * @param g
	 */
	public void paint(Graphics2D g);
}
