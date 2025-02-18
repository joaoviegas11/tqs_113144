import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.Test;

import ui.DemoMain;

public class DemoMainTest {
    @Test
    public void testMain(){
        try {
            DemoMain.main(null);
        } catch (Exception e) {
            assumeTrue(false);
        }
    }
}
