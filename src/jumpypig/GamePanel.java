package jumpypig;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener,Runnable {

	private final int FPS = 60;
	private final int MENU_STATE = 0;
	private final int GAME_STATE = 1;
	private final int HIGHSCORE_STATE = 2;
	
	private ArrayList<PanelView> states;
	private int currentState;
	
	public GamePanel() {
		
		//FIX
		currentState = GAME_STATE;
		
		//INIT.
		setBackground(Color.black);
		setDoubleBuffered(true);
		setFocusable(true);
		
		//ADD STATES
		states = new ArrayList<PanelView>();
		states.add(new MenuView(this));
		states.add(new GameView(this));
		states.add(new HighscoreView(this));

	}
	
	private PanelView getCurrentState() {
		return states.get(currentState);
	}
	
	private void update() {
		getCurrentState().update();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		//CLEAR SCREEN
		super.paintComponent(g);
		
		//GET 2D canvas
		Graphics2D g2 = (Graphics2D) g;
		
		//CALL CURRENT STATES PAINT METH.
		getCurrentState().paint(g2);
		
		//RELEASE
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
		
	}

	@Override
	public void addNotify() {
		super.addNotify();
		Thread selfThread = new Thread(this);
		addKeyListener(this);
		selfThread.start();
	}
	
	@Override
	public void run() {
		while(true) {
			
			//UPDATE PANELVIEW GRAPHICS
			update();
			
			//REPAINT GRAPHICS
			repaint();
			
			//SLEEP FOR A WHILE
			try {
				Thread.sleep(1000/FPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		getCurrentState().keyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		getCurrentState().keyReleased(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {
		getCurrentState().keyTyped(e.getKeyCode());
	}
	
	
	
	
	
}
