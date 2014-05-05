package jumpypig;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameOverView implements PanelView{
	
	private ObjectManager obm;
	private GamePanel parentPanel;
	
	// Game Over items
	private ArrayList<Image> gameOverItems;
	public static final int SUBMITSCORE = 0;
	public static final int PLAYAGAIN = 1;
	public static final int MAINMENU = 2;
	
	// Current game over item
	private int currentItem;
	
	
	public GameOverView(GamePanel parent){
		//Set parent panel
		parentPanel = parent;
		
		obm = new ObjectManager();
		// No platforms
		obm.setNumberOfPlatforms(0);
		// Number of clouds
		obm.setNumberOfClouds(10);
		// Max range for clouds to spawn
		obm.setMaxCloudRange(GameFrame.SCREENSIZE.height);
		
		currentItem = 0;
		
		gameOverItems = new ArrayList<Image>();
		gameOverItems.add(SpriteManager.getInstance().IMAGE_STARTBUTTON);
		gameOverItems.add(SpriteManager.getInstance().IMAGE_STARTBUTTON);
		gameOverItems.add(SpriteManager.getInstance().IMAGE_STARTBUTTON);
	}

	@Override
	public void paint(Graphics2D g) {
		// Paint background
		g.drawImage(SpriteManager.getInstance().IMAGE_BACKGROUND,0,0,null);
		
		// Paint objects
		obm.paint(g);
		
		// Paint logo
		g.drawImage(SpriteManager.getInstance().IMAGE_LOGO, 
				GameFrame.SCREENSIZE.width/2 - SpriteManager.getInstance().IMAGE_LOGO.getWidth(null)/2,
				10,
				null);
		// Paint menu items
		for(int i=0;i<gameOverItems.size();i++) {
			// Paint item
			Image item = gameOverItems.get(i);
			g.drawImage(item, GameFrame.SCREENSIZE.width/2 - gameOverItems.get(0).getWidth(null)/2,
					SpriteManager.getInstance().IMAGE_LOGO.getHeight(null) + 40 + (item.getHeight(null)+20)*i,
					null);
			
			//If item is chosen paint pig
			if(i == currentItem) {
				g.drawImage(SpriteManager.getInstance().IMAGE_MENUHIGHLIGHT,
						GameFrame.SCREENSIZE.width/2 - gameOverItems.get(0).getWidth(null)/2 - 60,
						SpriteManager.getInstance().IMAGE_LOGO.getHeight(null) + 30 + (item.getHeight(null)+20)*i,
						50 , 50*69/76 ,
						null); 
			}
		}
	}

	@Override
	public void update() {
		obm.update(this);
		
	}

	@Override
	public void keyPressed(int k) {
		// TODO Auto-generated method stub
		
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
			if(getItem() == SUBMITSCORE){
				parentPanel.switchState(GamePanel.HIGHSCORE_STATE);
			}
			else if(getItem() == PLAYAGAIN){
				parentPanel.switchState(GamePanel.GAME_STATE);
			}
			else if(getItem() == MAINMENU){
				parentPanel.switchState(GamePanel.MENU_STATE);;
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
		if(currentItem == gameOverItems.size()-1) {
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
			currentItem = gameOverItems.size()-1;
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
