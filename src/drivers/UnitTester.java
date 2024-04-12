package drivers;

/**
 * This is the main driver. Once the main method is called all other unit tests will be done.
 * 
 * @author seanp
 */
public class UnitTester {
    
    /**
     * This is the main function of the class. Once this is run all drivers will be run.
     * @param args - NOT USED
     */
    public static void main(String[] args) {
        boolean allPassed = true;
        
        EntityDriver entityDriver = new EntityDriver();
        if (!entityDriver.test()) {
            System.err.println("ENTITY DRIVER FAILED");
            allPassed = false;
        }
        
        InputsDriver inputsDriver = new InputsDriver();
        if (!inputsDriver.test()) {
            System.err.println("INPUTS DRIVER FAILED");
            allPassed = false;
        }
        
        LevelsDriver levelsDriver = new LevelsDriver();
        if (!levelsDriver.test()) {
            System.err.println("LEVELS DRIVER FAILED");
            allPassed = false;
        }
        
        ProjectilesDriver projDriver = new ProjectilesDriver();
        if (!projDriver.test()) {
            System.err.println("PROJECTILES DRIVER FAILED");
            allPassed = false;
        }
        
        StatesDriver statesDriver = new StatesDriver();
        if (!statesDriver.test()) {
            System.err.println("STATES DRIVER FAILED");
            allPassed = false;
        }
        
        UIDriver uiDriver = new UIDriver();
        if (!uiDriver.test()) {
            System.err.println("UI DRIVER FAILED");
            allPassed = false;
        }
        
        UtilsDriver utilsDriver = new UtilsDriver();
        if (!utilsDriver.test()) {
            System.err.println("UTILS DRIVER FAILED");
            allPassed = false;
        }
        
        if (allPassed) {
            System.out.println("All drivers passed successfully.");
        } else {
            System.err.println("One or more drivers failed.");
        }
    }
}
