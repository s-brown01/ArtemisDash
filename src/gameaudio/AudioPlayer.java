package gameaudio;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Audio Player is a class that allows WAV files to be played through the use of Java
 * CLIPS. The songs are loaded into an array which finds the files in the correct folder,
 * and then converts those WAV files into a format that java can understand, saving them
 * into a new array. From this new array, they are able to be accessed and played at any
 * time.
 * 
 * @author John Botonakis
 */
public class AudioPlayer {
    /**
     * Main Menu music
     */
    public static int MENU_1 = 0;
    /**
     * Level 1 music
     */
    public static int ODDLEVELS = 1;
    /**
     * Game over music
     */
    public static int GAMEOVER = 2;
    /**
     * Level 2 music
     */
    public static int EVENLEVELS = 3;
    /**
     * World 1 Boss Music
     */
    public static int WORLD1BOSS = 4;

    /**
     * Death sound effect
     */
    public static int DEATH = 0;
    /**
     * Player Jumping sound effect
     */
    public static int JUMP = 1;
    /**
     * Player Fire sound effect
     */
    public static int FIRE = 2;
    /**
     * Button state - Hover sound effect
     */
    public static int BTN_HOVER = 3;
    /**
     * Button state - Confirm sound effect
     */
    public static int BTN_CONFIRM = 4;
    /**
     * Player Dash sound effect
     */
    public static int DASH = 5;

    /**
     * Enemy Attack sound effect
     */
    public static int ENEMY_ATTACK = 6;

    /**
     * Player Hurt sound effect
     */
    public static int PLAYER_HURT = 7;

    /**
     * Level complete music
     */
    public static int LEVELCOMPLETE = 8;
    /**
     * The integer value assigned to an effect or song for retrieval
     */
    private int currentID;
    /**
     * An array of Clip objects storing songs, labeled "music"
     */
    private Clip[] music;
    /**
     * An array of Clip objects storing effect sounds, labeled "fx"
     */
    private Clip[] fx;
    /**
     * A boolean value to mute songs. True is mute, False is unmute
     */
    private boolean songMute;
    /**
     * A boolean value to mute effects. True is mute, False is unmute
     */
    private boolean effectMute;
    /**
     * Sets the volume for every sound that will be played. 1f is 100% volume, 0f is 0%
     * volume.
     */
    private float volume = 1f;

    /**
     * The main constructor for the Audio player class. It loads in the Songs first, then the
     * Effects, then plays the Main Menu music as it is the first to load in when starting the
     * program.
     * 
     */
    public AudioPlayer() {
        loadSong();
        loadEffect();
        playSong(MENU_1);
    }

    /**
     * Loads the music audio into an array for easier fetching. The array stores these audio
     * files as Clip objects
     */
    public void loadSong() {
        String[] names = { "mm16", "W1L116", "game-over15", "W1L216", "W1L516" };
        music = new Clip[names.length];
        for (int i = 0; i < music.length; i++)
            music[i] = getSound(names[i]);
    }

    /**
     * Loads in the SFX audio into an array for easier fetching. The array stores these SFX
     * files as Clip objects
     */
    public void loadEffect() {
        String[] effectNames = { "playerdeath16", "jump16", "bowfire16", "buttonhover16", "buttonconfirm16", "dash16",
                "enemyattack16", "playerhurt16", "levelcomplete16" };
        fx = new Clip[effectNames.length];
        for (int i = 0; i < fx.length; i++)
            fx[i] = getSound(effectNames[i]);
    }

    /**
     * Loads in the audio file as a Clip object by first getting the resource as a URL file
     * 
     * @param name - Name of clip to load in
     * @return The audio file
     */
    private Clip getSound(String name) {
        URL url = getClass().getResource("/Audio/" + name + ".wav");
        AudioInputStream audio;

        try {
            audio = AudioSystem.getAudioInputStream(url);
            Clip c = AudioSystem.getClip();
            c.open(audio);
            return c;

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            return null;
        }

    }

    /**
     * Adjusts the volume for the songs
     * 
     * @param volume - The float volume level
     */
    public void setSongVolume(float volume) {
        this.volume = volume;
//        updateSongVolume();
    }

    /**
     * Stops the currently playing song
     */
    public void stopSong() {
        if (music[currentID].isActive())
            music[currentID].stop();
    }

    /**
     * Sets the Level music based on the current level index
     * 
     * @param lvlIndex - The index of the level that corresponds to the selected song
     */
    public void setLevelSong(int lvlIndex) {
        if (lvlIndex == 4) {
            playSong(WORLD1BOSS);
        } else {
            if (lvlIndex % 2 == 0)
                playSong(ODDLEVELS);
            else
                playSong(EVENLEVELS);
        }
    }

    /**
     * Stops whatever song is currently playing, and switches to the LEVELCOMPLETE song
     */
    public void lvlCompleted() {
        stopSong();
        playEffect(LEVELCOMPLETE);
    }

    /**
     * Plays the SFX for attacking
     */
    public void playAttackSound() {
        int start = 4;
        playEffect(start);
    }

    /**
     * Plays other sound effects
     * 
     * @param effect - The sound index that is to be played
     */
    public void playEffect(int effect) {
        fx[effect].setMicrosecondPosition(0);
        fx[effect].start();
    }

    /**
     * Plays the passed in Song, and will loop
     * 
     * @param song - The integer value of the song you wish to play
     */
    public void playSong(int song) {
        stopSong();

        currentID = song;
//        updateSongVolume();
        music[currentID].setMicrosecondPosition(0);
        if (song == GAMEOVER) {
            music[currentID].loop(0);
        } else {
            music[currentID].loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * Mutes all current Songs by passing in a boolean Control value of type MUTE.
     */
    public void toggleSongMute() {
        this.songMute = !songMute;
        for (Clip c : music) {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(songMute);
        }
    }

    /**
     * Mutes all sound effects by passing in a boolean control value of type MUTE.
     */
    public void toggleEffectMute() {
        this.effectMute = !effectMute;
        if (fx == null) {
            return;
        } else {
            for (Clip c : fx) {
                BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
                booleanControl.setValue(effectMute);
            }
        }
    }

    /**
     * Updates the currently playing song's volume as a float.
     */
    private void updateSongVolume() {
        FloatControl gainControl = (FloatControl) music[currentID].getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * volume) + gainControl.getMinimum();
        gainControl.setValue(gain);
    }

}
