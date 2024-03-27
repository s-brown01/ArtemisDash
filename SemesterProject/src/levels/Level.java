/**
 * Level Class
 * @author johnbotonakis
 * Every object instantiated from this class will be a level that the user can play throughout. 
 * This holds onto information such as level data and sprite indices to create a level.
 */
package levels;

public class Level {

    private int[][] lvlData;
    private Boolean completed = false;
    private Boolean hidden = true;

    public Level(int[][] lvlData) {
        this.lvlData = lvlData;
    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    public int[][] getLevelData() {
        return lvlData;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }
}
