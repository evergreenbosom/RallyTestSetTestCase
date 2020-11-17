import java.lang.reflect.Method;

import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;

public class TestBase {
    String methodName;

    @BeforeMethod
    public void before(Method method) {
        methodName = method.getName();
    }

    public void reportTest() throws NoSuchMethodException, SecurityException {
        Method testMethod = getClass().getMethod(methodName);
        String testId = testMethod.getAnnotation(TestData.class).testId();
        String testSetId = testMethod.getAnnotation(TestData.class).testSetId();
        Reporter.getCurrentTestResult().setAttribute("TestID", testId);
        Reporter.getCurrentTestResult().setAttribute("TestSetID", testSetId);
    }
}