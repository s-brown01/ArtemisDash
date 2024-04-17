package drivers;

public class UIDriver implements DriverInterface {

    @Override
    public boolean test() {
        boolean allSuccess = true;
        if (!testHUD()) {
            allSuccess = false;
        }
        return allSuccess;
    }

    private boolean testHUD() {
        boolean allSuccess = true;
        return allSuccess;
    }

}
