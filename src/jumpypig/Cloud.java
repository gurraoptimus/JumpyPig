package jumpypig;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

public class Cloud implements GameObject {
	
	private final int CLOUD_1 = 0;
	private final int CLOUD_2 = 1;
	
	private final float FULLSPEED = 1; 
	
	private float posX,posY;
	private float distance;
	private Image image;
	
	/**
	 * Create a cloud with random sprite
	 * @param x
	 * @param y
	 * @param distance
	 */
	public Cloud(int x, int y, int distance) {
		posX = x;
		posY = y;
		this.distance = distance;

		//CHOOSE RANDOM CLOUD SPRITE
		Random rand = new Random();
		if(rand.nextInt(CLOUD_2+1) == CLOUD_1) {
			image = SpriteManager.getInstance().IMAGE_CLOUD_1;
		}else{
			image = SpriteManager.getInstance().IMAGE_CLOUD_2;
		}
		
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
		return (int) posY;
	}

	@Override
	public int getX() {
		return (int) posX;
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

	//NOT USED
	@Override
	public boolean intersecting(Rectangle rec) {
		return false;
	}
	
	public float getSpeed() {
		return FULLSPEED * getAlpha();
	}

	private float getAlpha() {
		return (100-distance)/100f;
	}
	
	private float getScale() {
		return getAlpha();
	}
	
	private int getPixelWidth() {
		return image.getWidth(null);
	}
	
	private int getPixelHeight() {
		return image.getHeight(null);
	}
	
	@Override
	public void paint(Graphics2D g) {
		
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getAlpha()));
		g.drawImage(image, (int) posX,(int) posY, (int) (getPixelWidth()*getScale()), (int) (getPixelHeight()*getScale()), null);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

}
