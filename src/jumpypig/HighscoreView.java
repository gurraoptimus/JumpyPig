package jumpypig;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class HighscoreView implements PanelView {
	
	private static final int HIGHSCORE_NAME_POS = 150;
	private static final int HIGHSCORE_SCORE_POS = 450;
	
	private ObjectManager obm;
	
	private ArrayList<HighscoreEntry> highscores;
	private GamePanel parentPanel;
	
	private boolean error;
	private boolean loading;
	
	private Font font;
	
	/**
	 * Class to handle highscore entries
	 *
	 */
	private class HighscoreEntry {
		private String name;
		private int score;
		
		public HighscoreEntry(String name, int score) {
			this.name = name;
			this.score = score;
		}
		
		public int getScore() {
			return score;
		}
		
		public String getName() {
			return name;
		}
		
	}
	
	public HighscoreView(GamePanel gamePanel) {
		//INIT.
		parentPanel = gamePanel;
		highscores = new ArrayList<HighscoreEntry>();
		error = false;
		loading = true;
		obm = new ObjectManager();
		//no platforms
		obm.setNumberOfPlatforms(0);
		//10 clouds
		obm.setNumberOfClouds(10);
		//set cloud spawn range
		obm.setMaxCloudRange(GameFrame.SCREENSIZE.height);
		
		//load font
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/BALLOON.TTF"));
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Set font size
		font = font.deriveFont(30f);
		//font end
		
		
		//Load highscores
		updateHighscores();
		
	}

	@Override
	public void paint(Graphics2D g) {
		//Paint background
		g.drawImage(SpriteManager.getInstance().IMAGE_BACKGROUND,0,0,null);
		
		//paint objects
		obm.paint(g);
		
		g.setPaint(new Color(215,122,178));
		//Print "Highscores"
		g.setFont(font.deriveFont(100f));
		g.drawString("Highscore",
				(int) (GameFrame.SCREENSIZE.width/2 - g.getFontMetrics().getStringBounds("Highscore", g).getWidth()/2),
				100);
		
		//If loading - write "Loading..."
		g.setFont(font);
		if(loading) {
			
			g.drawString("LOADING...", GameFrame.SCREENSIZE.width/2, 50);
			
		}else{
			
			//IF ERROR
			if(error) {
				
				g.drawString("SOMETHING WENT WRONG", GameFrame.SCREENSIZE.width/2, 50);
				
			}
			//IF NOT ERROR
			else{
				//PRINT HIGHSCORES
				int yPos = 170;
				for(HighscoreEntry entry : highscores) {
					//Print name
					g.drawString(entry.getName(), HIGHSCORE_NAME_POS, yPos);
					//Print score
					g.drawString("" + entry.getScore(), HIGHSCORE_SCORE_POS, yPos);
					
					//Increment height
					yPos += g.getFontMetrics(font).getStringBounds(entry.getName()+entry.getScore(), g).getHeight() + 20;
				}
				//END HIGHSCORES
			}
			
		}
		
		//Print main menu button
		g.drawImage(SpriteManager.getInstance().IMAGE_MAINMENUBUTTON,
				GameFrame.SCREENSIZE.width/2 - SpriteManager.getInstance().IMAGE_MAINMENUBUTTON.getWidth(null)/2,
				GameFrame.SCREENSIZE.height - 48,
				null);
		
		
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * Update highscores
	 */
	public void updateHighscores() {
		//Fetch highscores
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				highscores.clear();
				try {
					URL site = new URL("http://192.168.1.16/JumpyPig/highscore.php?highscore=1");
					BufferedReader data = new BufferedReader(new InputStreamReader(site.openStream()));
					
					//Iterate through resonse and load in highscores
					String response = data.readLine();
					//response = "Player1:500;Player2:300;Player3:100 etc"
					for(String res : response.split(";")) {
						String[] highscore = res.split(":");
						highscores.add(new HighscoreEntry(highscore[0], Integer.parseInt(highscore[1])));
					}
					
				} catch (MalformedURLException e) {
					error = true;
					e.printStackTrace();
				} catch (IOException e) {
					error = true;
					e.printStackTrace();
				}
				
				loading = false;
			}
			
		}).start();
		//END FETCHING
	}

}
