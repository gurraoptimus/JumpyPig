package jumpypig;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

public class Block implements GameObject{
	
	public int posX, posY;
	public Image image;
	
	/**
	 * Create a new block at (x,y)
	 * @param x
	 * @param y
	 */
	public Block(int x, int y){
		posX = x;
		posY = y;
		// Get the image for the block
		image = SpriteManager.getInstance().IMAGE_BLOCK;
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

	@Override
	public void moveX(float x) {
		posX += x;
	}

	@Override
	public void moveY(float y) {
		posY += y;
	}

	@Override
	public boolean isVisible() {
			if(posX + getPixelWidth() < 0) {
				return false;
			}
			return true;
	}
	
	private int getPixelWidth(){
		return image.getWidth(null);
	}
	
	private int getPixelHeight(){
		return image.getHeight(null);
	}
	
	@Override
	public boolean intersecting(Rectangle rec) {
		return rec.intersects(new Rectangle(posX, posY, getPixelWidth(), getPixelHeight()));
	}

	@Override
	public void paint(Graphics2D g) {
		g.drawImage(image, posX, posY, null);
	}
	
}
