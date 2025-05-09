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
import com.kms.katalon.core.testdata.ExcelData as ExcelData

// Get last row from test data
int getLastRow = findTestData('Data Files/dataTestCredentials').getRowNumbers()

WebUI.callTestCase(findTestCase('UI TEST/COMMON/OpenBrowser'), [:], FailureHandling.STOP_ON_FAILURE)
WebUI.maximizeWindow()
WebUI.click(findTestObject('Object Repository/PageObjctLoginPages/PageObjct_menu-toggle'))
WebUI.click(findTestObject('Object Repository/PageObjctLoginPages/PageObjct_button_menu_login'))

// Loop through all data rows (use either this or the commented version below for specific rows)
//for (int excelRow : (1..getLastRow)) { // for running all data
for (int excelRow : (1..1)) { // for running specific data, just change the number
    // Get test data for current row
    TestData dataLogin = findTestData('Data Files/dataTestCredentials')
    String strNo = dataLogin.getValue('NO', excelRow)
    String username = dataLogin.getValue('USERNAME', excelRow)
    String password = dataLogin.getValue('PASSWORD', excelRow)
	
    println("Processing data row"+strNo)

    // Attempt login with current credentials
    WebUI.setText(findTestObject('Object Repository/PageObjctLoginPages/PageObjct_field_username'), username, FailureHandling.OPTIONAL)
    WebUI.setText(findTestObject('Object Repository/PageObjctLoginPages/PageObjct_field_password'), password, FailureHandling.OPTIONAL)
    WebUI.click(findTestObject('Object Repository/PageObjctLoginPages/PageObjct_button_Login'), FailureHandling.OPTIONAL)

    println("Attempting login with username:" +username )

    // Check if login was successful by verifying dashboard element
    Boolean isDashboardPresent = WebUI.verifyElementPresent(findTestObject('Object Repository/PageObjctMakeAppointmentPages/PageObjct_Txth2_Make Appointment'), 
        3, FailureHandling.OPTIONAL)

    if (isDashboardPresent) {
        println("Login successful with user:" +username)

        // Logout after successful login
        WebUI.click(findTestObject('Object Repository/PageObjctLoginPages/PageObjct_menu-toggle'))
        WebUI.click(findTestObject('Object Repository/PageObjctMakeAppointmentPages/PageObjct_a_Logout'), FailureHandling.OPTIONAL)
        WebUI.click(findTestObject('Object Repository/PageObjctLoginPages/PageObjct_menu-toggle'))
        WebUI.click(findTestObject('Object Repository/PageObjctLoginPages/PageObjct_button_menu_login'))

        // Wait for login page to be ready for next attempt
        WebUI.waitForElementPresent(findTestObject('Object Repository/PageObjctLoginPages/PageObjct_button_menu_login'), 
            3) // Check if invalid credential message appears
    } else {
        boolean isInvalidCredential = WebUI.verifyElementPresent(findTestObject('Object Repository/PageObjctLoginPages/PageObjct_p_lgoinFailedTxt'), 
            3, FailureHandling.OPTIONAL)

        if (isInvalidCredential) {
            String errorMessage = WebUI.getText(findTestObject('Object Repository/PageObjctLoginPages/PageObjct_p_lgoinFailedTxt'), 
                FailureHandling.OPTIONAL)

            println("Login failed for user:" +username)
        } else {
            println("Login failed for user:" +username)
        }
    }
    
    WebUI.delay(1)
}

WebUI.closeBrowser()