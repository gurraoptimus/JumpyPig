package jumpypig;

import java.awt.Dimension;
import java.awt.Graphics2D;


public class GameView implements PanelView {
	
	private Player player;
	private ObjectManager obm;
	private boolean ingame;
	private GamePanel parentPanel;
	
	
	public GameView(GamePanel parent) {
		//INIT.
		player = new Player();
		obm = new ObjectManager();
		parentPanel = parent;
	}
	
	public Player getPlayer() {
		return player;
	}
	

	@Override
	public void paint(Graphics2D g) {
		g.drawImage(SpriteManager.getInstance().IMAGE_BACKGROUND,0,0,null);
		player.paint(g);
		obm.paint(g);
	}

	@Override
	public void update() {
		player.update(this);
		obm.update(this);
	}

	@Override
	public void keyPressed(int k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub

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

}
