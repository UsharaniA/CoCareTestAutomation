package utility;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import constants.EnvConstants;

public class ZephyrScaleAPI {
	

    public static class Payload {
        String projectKey;
        String statusName;
        String testCaseKey;
        String testCycleKey;
        TestScriptResult[] testScriptResults;
        String environmentName;
        String actualEndDate;
        long executionTime;
        String executedById;
        String assignedToId;
        String comment;
    }

    public static  class TestScriptResult {
        String statusName;
        String actualEndDate;
        String actualResult;
    }

    private static final String jira_URL = EnvHelper.getValue(EnvConstants.jiraurl);
    private static final String apiToken = EnvHelper.getValue(EnvConstants.jiratoken);
//    private static final String apiToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjb250ZXh0Ijp7ImJhc2VVcmwiOiJodHRwczovL3Zpbmtzb2Z0d2FyZS5hdGxhc3NpYW4ubmV0IiwidXNlciI6eyJhY2NvdW50SWQiOiI2MDI2Nzk5NWI5ZDAwMzAwNzA5YjU4MmEifX0sImlzcyI6ImNvbS5rYW5vYWgudGVzdC1tYW5hZ2VyIiwic3ViIjoiNzQ2NGE5MTAtM2MzYi0zZGM0LWFiZDItMWUzM2FiMGUxYzQ5IiwiZXhwIjoxNzUyOTE4MjY2LCJpYXQiOjE3MjEzODIyNjZ9.8HCt56kPgGXL-Ot7gtp338DwDynZ6a7OxGHD1384iuw";
    private static final String jiratestcycle = EnvHelper.getValue(EnvConstants.jiratestcycle);
    private static final String jiraprojectid = EnvHelper.getValue(EnvConstants.jiraprojectid);


    public static void updateTestResult(String testcaseid, String status) throws IOException {
        String createTestCaseUrl = jira_URL + "/testexecutions";
    	 // Create the payload
        Payload payload = createPayload(testcaseid, status);

        // Convert the payload to JSON
        Gson gson = new Gson();
        String jsonPayload = gson.toJson(payload);
        System.out.println("jsonPayload Code: " + jsonPayload);
    	 // Create the HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Create the HttpRequest
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(createTestCaseUrl)) // Example endpoint
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiToken)
                .POST(BodyPublishers.ofString(jsonPayload))
                .build();

     // Send the request and handle the response
        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            System.out.println("Response Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        
        
      
    }

    
    public static void attachFileToExecution() throws IOException {
//        String attachFileUrl = jira_URL + "/testresult/" + testExecutionKey + "/attachments";
        String apiUrl = "https://api.zephyrscale.smartbear.com/v2/testcases/CC-T24/attachments"; // Replace {testCaseId} with actual test case ID
        // Create the HttpClient
        HttpClient client = HttpClient.newHttpClient();
       // Create the File to upload
        Path filePath = Paths.get("D:\\Eclipse\\cocare\\allure-report\\index.html");
        File file = filePath.toFile();
//      /  File file = new File("D:\\Eclipse\\cocare\\allure-report");
        // Create the HttpRequest
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Authorization", "Bearer " + apiToken)
                .header("Content-Type", "multipart/form-data")
                .PUT(HttpRequest.BodyPublishers.ofFile(file.toPath()))
                .build();

        // Send the request and handle the response
        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            System.out.println("Response Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    
    
    
    
    public static Payload createPayload(String testcaseid, String status) {
        Payload payload = new Payload();
        payload.projectKey = jiraprojectid;
        payload.statusName = status;
        payload.testCaseKey = testcaseid;
        payload.testCycleKey = jiratestcycle; 

        payload.comment = "The evidence are stored in the path D:\\Eclipse\\cocare\\allure-report";
        TestScriptResult result = new TestScriptResult();
        result.statusName = status;
        result.actualResult = "User logged in successfully";
        payload.testScriptResults = new TestScriptResult[]{result};

        return payload;
    }

    
    
    
    public static void createTestCase() throws IOException {
        String createTestCaseUrl = jira_URL + "/testcases";
  

        System.out.println("createTestCaseUrl: " + createTestCaseUrl);
        String projectKey=jiraprojectid;
        String testCaseName="Consolidated Automation Result";

        // Create the payload
        Map<String, Object> payload = new HashMap<>();
        payload.put("projectKey", projectKey);
        payload.put("name", testCaseName);

        // Convert the payload to JSON
        Gson gson = new Gson();
        String jsonPayload = gson.toJson(payload);
        System.out.println("jsonPayload: " + jsonPayload);

        // Create the HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Create the HttpRequest
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(createTestCaseUrl))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiToken)
                .POST(BodyPublishers.ofString(jsonPayload))
                .build();
        
        
        

        // Send the request and handle the response
        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            System.out.println("Response Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
            Map<String, Object> responseMap = gson.fromJson(response.body(), Map.class);
             String testCaseKey = responseMap.get("key").toString();
            System.out.println("Created test case with key: " + testCaseKey);
//            associateTestCaseWithCycle(jiratestcycle,testCaseKey);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
//        return null;
        
    }

    public static void associateTestCaseWithCycle (String testCycleKey, String testCaseKey) throws IOException {
        String addTestCaseToExecutionUrl = jira_URL + "/testresult";

        // Create the payload
        Map<String, String> payload = new HashMap<>();
        payload.put("cycleId", testCycleKey);
        payload.put("testCaseKey", testCaseKey);
        payload.put("projectId", jiraprojectid);

        // Convert the payload to JSON
        Gson gson = new Gson();
        String jsonPayload = gson.toJson(payload);
        System.out.println("jsonPayload: " + jsonPayload);

        // Create the HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Create the HttpRequest
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(addTestCaseToExecutionUrl))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiToken)
                .POST(BodyPublishers.ofString(jsonPayload))
                .build();

        // Send the request and handle the response
        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            System.out.println("Response Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
  
}
    
}
