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
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// Get last row from test data
int getLastRow = findTestData('Data Files/dataTestAppointment').getRowNumbers()

WebUI.callTestCase(findTestCase('UI TEST/COMMON/OpenBrowser'), [:], FailureHandling.STOP_ON_FAILURE)
WebUI.maximizeWindow()
WebUI.click(findTestObject('Object Repository/PageObjctLoginPages/PageObjct_menu-toggle'))
WebUI.click(findTestObject('Object Repository/PageObjctLoginPages/PageObjct_button_menu_login'))

// Loop through all data rows (use either this or the commented version below for specific rows)
//for (int excelRow : (1..getLastRow)) { // for running all data
for (int excelRow : (1..getLastRow)) { // for running specific data, just change the number
	// Get test data for current row
	TestData dataAppointment = findTestData('Data Files/dataTestAppointment')
	String strNo = dataAppointment.getValue('NO', excelRow)
	String username = dataAppointment.getValue('USERNAME', excelRow)
	String password = dataAppointment.getValue('PASSWORD', excelRow)
	String facility = dataAppointment.getValue('FACILITY', excelRow)
	String applyForHospital = dataAppointment.getValue('APPLY FOR HOSTPITAL', excelRow)
	String healthCareProgram = dataAppointment.getValue('HEALTHCARE PROGRAM', excelRow)
	
	WebUI.setText(findTestObject('Object Repository/PageObjctLoginPages/PageObjct_field_username'), username, FailureHandling.OPTIONAL)
	WebUI.setText(findTestObject('Object Repository/PageObjctLoginPages/PageObjct_field_password'), password, FailureHandling.OPTIONAL)
	WebUI.click(findTestObject('Object Repository/PageObjctLoginPages/PageObjct_button_Login'), FailureHandling.OPTIONAL)
	WebUI.verifyElementPresent(findTestObject('Object Repository/PageObjctMakeAppointmentPages/PageObjct_Txth2_Make Appointment'),
		3, FailureHandling.OPTIONAL)
	
	if (facility == 'Hongkong CURA Healthcare Center') {
		WebUI.selectOptionByValue(findTestObject('PageObjctMakeAppointmentPages/PageObject_OptionValueFacility'), 'Hongkong CURA Healthcare Center', true)
	}
	else if (facility == 'Tokyo CURA Healthcare Center'){
		WebUI.selectOptionByValue(findTestObject('PageObjctMakeAppointmentPages/PageObject_OptionValueFacility'), 'Tokyo CURA Healthcare Center', true)
	}
	else if (facility == 'Seoul CURA Healthcare Center'){
		WebUI.selectOptionByValue(findTestObject('PageObjctMakeAppointmentPages/PageObject_OptionValueFacility'), 'Seoul CURA Healthcare Center', true)
	}
	
	if (applyForHospital == 'YES') {
		WebUI.click(findTestObject('PageObjctMakeAppointmentPages/label_Apply for hospital readmission'))
		
	}
	else if (applyForHospital == 'NO'){
		println("If you choose no, there is no need to click")
	}
	
	if (healthCareProgram == 'Medicare') {
		WebUI.click(findTestObject('PageObjctMakeAppointmentPages/input_Medicare_programs'))
	}
	else if (healthCareProgram == 'Medicaid'){
		WebUI.click(findTestObject('PageObjctMakeAppointmentPages/input_Medicaid_programs'))
	}
	else if (healthCareProgram == 'None'){
		WebUI.click(findTestObject('PageObjctMakeAppointmentPages/input_None_programs'))
	}
	
	LocalDate dateNowPlus1 = LocalDate.now().plusDays(1)
	String formattedDate = dateNowPlus1.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
	WebUI.setText(findTestObject('Object Repository/PageObjctMakeAppointmentPages/input_Visit Date (Required)_visit_date'), formattedDate)
	
	WebUI.setText(findTestObject('PageObjctMakeAppointmentPages/textarea_Comment_comment'), 'Checkup Jantung')
	WebUI.click(findTestObject('PageObjctMakeAppointmentPages/button_Book Appointment'))
	
	WebUI.click(findTestObject('Object Repository/PageObjctMakeAppointmentPages/a_Go to Homepage'))
	WebUI.click(findTestObject('Object Repository/PageObjctLoginPages/PageObjct_menu-toggle'))
	WebUI.click(findTestObject('Object Repository/PageObjctMakeAppointmentPages/PageObjct_a_Logout'), FailureHandling.OPTIONAL)
	WebUI.click(findTestObject('Object Repository/PageObjctLoginPages/PageObjct_menu-toggle'))
	WebUI.click(findTestObject('Object Repository/PageObjctLoginPages/PageObjct_button_menu_login'))
}
WebUI.closeBrowser()

