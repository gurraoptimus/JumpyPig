package jumpypig;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;


public class GameView implements PanelView {
	
	private final int GAME_STATE = 0;
	private final int PAUSE_STATE = 1;
	
	//LEVELS
	private final int[] LEVEL_LIMITS = new int[]{0,5,10};
	
	
	private Player player;
	private ObjectManager obm;
	private GamePanel parentPanel;
	private PauseMenu pausemenu;
	private Score score;
	private int STATE;
	private ArrayList<MountainBackground> backgrounds;
	
	private NotificationManager nm;
	
	
	//MOUNTAIN BACKGROUND
	/**
	 * Use this to loop mountain background
	 */
	private class MountainBackground {
		private static final float SPEED = 0.5f;
		private static final int POSY = 100;
		private float posX;
		public MountainBackground(int x) {
			posX = x;
		}
		
		/**
		 * Is background inside frame
		 * @return
		 */
		public boolean isVisible() {
			//Check if outside frame
			if(posX + getWidth() < 0) {
				return false;
			}
			return true;
		}
		
		public void update() {
			posX -= SPEED;
		}
		
		public void paint(Graphics2D g) {
			g.drawImage(SpriteManager.getInstance().IMAGE_BACKGROUNDMOUNTAIN,(int) posX, POSY, null);
		}
		
		public int getWidth() {
			return SpriteManager.getInstance().IMAGE_BACKGROUNDMOUNTAIN.getWidth(null);
		}
		
		public int getHeight() {
			return SpriteManager.getInstance().IMAGE_BACKGROUNDMOUNTAIN.getHeight(null);
		}
	}
	//END MOUNTAIN BACKGROUND
	
	
	
	
	
	public GameView(GamePanel parent) {
		//INIT.
		player = new Player(50,0);
		obm = new ObjectManager();
		obm.initObjects();
		score = new Score();
		nm = new NotificationManager();
		
		parentPanel = parent;
		
		//Add background
		backgrounds = new ArrayList<MountainBackground>();
		backgrounds.add(new MountainBackground(0));
		
		STATE = GAME_STATE;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public ObjectManager getObjectManager() {
		return obm;
	}
	

	@Override
	public void paint(Graphics2D g) {
		//Paint background
		g.drawImage(SpriteManager.getInstance().IMAGE_BACKGROUND,0,0,null);
		
		//Paint mountain backgrounds
		for(int i=0;i<backgrounds.size();i++) {
			backgrounds.get(i).paint(g);
		}
		//Backgrounds
		
		//Paint objects
		obm.paint(g);
		//Paint player
		player.paint(g);
		//Paint score
		score.paint(g);
		//Paint notifications
		nm.paint(g);
		//Game is paused - paint pause menu
		if(STATE == PAUSE_STATE) {
			pausemenu.paint(g);
		}
		
	}

	@Override
	public void update() {
		//If paused - don't update
		if(STATE != PAUSE_STATE) {
			obm.update(this);
			player.update(this);
			score.update();
			nm.update();
			
			//Update mountain background
			Iterator<MountainBackground> it = backgrounds.iterator();
			while(it.hasNext()) {
				MountainBackground bg = it.next();
				
				if(!bg.isVisible()) {
					it.remove();
				}else{
					bg.update();
				}
			}
			if(backgrounds.size() < 2) {
				//Add new background behind last one
				backgrounds.add(new MountainBackground(backgrounds.get(backgrounds.size()-1).getWidth()));
			}
			//End backgrounds
			
			//Check if player died - change view to game over
			if(player.isDead()) {
				//Switch view
				parentPanel.switchState(GamePanel.GAMEOVER_STATE);
				//Set score
				((GameOverView) parentPanel.getCurrentState()).setScore(score.getScore());
				//Reset view in case player wants to play again
				restart();
			}
			//end player
			
			//Update game level
			for(int i=0;i<LEVEL_LIMITS.length;i++) {
				//if score is bigger than limit - upgrade
				if(score.getScore() > LEVEL_LIMITS[i]) {
					obm.setLevel(i+1);
				}
			}
			//end level
			
		}
	}

	@Override
	public void keyPressed(int k) {
		if(STATE == GAME_STATE && k == KeyEvent.VK_SPACE) {
			player.jump();
		}
	}

	@Override
	public void keyReleased(int k) {
		//Stop jump if in game and released SPACE
		if(STATE == GAME_STATE && k == KeyEvent.VK_SPACE) {
			player.fall();
		}
		
		//Pause game
		else if(STATE == GAME_STATE && k == KeyEvent.VK_ESCAPE) {
			pause();
		}
		
		//In pause menu
		else if(STATE == PAUSE_STATE) {
			//Unpause game
			if(k == KeyEvent.VK_ESCAPE) {
				unpause();
			}
			//Move up in menu
			else if(k == KeyEvent.VK_UP) {
				pausemenu.switchUp();
			}
			//Move down in menu
			else if(k == KeyEvent.VK_DOWN) {
				pausemenu.switchDown();
			}
			//Activate item
			else if(k == KeyEvent.VK_ENTER) {
				//If continue
				if(pausemenu.getItem() == PauseMenu.RESUME) {
					unpause();
				}
				//If restart
				else if(pausemenu.getItem() == PauseMenu.RESTART) {
					unpause();
					restart();
				}
				
				else if(pausemenu.getItem() == PauseMenu.MAINMENU) {
					unpause();
					restart();
					parentPanel.switchState(GamePanel.MENU_STATE);
				}
			}
		}
		//END PAUSE MENU
		
	}

	@Override
	public void keyTyped(int k) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getHeight() {
		return parentPanel.getHeight();
	}

	@Override
	public int getWidth() {
		return parentPanel.getWidth();
	}
	
	/**
	 * Pause the game
	 */
	private void pause() {
		STATE = PAUSE_STATE;
		
		//If no pause menu has been initialized, init. one.
		if(pausemenu == null) {
			pausemenu = new PauseMenu();
		}
			
	}
	
	/**
	 * Unpause the game
	 */
	private void unpause() {
		STATE = GAME_STATE;
	}
	
	/**
	 * Restart the game
	 */
	private void restart() {
		player = new Player(50,0);
		obm = new ObjectManager();
		obm.initObjects();
		pausemenu = null;
		score = new Score();
		nm = new NotificationManager();
	}

}
