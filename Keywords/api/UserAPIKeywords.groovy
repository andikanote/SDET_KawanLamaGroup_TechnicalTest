package api

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.HttpBodyContent
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import groovy.json.JsonOutput
import com.kms.katalon.core.util.KeywordUtil

class UserAPIKeywords {
	static def createUser(Map userData) {
		TestObject request = findTestObject('Object Repository/API/UserCreationRequest')

		String requestBody = JsonOutput.toJson(userData)

		request.setBodyContent(new HttpTextBodyContent(requestBody))

		def response = WS.sendRequest(request)

		return response
	}


	static def getUser(String username) {
		try {
			// 1. Get request object
			TestObject request = findTestObject('Object Repository/API/GETUserRequest')

			// 2. Replace path parameter
			String endpoint = request.getRestUrl().replace('{username}', username)
			request.setRestUrl(endpoint)

			// 3. Debug output
			KeywordUtil.logInfo("Sending GET request to: ${endpoint}")

			// 4. Send request
			def response = WS.sendRequest(request)

			// 5. Debug response
			KeywordUtil.logInfo("Response status: ${response.getStatusCode()}")
			KeywordUtil.logInfo("Response body: ${response.getResponseText()}")

			return response
		} catch (Exception e) {
			KeywordUtil.markFailed("API call failed: ${e.getMessage()}")
			e.printStackTrace()
			return null
		}
	}
	
	static def updateUser(String username, Map userData) {
		// 1. Get request template
		TestObject request = findTestObject('Object Repository/API/UpdateUserRequest')
		
		// 2. Replace path parameter
		String endpoint = request.getRestUrl().replace('{username}', username)
		request.setRestUrl(endpoint)
		
		// 3. Set JSON body
		String requestBody = JsonOutput.toJson(userData)
		request.setBodyContent(new HttpTextBodyContent(requestBody))
		
		// 4. Send request
		return WS.sendRequest(request)
	}
	
	/**
	 * Generate random user data
	 * @return Map of user properties
	 */
	static Map generateRandomUserData() {
		def random = new Random()
		def timestamp = System.currentTimeMillis()
		
		return [
			"id": random.nextInt(10000) + 1,
			"username": "user_${timestamp}",
			"firstName": "First_${random.nextInt(1000)}",
			"lastName": "Last_${random.nextInt(1000)}",
			"email": "user_${timestamp}@test.com",
			"password": "P@ss_${timestamp}",
			"phone": "628${String.format("%09d", random.nextInt(1000000000))}",
			"userStatus": 1
		]
	}

}