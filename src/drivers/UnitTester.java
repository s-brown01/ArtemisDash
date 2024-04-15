package drivers;

/**
 * This is the main driver. Once the main method is called all other unit tests will be
 * done.
 * 
 * @author seanp
 */
public class UnitTester {

    /**
     * This is the main function of the class. Once this is run all drivers will be run.
     * 
     * @param args - NOT USED
     */
    public static void main(String[] args) {
        boolean allPassed = true;

        // test the entity classes
        EntityDriver entityDriver = new EntityDriver();
        if (!entityDriver.test()) {
            System.err.println("ENTITY DRIVER FAILED");
            allPassed = false;
        }

        /*
         * // test the input classes InputsDriver inputsDriver = new InputsDriver(); if
         * (!inputsDriver.test()) { System.err.println("INPUTS DRIVER FAILED"); allPassed = false;
         * }
         * 
         * // test the level classes LevelsDriver levelsDriver = new LevelsDriver(); if
         * (!levelsDriver.test()) { System.err.println("LEVELS DRIVER FAILED"); allPassed = false;
         * }
         * 
         * // test the projectile classes ProjectilesDriver projDriver = new ProjectilesDriver();
         * if (!projDriver.test()) { System.err.println("PROJECTILES DRIVER FAILED"); allPassed =
         * false; }
         * 
         * // test the states classes StatesDriver statesDriver = new StatesDriver(); if
         * (!statesDriver.test()) { System.err.println("STATES DRIVER FAILED"); allPassed = false;
         * }
         * 
         * // test the UI classes UIDriver uiDriver = new UIDriver(); if (!uiDriver.test()) {
         * System.err.println("UI DRIVER FAILED"); allPassed = false; }
         * 
         * // test the utils classes UtilsDriver utilsDriver = new UtilsDriver(); if
         * (!utilsDriver.test()) { System.err.println("UTILS DRIVER FAILED"); allPassed = false; }
         */

        // if every single test has passed display it
        if (allPassed) {
            System.out.println("All drivers passed successfully.");
        } else {
            // if not every test has passed print an error
            System.err.println("One or more drivers failed.");
        }
    }
}
