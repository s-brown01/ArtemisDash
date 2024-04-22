package drivers;

/**
 * This is a very basic interface that all unit tests/drivers should follow. It has a main function test that will run all tests.
 * @author Sean-Paul Brown
 *
 */
public interface DriverInterface {

    /**
     * This will test all classes responsible for the specific driver.
     * @return true if all of the tests were passed. False if not.
     */
    public boolean test();
}
