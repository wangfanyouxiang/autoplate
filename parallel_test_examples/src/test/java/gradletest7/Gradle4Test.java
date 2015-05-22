// File: src/templates/SampleTest.java
package gradletest7;

import org.junit.Test;
import static org.junit.Assert.*;
 
public class Gradle4Test {
 
    @Test
    public void testString() throws Exception {
        Thread.sleep(200);
        assertEquals("QE Autos", "QE" + " " + "Autos");
    }
}
