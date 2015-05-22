// File: src/templates/SampleTest.java
package gradletest3;

import org.junit.Test;
import static org.junit.Assert.*;
 
public class Gradle14Test {
 
    @Test
    public void testString() throws Exception {
        Thread.sleep(200);
        assertEquals("QE Autos", "QE" + " " + "Autos");
    }
}
