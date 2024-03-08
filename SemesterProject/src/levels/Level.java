package levels;

/**
 */
public class Level {
    private int[][] lvlData;

    public Level(int[][] lvlData) {
        this.lvlData = lvlData;
    }

    /**
     * Creates levels through sprite indexing
     */
    public int intGetSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }
}
