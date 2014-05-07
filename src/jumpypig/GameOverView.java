package jumpypig;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
	private Font fontName;
	
	// Size
	private final Dimension size = GameFrame.SCREENSIZE;
	private int posX,posY;
	
	private String nameStr = "";
	private String typeStr = " ";
	
	// Timer for _
	private long currentInterval;
	private long startingTime;
	
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
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Set sizes
		font = font.deriveFont(90f);
		fontName = font.deriveFont(40f);
		//END font
			
			currentInterval = 1000;
			startingTime = System.currentTimeMillis();
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
		g.drawString(scoreStr, (int) (GameFrame.SCREENSIZE.width/2 - g.getFontMetrics(font).getStringBounds(scoreStr, g).getWidth()/2),
				GameFrame.SCREENSIZE.height/2 + 10);
		
		
		// Paint name
		g.setFont(fontName);
		g.drawString("Name: " + nameStr + typeStr, (int) (GameFrame.SCREENSIZE.width/2 - 140),
				GameFrame.SCREENSIZE.height/2 + 50);
		
		// Paint menu items
		for(int i=0;i<gameOverItems.size();i++) {
			// Paint item
			Image item = gameOverItems.get(i);
			g.drawImage(item, GameFrame.SCREENSIZE.width/2 - gameOverItems.get(0).getWidth(null)/2 + 10,
					SpriteManager.getInstance().IMAGE_LOGO.getHeight(null) + 90 + (item.getHeight(null)+20)*i,
					null);
			
			//If item is chosen paint pig
			if(i == currentItem) {
				g.drawImage(SpriteManager.getInstance().IMAGE_MENUHIGHLIGHT,
						GameFrame.SCREENSIZE.width/2 - gameOverItems.get(0).getWidth(null)/2 - 50,
						SpriteManager.getInstance().IMAGE_LOGO.getHeight(null) + 80 + (item.getHeight(null)+20)*i,	null); 
			}
		}
	}

	@Override
	public void update() {
		obm.update(this);
		//Update score
		if(score < finishScore){
			score++;
		}
		else{
			score = finishScore;
		}
		//End blink
		
		//blink "_"
		if(currentInterval < (System.currentTimeMillis() - startingTime)){
			startingTime = System.currentTimeMillis();
			if(typeStr == " "){
				typeStr = "_";
			}
			else{
				typeStr = " ";
			}
		}
		//end blink
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
				//Submit highscore
				submitHighscore();
				parentPanel.switchState(GamePanel.HIGHSCORE_STATE);
				//update highscores
				((HighscoreView) parentPanel.getCurrentState()).updateHighscores();
			}
			else if(getItem() == RESTART){
				parentPanel.switchState(GamePanel.GAME_STATE);
			}
			else if(getItem() == MAINMENU){
				parentPanel.switchState(GamePanel.MENU_STATE);
			}
			// Make default choice submit score
			currentItem = 0;
		}
		// Type name
		else if(k >= KeyEvent.VK_A && k <= KeyEvent.VK_Z || k >= KeyEvent.VK_0 && k <= KeyEvent.VK_9){
			nameStr += KeyEvent.getKeyText(k);
		}
		//backspace
		else if(k == KeyEvent.VK_BACK_SPACE) {
			if(nameStr.length() > 0) {
				nameStr = nameStr.substring(0, nameStr.length()-1);
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
		score = 0;
		finishScore = n;
	}
	
	private void submitHighscore() {
		try {
			//OPEN CONNECTION
			URL site = new URL("http://home.arnflo.se/JumpyPig/highscore.php");
			URLConnection conn = site.openConnection();
			conn.setDoOutput(true);
			//WRITE TO CONNECTION
			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
			writer.write("name="+nameStr+"&score="+score);
			writer.close();
			//POST
			conn.getInputStream();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
