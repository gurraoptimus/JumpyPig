package jumpypig;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

public class ObjectManager {
	
	private int NUMBER_OF_PLATFORMS;
	private int PLATFORM_SPEED;
	private int NUMBER_OF_CLOUDS;
	private int NUMBER_OF_BLOCKS;
	
	private ArrayList<GameObject> platforms;
	private ArrayList<GameObject> clouds;
	private ArrayList<GameObject> blocks;
	
	
	public ObjectManager() {
		//INIT.
		NUMBER_OF_PLATFORMS = 3;
		PLATFORM_SPEED = 5;
		NUMBER_OF_CLOUDS = 3;
		NUMBER_OF_BLOCKS = 0;
		
		platforms = new ArrayList<GameObject>();
		clouds = new ArrayList<GameObject>();
		blocks = new ArrayList<GameObject>();
		
		//ADD STARTING OBJECTS
		initObjects();
	}
	
	/**
	 * Add start objects
	 */
	private void initObjects() {
		platforms.add(new Platform(600, 200, 5));
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
				Platform p = new Platform(platforms.get(platforms.size()-1).getX() + 300, 250, 2);
				platforms.add(p);
			}
			
		}
		
		//CHECK CLOUDS
		if(NUMBER_OF_CLOUDS > clouds.size()) {
			int add = NUMBER_OF_CLOUDS - clouds.size();
			
			//ADD MISSING OBJECTS
			for(int i=0;i<add;i++) {
				Cloud p = new Cloud();
				//TODO: CONFIGURE CLOUD (POSITION, DISTANCE, SPEED etc.)
				clouds.add(p);
			}
			
		}
		
		//CHECK BLOCKS
		if(NUMBER_OF_BLOCKS > blocks.size()) {
			int add = NUMBER_OF_BLOCKS - blocks.size();
			
			//ADD MISSING OBJECTS
			for(int i=0;i<add;i++) {
				Block p = new Block();
				//TODO: CONFIGURE BLOCK (POSITION, SPEED etc.)
				blocks.add(p);
			}
			
		}
		
	}
	
	//UPDATE ALL OBJECTS
	public void update(GameView gv) {
		
		//UPDATE PLATFORMS
		Iterator<GameObject> it = platforms.iterator();
		while(it.hasNext()) {
			Platform platform = (Platform) it.next();
			
			//MOVE PLATFORM
			platform.moveX(-1*PLATFORM_SPEED);
			
			if(!platform.isVisible()) {
				it.remove();
			}
		}
		//END PLATFORMS
		
		//CORRECT NUMBER OF OBJECTS
		manageObjects();
		
	}
	
	public void paint(Graphics2D g) {
		
		//PAINT PLATFORMS
		for(GameObject platform : platforms) {
			platform.paint(g);
		}
		
		/*PAINT CLOUDS
		for(GameObject cloud : clouds) {
			cloud.paint(g);
		}
		
		//PAINT BLOCKS
		for(GameObject block : blocks) {
			block.paint(g);
		}*/
		
	}

}
