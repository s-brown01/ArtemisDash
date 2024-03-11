package levels;

/**
 */
public class Level {
    private int[][] lvlData;

    //Level constructor
    public Level(int[][] lvlData) {
        this.lvlData = lvlData;
    }

    /**
     * Creates levels through sprite indexing
     */
    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    public int[][] getLevelData() {
        return lvlData;
    }
}
