package jumpypig;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MenuView implements PanelView {
	
	private ObjectManager obm;
	private GamePanel parentPanel;
	
	// Menu items
	private ArrayList<Image> menuItems;
	public static final int START = 0;
	public static final int HIGHSCORE = 1;
	public static final int EXIT = 2;
	
	// Current menu item
	private int currentItem;
	
	// Size
	private final Dimension size = GameFrame.SCREENSIZE;
	private int posX,posY;
	
	public MenuView(GamePanel parent) {
		parentPanel = parent;
		obm = new ObjectManager();
		currentItem = 0;
		
		// Init position
		posX = GameFrame.SCREENSIZE.width/2-size.width/2;
		posY = GameFrame.SCREENSIZE.height/2-size.height/2;
		// Set menu items
		
		menuItems = new ArrayList<Image>();
		menuItems.add(SpriteManager.getInstance().IMAGE_STARTBUTTON);
		menuItems.add(SpriteManager.getInstance().IMAGE_HIGHSCOREBUTTON);
		menuItems.add(SpriteManager.getInstance().IMAGE_EXITBUTTON);
	}

	@Override
	public void paint(Graphics2D g) {
		// Paint sprites
		g.drawImage(SpriteManager.getInstance().IMAGE_BACKGROUND,0,0,null);
		
		// Paint objects
		obm.paint(g);
		
		// Paint logo
		g.drawImage(SpriteManager.getInstance().IMAGE_LOGO,posX + size.width/2 - 300/2, posY + 10, 300 , 300*SpriteManager.getInstance().IMAGE_LOGO.getHeight(null)/SpriteManager.getInstance().IMAGE_LOGO.getWidth(null) , null);
		
		// Paint menu items
		for(int i=0;i<menuItems.size();i++) {
			// Paint item
			g.drawImage(menuItems.get(i),posX + size.width/2 - 30, posY + 200 + 50*i,null);
			
			// If item is chosen paint pig
			if(i == currentItem) {
				g.drawImage(SpriteManager.getInstance().IMAGE_MENUHIGHLIGHT, posX + size.width/2 - 90, posY + 195 + 50*i, 50 , 50*69/76 ,null); 
			}
			
		}
	}

	@Override
	public void update() {
		obm.update(this);
	}

	@Override
	public void keyPressed(int k) {
		//TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(int k) {
		// Move up
		if(k == KeyEvent.VK_UP){
			switchUp();
		}
		// Move down
		else if(k == KeyEvent.VK_DOWN){
			switchDown();
		}
		// Activate item
		else if(k == KeyEvent.VK_ENTER){
			if(getItem() == START){
				parentPanel.switchState(GamePanel.GAME_STATE);
			}
			else if(getItem() == HIGHSCORE){
				parentPanel.switchState(GamePanel.HIGHSCORE_STATE);
			}
			else if(getItem() == EXIT){
				System.exit(0);
			}
		}

	}

	@Override
	public void keyTyped(int k) {
		// TODO Auto-generated method stub

	}

	/**
	 * Move one menu item down
	 */
	public void switchDown() {
		// If at the end - start over
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
		// If at the top - move to the bottom
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
	
	@Override
	public int getHeight() {
		return parentPanel.getHeight();
	}

	@Override
	public int getWidth() {
		return parentPanel.getWidth();
	}

}
