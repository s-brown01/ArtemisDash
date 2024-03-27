package entities;

import static utils.Constants.BossConstants.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Boss extends Entity {

    protected int maxHealth;
    protected int currentHealth;
    protected int boss_type;
    protected int[] phases; // just a possible way we could set up phases (if we want those)
    private BufferedImage[][] animations;

    public Boss(float x, float y, int width, int height, int boss_type) {
        super(x, y, width, height);
        this.boss_type = boss_type;
        maxHealth = getMaxHealth(boss_type);
        this.currentHealth = maxHealth;
    }

    public void draw(Graphics g) {

    }

    public void update() {

    }

}
