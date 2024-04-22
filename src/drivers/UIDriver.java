package drivers;

/**
 * This will test all of the classes in the UI package
 * @author Sean-Paul Brown
 *
 */
public class UIDriver implements DriverInterface {

    @Override
    public boolean test() {
        boolean allSuccess = true;
        if (!testHUD()) {
            allSuccess = false;
        }
        return allSuccess;
    }

    /**
     * This will test all of the method in the HUD class
     * @return true if all of the tests were passed, false if not
     */
    private boolean testHUD() {
        boolean allSuccess = true;
        return allSuccess;
    }

}
