package jumpypig;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

	public GameFrame() {
		add(new GamePanel());
		
		//TODO COMMENTS
		setTitle("Jumpy Pig");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(640,480);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
	}
	
	public static void main(String[] args) {
		new GameFrame();
	}
}
