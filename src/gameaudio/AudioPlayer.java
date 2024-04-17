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
    public static int W_1L_2 = 2;
    public static int W_1L_3 = 3;
    public static int W_1L_4 = 4;
    public static int W_1L_5 = 5;

    public static int DEATH = 0;
    public static int JUMP = 1;
    public static int GAMEOVER = 2;
    public static int LEVELCOMPLETE = 3;
    public static int FIRE = 4;
    public static int BTN_HOVER = 5;
    public static int BTN_CONFIRM = 6;

    private Clip[] music, fx;
    private int currentID;
    private float volume = 0.5f;
    private boolean songMute, effectMute;
    private Random rnd = new Random();
    private Game game;

    public AudioPlayer(Game game) {
        loadSong();
        playSong(MENU_1);
//        loadEffect();
    }

    public void loadSong() {
        String[] names = { "mm", "W1L1" };
        music = new Clip[names.length];
        for (int i = 0; i < music.length; i++)
            music[i] = getSound(names[i]);
    }

//    public void loadEffect() {
//        String[] effectNames = { "player_death", "jump", "game-over", "level-complete", "bow_fire","button_hover","button_confirm" };
//        fx = new Clip[effectNames.length];
//        for (int i = 0; i < fx.length; i++)
//            fx[i] = getSound(effectNames[i]);
//        
//        updateEffectsVolume();
//    }

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

    public void setVolume(float volume) {
        this.volume = volume;
        updateSongVolume();
        updateEffectsVolume();
    }

    public void stopSong() {
        if (music[currentID].isActive())
            music[currentID].stop();
    }

    public void setLevelSong(int lvlIndex) {
        if (lvlIndex % 2 == 0)
            playSong(W_1L_1);
        else
            playSong(W_1L_2);
    }

    public void lvlCompleted() {
        stopSong();
        playEffect(LEVELCOMPLETE);
    }

    public void playAttackSound() {
        int start = 4;
//        start += rnd.nextInt(3);
        playEffect(start);
    }

    public void playEffect(int effect) {
        fx[effect].setMicrosecondPosition(0);
        fx[effect].start();
    }

    public void playSong(int song) {
        stopSong();

        currentID = song;
        updateSongVolume();
        music[currentID].setMicrosecondPosition(0);
        music[currentID].loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void toggleSongMute() {
        this.songMute = !songMute;
        for (Clip c : music) {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(songMute);
        }
    }

    public void toggleEffectMute() {
        this.effectMute = !effectMute;
        for (Clip c : fx) {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(effectMute);
        }
        if (!effectMute)
            playEffect(JUMP);
    }

    private void updateSongVolume() {

        FloatControl gainControl = (FloatControl) music[currentID].getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * volume) + gainControl.getMinimum();
        gainControl.setValue(gain);

    }

    private void updateEffectsVolume() {
        for (Clip c : fx) {
            FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }

}
