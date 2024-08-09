package page;
class Payload {
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

class TestScriptResult {
    String statusName;
    String actualEndDate;
    String actualResult;
}