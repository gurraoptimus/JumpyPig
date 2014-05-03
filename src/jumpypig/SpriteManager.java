package jumpypig;

import java.awt.Image;

import javax.swing.ImageIcon;

public final class SpriteManager {

		private static SpriteManager instance;
		
		//SPRITES URL
		private final String IMG_URL_BLOCK = "/Sprites/block.png";
		private final String IMG_URL_CLOUD_1 = "/Sprites/cloud_1.png";
		private final String IMG_URL_CLOUD_2 = "/Sprites/cloud_2.png";
		private final String IMG_URL_PLATFORM_LEFT = "/Sprites/platform_left.png";
		private final String IMG_URL_PLATFORM_RIGHT = "/Sprites/platform_right.png";
		private final String IMG_URL_PLATFORM_TOP = "/Sprites/platform_top.png";
		private final String IMG_URL_PLATFORM_BOTTOM = "/Sprites/platform_bottom.png";
		private final String IMG_URL_BACKGROUND = "/background.png";
		
		//SPRITES IMAGES
		public final Image IMAGE_BLOCK;
		public final Image IMAGE_CLOUD_1;
		public final Image IMAGE_CLOUD_2;
		public final Image IMAGE_PLATFORM_LEFT;
		public final Image IMAGE_PLATFORM_RIGHT;
		public final Image IMAGE_PLATFORM_TOP;
		public final Image IMAGE_PLATFORM_BOTTOM;
		public final Image IMAGE_BACKGROUND;
		
		private SpriteManager() {
			//INIT.
			IMAGE_BLOCK = new ImageIcon(this.getClass().getResource(IMG_URL_BLOCK)).getImage();
			IMAGE_CLOUD_1 = new ImageIcon(this.getClass().getResource(IMG_URL_CLOUD_1)).getImage();
			IMAGE_CLOUD_2 = new ImageIcon(this.getClass().getResource(IMG_URL_CLOUD_2)).getImage();
			IMAGE_PLATFORM_LEFT = new ImageIcon(this.getClass().getResource(IMG_URL_PLATFORM_LEFT)).getImage();
			IMAGE_PLATFORM_RIGHT = new ImageIcon(this.getClass().getResource(IMG_URL_PLATFORM_RIGHT)).getImage();
			IMAGE_PLATFORM_TOP = new ImageIcon(this.getClass().getResource(IMG_URL_PLATFORM_TOP)).getImage();
			IMAGE_PLATFORM_BOTTOM = new ImageIcon(this.getClass().getResource(IMG_URL_PLATFORM_BOTTOM)).getImage();
			IMAGE_BACKGROUND = new ImageIcon(this.getClass().getResource(IMG_URL_BACKGROUND)).getImage();
		}
		
		public static SpriteManager getInstance() {
			if(instance == null) {
				synchronized (SpriteManager.class) {
					if(instance == null) {
						instance = new SpriteManager();
					}
				}
			}
			return instance;
		}
	
}