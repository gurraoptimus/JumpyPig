package jumpypig;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;


public class Platform implements GameObject{
	
	private int posX,posY;
	private int length;
	private int pixelWidth;
	private int pixelHeight;
	
	/**
	 * Create new platform at (x,y) with length, length.
	 * @param x
	 * @param y
	 * @param length
	 */
	public Platform(int x, int y, int length) {
		posX = x;
		posY = y;
		this.length = length;
		
		//Only use getPixelWidth/Height here. Will cause concurrency exception otherwise
		pixelWidth = getPixelWidth();
		pixelHeight = getPixelHeight();
	}

	@Override
	public void setX(int x) {
		posX = x;
	}

	@Override
	public void setY(int y) {
		posY = y;
	}

	@Override
	public int getY() {
		return posY;
	}

	@Override
	public int getX() {
		return posX;
	}
	
	/**
	 * Get width of platform in pixels. Only use in constructor. Causing ConcurrencyException otherwise :S
	 * @return
	 */
	private int getPixelWidth() {
		int width = SpriteManager.getInstance().IMAGE_PLATFORM_LEFT.getWidth(null);
		width += SpriteManager.getInstance().IMAGE_PLATFORM_TOP.getWidth(null)*length;
		width += SpriteManager.getInstance().IMAGE_PLATFORM_RIGHT.getWidth(null);
		return width;
	}
	
	/**
	 * Get height of platform in pixels. Only use in constructor. Causing ConcurrencyException otherwise :S
	 * @return
	 */
	private int getPixelHeight() {
		return SpriteManager.getInstance().IMAGE_PLATFORM_LEFT.getHeight(null);
	}

	/**
	 * Check if platform is visible, only check if position is bigger than 0!!!!
	 */
	@Override
	public boolean isVisible() {
		if(posX+pixelWidth <0) {
			return false;
		}
		return true;
	}

	@Override
	public boolean intersecting(Rectangle rec) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void paint(Graphics2D g) {
		//DRAW LEFT
		g.drawImage(SpriteManager.getInstance().IMAGE_PLATFORM_LEFT,posX,posY,null);
		
		//DRAW MIDDLE
		int segmentX = -1 + posX + SpriteManager.getInstance().IMAGE_PLATFORM_LEFT.getWidth(null);
		int segmentY = posY + SpriteManager.getInstance().IMAGE_PLATFORM_LEFT.getHeight(null) - SpriteManager.getInstance().IMAGE_PLATFORM_BOTTOM.getHeight(null);
				
		for(int i=0;i<length;i++) {
			g.drawImage(SpriteManager.getInstance().IMAGE_PLATFORM_TOP,segmentX,posY,null);
			g.drawImage(SpriteManager.getInstance().IMAGE_PLATFORM_BOTTOM,segmentX,segmentY,null);
			
			segmentX += -1 + SpriteManager.getInstance().IMAGE_PLATFORM_TOP.getWidth(null);
		}
		
		//DRAW RIGHT 
		g.drawImage(SpriteManager.getInstance().IMAGE_PLATFORM_RIGHT,segmentX,posY,null);
		
		//DRAW RECT
		g.setPaint(new Color(161,110,54));
		
		int rectX = -1 + posX + SpriteManager.getInstance().IMAGE_PLATFORM_LEFT.getWidth(null);
		int rectY = -1 + posY + SpriteManager.getInstance().IMAGE_PLATFORM_TOP.getHeight(null);
		g.fillRect(rectX,rectY,segmentX-posX-SpriteManager.getInstance().IMAGE_PLATFORM_LEFT.getWidth(null)+1,segmentY-posY-SpriteManager.getInstance().IMAGE_PLATFORM_TOP.getHeight(null)+2);
		
	}

	@Override
	public void moveX(int x) {
		posX += x;
	}

	@Override
	public void moveY(int y) {
		posY += y;
	}

}
