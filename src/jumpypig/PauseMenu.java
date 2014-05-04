package jumpypig;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

public class PauseMenu {

	//Menu items
	private ArrayList<Image> menuItems;
	public static final int RESUME = 0;
	public static final int RESTART = 1;
	public static final int EXIT = 2;
	
	//Current menu item
	private int currentItem;
	
	//Size
	private final Dimension size = GameFrame.SCREENSIZE;
	private int posX,posY;
	
	public PauseMenu() {
		currentItem = 0;
		//init. position
		posX = GameFrame.SCREENSIZE.width/2-size.width/2;
		posY = GameFrame.SCREENSIZE.height/2-size.height/2;
		
		//Set menu items
		menuItems = new ArrayList<Image>();
		//TODO Fix correct button imgs
		menuItems.add(SpriteManager.getInstance().IMAGE_STARTBUTTON);
		menuItems.add(SpriteManager.getInstance().IMAGE_STARTBUTTON); 
		menuItems.add(SpriteManager.getInstance().IMAGE_EXITBUTTON);
	}
	
	public void paint(Graphics2D g) {
		//Paint black background in the center of the screen
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		g.setPaint(Color.black);
		g.fillRect(posX, posY , size.width, size.height);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.f));
		
		//Paint logo
		g.drawImage(SpriteManager.getInstance().IMAGE_LOGO,posX + size.width/2 - 300/2, posY + 10, 300 , 300*SpriteManager.getInstance().IMAGE_LOGO.getHeight(null)/SpriteManager.getInstance().IMAGE_LOGO.getWidth(null) , null);
		
		//Paint menu items
		for(int i=0;i<menuItems.size();i++) {
			//paint item
			g.drawImage(menuItems.get(i),posX + size.width/2 - 30, posY + 200 + 50*i,null);
			
			//if item is choosen praint pig
			if(i == currentItem) {
				g.drawImage(SpriteManager.getInstance().IMAGE_MENUHIGHLIGHT, posX + size.width/2 - 90, posY + 195 + 50*i, 50 , 50*69/76 ,null); 
			}
			
		}
		//END menu items
		
	}
	
	/**
	 * Move one menu item down
	 */
	public void switchDown() {
		//if at the end - start over
		if(currentItem == menuItems.size()-1) {
			currentItem = 0;
		}else{
			currentItem++;
		}
	}
	
	/**
	 * Move one menu item up
	 */
	public void switchUp() {
		//If at the top - move to the bottom
		if(currentItem == 0) {
			currentItem = menuItems.size()-1;
		}else{
			currentItem--;
		}
	}
	
	/**
	 * Get current menu item 
	 * @return
	 */
	public int getItem() {
		return currentItem;
	}
}
