// File: src/templates/SampleTest.java
package gradletest9;

import org.junit.Test;
import static org.junit.Assert.*;
 
public class Gradle10Test {
 
    @Test
    public void testString() throws Exception {
        Thread.sleep(200);
        assertEquals("QE Autos", "QE" + " " + "Autos");
    }
}
