package jumpypig;

import java.awt.Graphics2D;

public class MenuView implements PanelView {
	
	private ObjectManager obm;
	private GamePanel parentPanel;
	
	public MenuView(GamePanel parent) {
		parentPanel = parent;
		obm = new ObjectManager();
	}

	@Override
	public void paint(Graphics2D g) {
		// Paint sprites
		g.drawImage(SpriteManager.getInstance().IMAGE_BACKGROUND,0,0,null);
		g.drawImage(SpriteManager.getInstance().IMAGE_LOGO,50,50,null);
		g.drawImage(SpriteManager.getInstance().IMAGE_STARTBUTTON,200,100,null);
		g.drawImage(SpriteManager.getInstance().IMAGE_HIGHSCOREBUTTON,200,150,null);
		g.drawImage(SpriteManager.getInstance().IMAGE_EXITBUTTON,200,200,null);
		// Paint objects
		obm.paint(g);
		
		
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
