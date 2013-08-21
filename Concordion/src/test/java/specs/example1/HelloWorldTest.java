package specs.example1;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.Before;
import org.junit.runner.RunWith;
import com.example.HelloWorld;

/* Run this class as a JUnit test. */

@RunWith(ConcordionRunner.class)
public class HelloWorldTest {
    
    private HelloWorld helloWorld;

    @Before
    public void setUp() {
        helloWorld = new HelloWorld();
    }
    
    public String getGreeting() {
        return helloWorld.getMessage();
    }
}
