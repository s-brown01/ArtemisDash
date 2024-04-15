/**
 * 
 */
package gameaudio;

import java.util.Random;

import javax.sound.sampled.Clip;

/**
 * 
 */
public class AudioPlayer {
    public static int MENU_1 = 0;
    public static int WORLD_1 = 1;
    public static int WORLD_2 = 2;
    public static int WORLD_3 = 3;

    public static int DEATH = 0;
    public static int JUMP = 1;
    public static int GAMEOVER = 2;
    public static int LEVELCOMPLETE = 3;
    public static int FIRE = 4;

    private Clip[] songs, effects;
    private int currentID;
    private float volume = 0.5f;
    private boolean songMute, effectMute;
    private Random rnd = new Random();

    public AudioPlayer() {
    }

    public void loadSong() {
    }

    public void loadEffect() {
    }

    private Clip getSound(String name) {
        return null;
    }
}
