package jumpypig;

public final class SoundManager {
	
	private static SoundManager instance;
	
	private static final String GAME_MUSIC_URL = "/Audio/music.wav";
	private static final String START_SOUND_URL = "/Audio/start_sound.wav";
	private static final String SPLAT_SOUND_URL = "/Audio/splat.wav";
	private static final String DIE_SOUND_URL = "/Audio/dying.wav";
	

	public final Sound GAME_MUSIC;
	public final Sound START_SOUND;
	public final Sound SPLAT_SOUND;
	public final Sound DIE_SOUND;
	
	private SoundManager() {
		GAME_MUSIC = new Sound(GAME_MUSIC_URL);
		START_SOUND = new Sound(START_SOUND_URL);
		SPLAT_SOUND = new Sound(SPLAT_SOUND_URL);
		DIE_SOUND = new Sound(DIE_SOUND_URL);
	}
	
	public static SoundManager getInstance() {
		if(instance == null) {
			instance = new SoundManager();
		}
		return instance;
	}
	
	
}
