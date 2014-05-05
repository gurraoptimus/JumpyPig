package jumpypig;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotificationManager {

	private List<String> messages = new ArrayList<String>();
	private int currentMessage;
	// Fading
	private float currentAlpha;
	private float dAlpha;
	
	private Font font;
	
	public NotificationManager(){
		currentMessage = 0;
		currentAlpha = 1.0f;
		dAlpha = 0.02f;
		
		//Load in font
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/BALLOON.TTF"));
			font = font.deriveFont(70f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//END font
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
			g.setPaint(new Color(255,255,255));
			g.setFont(font);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, currentAlpha));
			// Print current message
			g.drawString(messages.get(currentMessage), 
					GameFrame.SCREENSIZE.width/2 - (int) g.getFontMetrics(font).getStringBounds(messages.get(currentMessage), g).getWidth()/2,
					GameFrame.SCREENSIZE.height/2);
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
