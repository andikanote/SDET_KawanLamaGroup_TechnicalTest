import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import api.UserAPIKeywords

// 1. DECLARE VARIABLES ONLY ONCE
def username = "userbaru"  // This should be the ONLY declaration in this scope

// 2. Get user data (no need to redeclare 'response')
def response = UserAPIKeywords.getUser(username)

// 3. Verify response exists before processing
if(response == null) {
    println "Error: No response received"
    return
}

// 4. Process response (use existing 'response' variable)
println "Status: ${response.getStatusCode()}"
println "Response Body: ${response.getResponseText()}"

// 5. Optional: Parse JSON if needed
try {
    def userData = new groovy.json.JsonSlurper().parseText(response.getResponseText())
    println "Parsed username: ${userData.username}"
} catch(Exception e) {
    println "Failed to parse JSON: ${e.getMessage()}"
}