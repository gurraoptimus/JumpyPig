package jumpypig;

public final class SoundManager {
	
	private static SoundManager instance;
	
	private static final String GAME_MUSIC_URL = "/Audio/music_menu.wav";
	private static final String START_SOUND_URL = "/Audio/start_sound.wav";
	

	public final Sound GAME_MUSIC;
	public final Sound START_SOUND;
	
	private SoundManager() {
		GAME_MUSIC = new Sound(GAME_MUSIC_URL);
		START_SOUND = new Sound(START_SOUND_URL);
	}
	
	public static SoundManager getInstance() {
		if(instance == null) {
			instance = new SoundManager();
		}
		return instance;
	}
	
	
}
