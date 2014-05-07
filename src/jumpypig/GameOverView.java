package jumpypig;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

public class GameOverView implements PanelView{
	
	private ObjectManager obm;
	private GamePanel parentPanel;
	
	// Game Over items
	private ArrayList<Image> gameOverItems;
	public static final int SUBMITSCORE = 0;
	public static final int RESTART = 1;
	public static final int MAINMENU = 2;
	
	// Current game over item
	private int currentItem;
	
	private long score = 0;
	private long finishScore = 0;
	
	private Font font;
	
	// Size
	private final Dimension size = GameFrame.SCREENSIZE;
	private int posX,posY;
	
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
		gameOverItems.add(SpriteManager.getInstance().IMAGE_SUBMITHIGHSCOREBUTTON);
		gameOverItems.add(SpriteManager.getInstance().IMAGE_RESTARTBUTTON);
		gameOverItems.add(SpriteManager.getInstance().IMAGE_MAINMENUBUTTON);
		
		//Load in font
			try {
				font = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/BALLOON.TTF"));
				font = font.deriveFont(90f);
			} catch (FontFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		//END font
	}

	@Override
	public void paint(Graphics2D g) {
		// Paint background
		g.drawImage(SpriteManager.getInstance().IMAGE_BACKGROUND,0,0,null);
		
		// Paint objects
		obm.paint(g);
		
		// Paint logo
		g.drawImage(SpriteManager.getInstance().IMAGE_LOGO,posX + size.width/2 - 300/2,
				posY + 10, 300 ,
				300*SpriteManager.getInstance().IMAGE_LOGO.getHeight(null)/SpriteManager.getInstance().IMAGE_LOGO.getWidth(null) , null);
		
		// Paint score
		String scoreStr = "" + score;
		g.setPaint(new Color(215,122,178));
		g.setFont(font);
		// Print from 0 up to current score
		if(score <= finishScore){
			g.drawString(scoreStr, (int) (GameFrame.SCREENSIZE.width/2 - g.getFontMetrics(font).getStringBounds(scoreStr, g).getWidth()/2),
				GameFrame.SCREENSIZE.height/2 + 15);
		}
		// Stop at finished score
		if(score > finishScore){
			scoreStr = "" + finishScore;
			g.drawString(scoreStr, (int) (GameFrame.SCREENSIZE.width/2 - g.getFontMetrics(font).getStringBounds(scoreStr, g).getWidth()/2),
				GameFrame.SCREENSIZE.height/2 + 15);
		}
		
		// Paint menu items
		for(int i=0;i<gameOverItems.size();i++) {
			// Paint item
			Image item = gameOverItems.get(i);
			g.drawImage(item, GameFrame.SCREENSIZE.width/2 - gameOverItems.get(0).getWidth(null)/2 + 10,
					SpriteManager.getInstance().IMAGE_LOGO.getHeight(null) + 60 + (item.getHeight(null)+20)*i,
					null);
			
			//If item is chosen paint pig
			if(i == currentItem) {
				g.drawImage(SpriteManager.getInstance().IMAGE_MENUHIGHLIGHT,
						GameFrame.SCREENSIZE.width/2 - gameOverItems.get(0).getWidth(null)/2 - 50,
						SpriteManager.getInstance().IMAGE_LOGO.getHeight(null) + 50 + (item.getHeight(null)+20)*i,	null); 
			}
		}
	}

	@Override
	public void update() {
		obm.update(this);
		score++;
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
			else if(getItem() == RESTART){
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
	private int getItem() {
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
	
	public void setScore(long n){
		finishScore = n;
		score = 0;
	}
	
	
}
