package jumpypig;

import java.awt.Font;
import java.awt.Graphics2D;

public class Score {
	
	private long score;
	private long SLOW_INTERVAL = 2000;
	private long STANDARD_INTERVAL = 1000;
	private long FAST_INTERVAL = 500;
	
	private long currentInterval;
	private long startingTime;
	
	public Score(){
		score = 0;
		currentInterval = STANDARD_INTERVAL;
		startingTime = System.currentTimeMillis();
	}
	
	public void update(){
		if(currentInterval < (System.currentTimeMillis() - startingTime)){
			score++;
			startingTime = System.currentTimeMillis();
		}
	}
	
	public void paint(Graphics2D g){
		g.setFont(new Font("serif", Font.PLAIN, 15));
		g.drawString("Score: " + score, 550, 50);
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
}
