package jumpypig;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Animation {

	private ArrayList<Image> sprites;
	//Sprite right now(index)
	private int currentSprite;
	//Last update time
	private long lastUpdate;
	//Update time interval
	private long interval;
	
	/**
	 * Create new Animation
	 */
	public Animation(String[] url_sprites,long millis) {
		sprites = new ArrayList<Image>();
		
		//Create Images from URLs
		for(String url : url_sprites) {
			sprites.add(new ImageIcon(this.getClass().getResource(url)).getImage());
		}
		
		currentSprite=0;
		lastUpdate=System.currentTimeMillis();
		interval = millis;
	}
	
	public void update() {
		//Check if interval has passed and current sprite isn't last
		if( (System.currentTimeMillis()-lastUpdate) >= interval  && currentSprite != sprites.size()-1) {
			//Update currentSprite
			currentSprite++;
			
			//Set last update
			lastUpdate = System.currentTimeMillis();
		}
	}
	
	/**
	 * Check if animation is playing.
	 */
	public boolean isPlaying() {
		if(currentSprite < sprites.size()-1) {
			return true;
		}
		return false;
	}
	
	/**
	 * Get current sprite
	 * @return
	 */
	public Image getSprite() {
		return sprites.get(currentSprite);
	}
	
	/**
	 * Reset animation
	 */
	public void reset() {
		//Reset current sprite
		currentSprite = 0;
		//Reset clock
		lastUpdate = System.currentTimeMillis();
	}
	
	
}
