package drivers;

public class UIDriver implements DriverInterface {

    @Override
    public boolean test() {
        boolean allSuccess = true;
        testHUD();
        return allSuccess;
    }

    private void testHUD() {
        
    }

}
