package jumpypig;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class NotificationManager {

	private List<String> messages = new ArrayList<String>();
	private int currentMessage;
	// Fading
	private float currentAlpha;
	private float dAlpha;
	
	public NotificationManager(){
		currentMessage = 0;
		currentAlpha = 1.0f;
		dAlpha = 0.02f;
	}
	
	public void update(){
		if(isFinished() == false){
			// Fade out
			currentAlpha -= dAlpha;
			if(currentAlpha <= 0.0f){
				currentAlpha = 1.0f;
				// Move to next message
				currentMessage++;
			}
		}
	}
	
	public void paint(Graphics2D g){
		// Check if there is a message to print
		if(isFinished() == false){
		g.setFont(new Font("serif", Font.PLAIN, 15));
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, currentAlpha));
		// Print current message
		g.drawString(messages.get(currentMessage), 200, 150);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		}
	}
	
	public void addNotification(String n){
		messages.add(n);
	}
	
	public boolean isFinished(){
		// No more messages
		if(currentMessage == messages.size()){
			return true;
		}
		return false;
	}

}
