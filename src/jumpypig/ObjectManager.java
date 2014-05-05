package jumpypig;


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class ObjectManager {
	
	private int NUMBER_OF_PLATFORMS;
	private int PLATFORM_SPEED;
	private int NUMBER_OF_CLOUDS;
	
	private int MAX_CLOUD_RANGE;
	private int MAX_PLATFORM_RANGE_X;
	private int MAX_PLATFORM_RANGE_Y;
	
	private ArrayList<GameObject> platforms;
	private ArrayList<GameObject> clouds;
	
	private Random rand;
	
	
	public ObjectManager() {
		//INIT.
		NUMBER_OF_PLATFORMS = 3;
		PLATFORM_SPEED = 5;
		NUMBER_OF_CLOUDS = 5;
		MAX_CLOUD_RANGE = GameFrame.SCREENSIZE.height/3;
		MAX_PLATFORM_RANGE_X = 300;
		MAX_PLATFORM_RANGE_Y = GameFrame.SCREENSIZE.height/2;
		
		platforms = new ArrayList<GameObject>();
		clouds = new ArrayList<GameObject>();
		
		rand = new Random();
	}
	
	public void setNumberOfPlatforms(int n) {
		NUMBER_OF_PLATFORMS = n;
	}
	
	public void setNumberOfClouds(int n) {
		NUMBER_OF_CLOUDS = n;
	}
	
	public void setPlatformSpeed(int n) {
		PLATFORM_SPEED = n;
	}
	
	public void setMaxCloudRange(int n) {
		MAX_CLOUD_RANGE = n;
	}

	/**
	 * Add start objects
	 */
	public void initObjects() {
		platforms.add(new Platform(0, 200, 20));	
	}
	
	/**
	 * Handle number of objects, create new if needed 
	 */
	private void manageObjects() {
		//CHECK PLATFORMS
		if(NUMBER_OF_PLATFORMS > platforms.size()) {			
			int add = NUMBER_OF_PLATFORMS - platforms.size();
			
			//ADD MISSING OBJECTS
			for(int i=0;i<add;i++) {
				Platform prevPlatform = (Platform) platforms.get(platforms.size()-1);
				//ADD PLATFORM AFTER LAST ONE
				Platform p = new Platform(GameFrame.SCREENSIZE.width + prevPlatform.getWidth() + 30, prevPlatform.getY(), 4);
				platforms.add(p);
				
			}
			
		}
		
		//CHECK CLOUDS
		if(NUMBER_OF_CLOUDS > clouds.size()) {
			int add = NUMBER_OF_CLOUDS - clouds.size();
			
			//ADD MISSING OBJECTS
			for(int i=0;i<add;i++) {
				Cloud p = new Cloud(GameFrame.SCREENSIZE.width + rand.nextInt(100),rand.nextInt(MAX_CLOUD_RANGE),rand.nextInt(50));
				clouds.add(p);
			}
			
		}
	}
	
	//UPDATE ALL OBJECTS
	public void update(PanelView gv) {
		
		//UPDATE PLATFORMS
		Iterator<GameObject> it = platforms.iterator();
		//Collided with player
		boolean collided = false;
		while(it.hasNext()) {
			Platform platform = (Platform) it.next();
			
			//MOVE PLATFORM
			platform.moveX(-1*PLATFORM_SPEED);
			
			if(!platform.isVisible()) {
				it.remove();
			}
			
			//IF NOT DEMO PLAY - CHECK FOR COLLISIONS WITH PLAYER
			if(gv instanceof GameView) {

				//COLLISION WITH PLAYER
				if(platform.intersecting(((GameView) gv).getPlayer().getJumpRect())) {
					collided = true;
					
					//Handle collision
					platform.collidePlayer(((GameView) gv).getPlayer());	
				}
				
			}
			
		}
		
		//if in gameplay - change state of player
		if(gv instanceof GameView) {
			if(!collided) {
				((GameView) gv).getPlayer().setState(Player.FALLING_STATE);
			}
		}
		//END PLATFORMS
		
		//UPDATE CLOUDS
		it = clouds.iterator();
		while(it.hasNext()) {
			Cloud cloud = (Cloud) it.next();

			//MOVE CLOUD
			cloud.moveX(cloud.getSpeed()*-1);
			
			//REMOVE IF OUTSIDE
			if(!cloud.isVisible()) {
				it.remove();
			}
		}
		//END CLOUDS
		
		
		
		//CORRECT NUMBER OF OBJECTS
		manageObjects();
	}
	
	public void paint(Graphics2D g) {
		
		//PAINT CLOUDS
		for(int i=0;i<clouds.size();i++){
			clouds.get(i).paint(g);
		}

		//PAINT PLATFORMS
		for(int i=0;i<platforms.size();i++){
			platforms.get(i).paint(g);
		}
		
	}

}
