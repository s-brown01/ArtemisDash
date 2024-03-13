package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import levels.Level;
import states.Playing;
import utils.LoadSave;

public class EnemyManager {
    
    private Playing playing;
    private BufferedImage[][] skeletonAnis;
    private List<Skeleton> skeletonList = new ArrayList<>();
    
    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadImgs();
    }
    
    public void loadEnemies(Level level) {
        // skeletonList = level.getSkeletons();
    }

    private void loadImgs() {
        // SKELETONS
        // this is only to test the walking animation right now
        skeletonAnis = new BufferedImage[1][13];
        final BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.SKELETON_WALK);
        for (int j = 0; j < skeletonAnis.length; j++) {
            for (int i = 0; i < skeletonAnis[j].length; i++) {
                // the walking animation are each spaced 22x33 px, tho not all are same size
                skeletonAnis[j][i] = img.getSubimage(i * 22, j * 33, 22, 33);
            }
        }
    }
    
    public void draw(Graphics g) {
        for (Skeleton s : skeletonList) {
            // if the skeleton isn't active, skip it
            if (!s.isActive())
                continue;
            g.drawImage(skeletonAnis[s.getState()][s.getAniIndex()], 0, 0, 0, 0, null)
        }
    }
    
    public void update() {
        
    }

}