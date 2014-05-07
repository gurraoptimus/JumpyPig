package jumpypig;

import java.awt.Dimension;

import javax.swing.JFrame;


public class GameFrame extends JFrame {

	public static final Dimension SCREENSIZE = new Dimension(640,480);
	
	public GameFrame() {
		add(new GamePanel());
		
		//TODO COMMENTS
		setTitle("Jumpy Pig");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(SCREENSIZE.width,SCREENSIZE.height);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
	}
	
	public static void main(String[] args) {
		new GameFrame();
	}
}
