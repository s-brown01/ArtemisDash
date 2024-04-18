package gameaudio;

import java.net.URL;
import java.io.IOException;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import main.Game;

/**
 * 
 */
public class AudioPlayer {
    public static int MENU_1 = 0;
    public static int W_1L_1 = 1;
    public static int GAMEOVER = 2;
    public static int W_1L_2 = 3;
//    public static int LEVELCOMPLETE = 4;
//    public static int W_1L_3 = 3;
//    public static int W_1L_4 = 4;
//    public static int W_1L_5 = 5;

    public static int DEATH = 0;
    public static int JUMP = 1;
    public static int FIRE = 2;
//    public static int BTN_HOVER = 5;
//    public static int BTN_CONFIRM = 6;

    private int currentID;
    private Clip[] music, fx;
    private Game game;
    private boolean songMute, effectMute;
    private Random rnd = new Random();
    private float volume = 0.8f;

    public AudioPlayer(Game game) {
        loadSong();
        loadEffect();
        playSong(MENU_1);
    }

    /**
     * Loads the music audio into an array for easier fetching. The array stores these audio
     * files as Clip objects
     */
    public void loadSong() {
        String[] names = { "mm", "W1L1", "game-over" };
        music = new Clip[names.length];
        for (int i = 0; i < music.length; i++)
            music[i] = getSound(names[i]);
    }

    /**
     * Loads in the SFX audio into an array for easier fetching. The array stores these SFX
     * files as Clip objects
     */
    public void loadEffect() {
        String[] effectNames = { "player_death", "jump", "bow_fire" };
        fx = new Clip[effectNames.length];
        for (int i = 0; i < fx.length; i++)
            fx[i] = getSound(effectNames[i]);

        updateEffectsVolume();
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

            e.printStackTrace();
        }

        return null;

    }

    /**
     * 
     * @param volume
     */
    public void setVolume(float volume) {
        this.volume = volume;
        updateSongVolume();
        updateEffectsVolume();
    }

    /**
     * 
     */
    public void stopSong() {
        if (music[currentID].isActive())
            music[currentID].stop();
    }

    /**
     * Sets the Level music based on the current level index
     * 
     * @param lvlIndex
     */
    public void setLevelSong(int lvlIndex) {
        if (lvlIndex % 2 == 0)
            playSong(W_1L_1);
        else
            playSong(W_1L_2);
    }

    /**
     * Stops whatever song is currently playing, and switches to the LEVELCOMPLETE song
     */
    public void lvlCompleted() {
        stopSong();
//        playEffect(LEVELCOMPLETE);
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
     * @param effect
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
        updateSongVolume();
        music[currentID].setMicrosecondPosition(0);
        music[currentID].loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * 
     */
    public void toggleSongMute() {
        this.songMute = !songMute;
        for (Clip c : music) {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(songMute);
        }
    }

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

    /**
     * Updates the currently queued SFX's volume as a float. This method changes the volume
     * for EVERY sound effect.
     */
    private void updateEffectsVolume() {
        for (Clip c : fx) {
            FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }

}
