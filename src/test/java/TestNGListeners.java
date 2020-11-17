import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.google.gson.JsonObject;
import com.rallydev.rest.RallyRestApi;
import com.rallydev.rest.request.CreateRequest;
import com.rallydev.rest.request.QueryRequest;
import com.rallydev.rest.response.CreateResponse;
import com.rallydev.rest.response.QueryResponse;
import com.rallydev.rest.util.Fetch;
import com.rallydev.rest.util.QueryFilter;

public class TestNGListeners implements ITestListener {
    RallyRestApi restApi = null;

    public void onFinish(ITestContext arg0) {
        // TODO Auto-generated method stub
    }

    public void onStart(ITestContext arg0) {
        // TODO Auto-generated method stub
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
        // TODO Auto-generated method stub
    }

    public void onTestFailure(ITestResult arg0) {
        // Connect to the Rally
        String baseURL="https://rally1.rallydev.com";
        String apiKey = "_8ohhxYLRSTCUVXVPPeCS2iYdgxWx05DnfJHXZaTas";

        try {
            restApi = new RallyRestApi(new URI(baseURL),apiKey);   // "_2RFDlhDSQsS4MjJloQGUoD2BCZFUDZsxP7OQbrJno");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        restApi.setApplicationName("testSet Update");
        QueryResponse res=null;
        try {
            res = restApi.query(new QueryRequest("workspace"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String workReference = res.getResults().get(0).getAsJsonObject().get("_ref").getAsString();
        String workspaceRef = workReference.split("https://rally1.rallydev.com/slm/webservice/v2.0")[1];

        //String workspaceRef = "/workspace/440927704160"; // "/workspace/138978207580";
        String setId = Reporter.getCurrentTestResult().getAttribute("TestSetID").toString();
        String testId = Reporter.getCurrentTestResult().getAttribute("TestID").toString();

        String tsRef = queryRequestFail(workspaceRef, "TestSet", setId);
        String tcRef = queryRequestFail(workspaceRef, "TestCase", testId);
        addTCResultFail(tsRef, tcRef);
    }

    // type: TestCase, TestSet,
    public String queryRequestFail(String workspaceRef, String type, String id) {
        QueryRequest request = new QueryRequest(type);
        request.setFetch(new Fetch("FormattedID", "Name"));
        request.setWorkspace(workspaceRef);
        request.setQueryFilter(new QueryFilter("FormattedID", "=", id));
        QueryResponse response = null;
        try {
            response = restApi.query(request);
            if (response.getTotalResultCount() == 0) {
                System.out.println("Cannot find tag: " + id);
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String ref = response.getResults().get(0).getAsJsonObject().get("_ref").getAsString();
        return ref;
    }

    // Add a Test Case Result in test set
    public void addTCResultFail(String tsRef, String tcRef) {
        JsonObject newTestCaseResult = new JsonObject();
        newTestCaseResult.addProperty("Verdict", "Fail");
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        String timestamp = sdf.format(date);
        newTestCaseResult.addProperty("Date", timestamp);
        newTestCaseResult.addProperty("Build", "Build number# " + timestamp);
        newTestCaseResult.addProperty("Description", "Async Page Test Run");
        newTestCaseResult.addProperty("TestSet", tsRef);
        newTestCaseResult.addProperty("TestCase", tcRef);

        CreateRequest createRequest = new CreateRequest("testcaseresult", newTestCaseResult);
        try {
            CreateResponse createResponse = restApi.create(createRequest);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            restApi.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    public void onTestSkipped(ITestResult arg0) {
        // TODO Auto-generated method stub
    }

    public void onTestStart(ITestResult arg0) {
        // TODO Auto-generated method stub
    }

    public void onTestSuccess(ITestResult arg0) {
        // Connect to the Rally
        String baseURL="https://rally1.rallydev.com";
        String apiKey = "_8ohhxYLRSTCUVXVPPeCS2iYdgxWx05DnfJHXZaTas";

        try {
            restApi = new RallyRestApi(new URI(baseURL),apiKey);   // "_2RFDlhDSQsS4MjJloQGUoD2BCZFUDZsxP7OQbrJno");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        restApi.setApplicationName("testSet Update");
        QueryResponse res=null;
        try {
            res = restApi.query(new QueryRequest("workspace"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String workReference = res.getResults().get(0).getAsJsonObject().get("_ref").getAsString();
        String workspaceRef = workReference.split("https://rally1.rallydev.com/slm/webservice/v2.0")[1];

        //String workspaceRef = "/workspace/440927704160"; // "/workspace/138978207580";
        String setId = Reporter.getCurrentTestResult().getAttribute("TestSetID").toString();
        String testId = Reporter.getCurrentTestResult().getAttribute("TestID").toString();

        String tsRef = queryRequestPass(workspaceRef, "TestSet", setId);
        String tcRef = queryRequestPass(workspaceRef, "TestCase", testId);
        addTCResultPass(tsRef, tcRef);
    }

    // type: TestCase, TestSet,
    public String queryRequestPass(String workspaceRef, String type, String id) {
        QueryRequest request = new QueryRequest(type);
        request.setFetch(new Fetch("FormattedID", "Name"));
        request.setWorkspace(workspaceRef);
        request.setQueryFilter(new QueryFilter("FormattedID", "=", id));
        QueryResponse response = null;
        try {
            response = restApi.query(request);
            if (response.getTotalResultCount() == 0) {
                System.out.println("Cannot find tag: " + id);
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String ref = response.getResults().get(0).getAsJsonObject().get("_ref").getAsString();
        return ref;
    }

    // Add a Test Case Result in test set
    public void addTCResultPass(String tsRef, String tcRef) {
        JsonObject newTestCaseResult = new JsonObject();
        newTestCaseResult.addProperty("Verdict", "Pass");
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        String timestamp = sdf.format(date);
        newTestCaseResult.addProperty("Date", timestamp);
        newTestCaseResult.addProperty("Build", "Build number# " + timestamp);
        newTestCaseResult.addProperty("Description", "Async Page Test Run");
        newTestCaseResult.addProperty("TestSet", tsRef);
        newTestCaseResult.addProperty("TestCase", tcRef);

        CreateRequest createRequest = new CreateRequest("testcaseresult", newTestCaseResult);
        try {
            CreateResponse createResponse = restApi.create(createRequest);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            restApi.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
