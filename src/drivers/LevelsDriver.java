package drivers;

public class LevelsDriver implements DriverInterface {

    @Override
    public boolean test() {
        boolean allSuccess = true;
        if (!testLevel()) {
            allSuccess = false;
        }
        if (!testLevelManager()) {
            allSuccess = false;
        }
        return allSuccess;
    }

    private boolean testLevel() {
        boolean allSuccess = true;
        return allSuccess;
    }
    
    private boolean testLevelManager() {
        boolean allSuccess = true;
        return allSuccess;        
    }

}
