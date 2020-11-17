import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Runner extends TestBase{


    @Test
    @TestData(testSetId="TS47",testId = "TC13")
    public void testName() throws NoSuchMethodException, SecurityException {
        reportTest();
        String ac = "Hello";
        String ex = "Hello";
        Assert.assertEquals(ac,ex);

    }


    @Test
    @TestData(testSetId="TS48",testId = "TC13")
    public void testName2() throws NoSuchMethodException, SecurityException {
        reportTest();
        String ac = "Hello";
        String ex = "Helloo";
        Assert.assertEquals(ac,ex);

    }

    }
