package jumpypig;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;

public class Score {
	
	private long score;
	private long SLOW_INTERVAL = 2000;
	private long STANDARD_INTERVAL = 1000;
	private long FAST_INTERVAL = 500;
	
	private long currentInterval;
	private long startingTime;
	
	private Font font;
	
	public Score(){
		score = 0;
		currentInterval = STANDARD_INTERVAL;
		startingTime = System.currentTimeMillis();
		
		//Load in font
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/BALLOON.TTF"));
			font = font.deriveFont(30f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//END font
	}
	
	public void update(){
		if(currentInterval < (System.currentTimeMillis() - startingTime)){
			score++;
			startingTime = System.currentTimeMillis();
		}
	}
	
	public void paint(Graphics2D g){
		String scoreStr = "Score: " + score;
		g.setPaint(new Color(215,122,178));
		g.setFont(font);
		g.drawString(scoreStr,(int) (GameFrame.SCREENSIZE.width - g.getFontMetrics(font).getStringBounds(scoreStr, g).getWidth() - 40),
				50);
	}

	public void setInterval(){
		//TODO Fix collision with buffs
		/*if(Collision with good buff){
			currentInterval = FAST_INTERVAL;
		}
		else if(Collision with bad buff){
			currentInterval = SLOW_INTERVAL;
		}
		*/
	}
	/**
	 * Return currentScore
	 * @return
	 */
	public int getScore() {
		return (int) score;
	}
}
