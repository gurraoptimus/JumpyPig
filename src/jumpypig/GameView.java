package jumpypig;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;


public class GameView implements PanelView {
	
	private final int GAME_STATE = 0;
	private final int PAUSE_STATE = 1;
	private final int GAME_OVER_STATE = 2;
	
	private Player player;
	private ObjectManager obm;
	private GamePanel parentPanel;
	private PauseMenu pausemenu;
	private int STATE;
	
	
	public GameView(GamePanel parent) {
		//INIT.
		player = new Player(50,0);
		obm = new ObjectManager();
		obm.initObjects();
		parentPanel = parent;
		
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
		//Paint objects
		obm.paint(g);
		//Paint player
		player.paint(g);
		
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
				
				else if(pausemenu.getItem() == PauseMenu.EXIT) {
					//TODO: Fix this
					System.exit(0);
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
	}

}
