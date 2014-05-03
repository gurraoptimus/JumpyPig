package jumpypig;

import java.awt.Graphics2D;

public interface PanelView {

	public void paint(Graphics2D g);
	public void update();
	public void keyPressed(int k);
	public void keyReleased(int k);
	public void keyTyped(int k);
	
}
