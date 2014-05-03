package jumpypig;


import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class ObjectManager {
	
	private int NUMBER_OF_PLATFORMS;
	private int PLATFORM_SPEED;
	private int NUMBER_OF_CLOUDS;
	private int NUMBER_OF_BLOCKS;
	
	private ArrayList<GameObject> platforms;
	private ArrayList<GameObject> clouds;
	private ArrayList<GameObject> blocks;
	
	private Random rand;
	
	
	public ObjectManager() {
		//INIT.
		NUMBER_OF_PLATFORMS = 3;
		PLATFORM_SPEED = 2;
		NUMBER_OF_CLOUDS = 5;
		NUMBER_OF_BLOCKS = 0;
		
		platforms = new ArrayList<GameObject>();
		clouds = new ArrayList<GameObject>();
		blocks = new ArrayList<GameObject>();
		
		rand = new Random();
		
		//ADD STARTING OBJECTS
		initObjects();
	}
	
	public void setNumberOfPlatforms(int n) {
		NUMBER_OF_PLATFORMS = n;
	}
	
	public void setNumberOfClouds(int n) {
		NUMBER_OF_CLOUDS = n;
	}
	
	public void setNumberOfBlocks(int n) {
		NUMBER_OF_BLOCKS = n;
	}
	
	public void setPlatformSpeed(int n) {
		PLATFORM_SPEED = n;
	}

	
	/**
	 * Add start objects
	 */
	private void initObjects() {
		platforms.add(new Platform(0, 200, 5));
		
		clouds.add(new Cloud(600,20,70));
		
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
				//ADD PLATFORM AFTER LAST ONE
				Platform p = new Platform(platforms.get(platforms.size()-1).getX() + 300, 250, 1);
				platforms.add(p);
			}
			
		}
		
		//CHECK CLOUDS
		if(NUMBER_OF_CLOUDS > clouds.size()) {
			int add = NUMBER_OF_CLOUDS - clouds.size();
			
			//ADD MISSING OBJECTS
			for(int i=0;i<add;i++) {
				Cloud p = new Cloud(GameFrame.SCREENSIZE.width + rand.nextInt(100),rand.nextInt(100),rand.nextInt(50));
				clouds.add(p);
			}
			
		}
		
		//CHECK BLOCKS
		if(NUMBER_OF_BLOCKS > blocks.size()) {
			int add = NUMBER_OF_BLOCKS - blocks.size();
			
			//ADD MISSING OBJECTS
			for(int i=0;i<add;i++) {
				Block p = new Block(GameFrame.SCREENSIZE.width + rand.nextInt(100), rand.nextInt(100));
				blocks.add(p);
			}
			
		}
		
	}
	
	//UPDATE ALL OBJECTS
	public void update(PanelView gv) {

		//UPDATE PLATFORMS
		Iterator<GameObject> it = platforms.iterator();
		while(it.hasNext()) {
			Platform platform = (Platform) it.next();
			
			//MOVE PLATFORM
			platform.moveX(-1*PLATFORM_SPEED);
			
			if(!platform.isVisible()) {
				it.remove();
			}
			
			//IF NOT DEMO PLAY - CHECK FOR COLLISIONS WITH PLAYER
			if(gv instanceof GameView) {
				//TODO: FIX THIS
				//COLLISION WITH PLAYER
				if(platform.intersecting(((GameView) gv).getPlayer().getRect())) {
					((GameView) gv).getPlayer().setY(platform.getY()-((GameView) gv).getPlayer().getHeight());
					((GameView) gv).getPlayer().stand();
				}
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
		
		//UPDATE BLOCKS
		it = blocks.iterator();
		while(it.hasNext()) {
			Block block = (Block) it.next();
			
			//MOVE BLOCKS AT SAME SPEED AS PLATFORMS
			block.moveX(-1*PLATFORM_SPEED);
			
			if(!block.isVisible()) {
				it.remove();
			}
		}
		//END BLOCKS
	}
	
	public void paint(Graphics2D g) {
		
		//PAINT PLATFORMS
		for(int i=0;i<platforms.size();i++){
			platforms.get(i).paint(g);
		}
		
		//FIX THIS
		//PAINT CLOUDS
		for(int i=0;i<clouds.size();i++){
			clouds.get(i).paint(g);
		}
		
		
		//PAINT BLOCKS
		for(int i = 0; i < blocks.size(); i++) {
			blocks.get(i).paint(g);
		}
		
	}

}
