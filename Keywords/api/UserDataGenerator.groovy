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

import internal.GlobalVariable

import java.util.Random
import java.text.SimpleDateFormat
import groovy.json.JsonOutput

class UserDataGenerator {
	// Random generators
	private static Random random = new Random()
	private static String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())

	// Generate completely random user data
	static Map generateRandomUser() {
		return [
			"id": getRandomNumber(10000),
			"username": "user_${timestamp}_${getRandomNumber(100)}",
			"firstName": "First_${getRandomString(8)}",
			"lastName": "Last_${getRandomString(8)}",
			"email": "user_${timestamp}@test.com",
			"password": "P@ssw0rd_${getRandomNumber(1000)}",
			"phone": generateRandomPhoneNumber(),
			"userStatus": 1
		]
	}

	// Generate random user with optional overrides
	static Map generateRandomUser(Map overrides) {
		def defaultData = generateRandomUser()
		return defaultData + overrides // Override with custom values
	}

	// Helper: Random number generator
	private static int getRandomNumber(int max) {
		return random.nextInt(max) + 1
	}

	// Helper: Random string generator (letters only)
	private static String getRandomString(int length) {
		def chars = ('a'..'z').join('')
		def randomStr = new StringBuilder()
		for (i in 0..<length) {
			randomStr.append(chars.charAt(random.nextInt(chars.length())))
		}
		return randomStr.toString()
	}

	// Helper: Generate valid Indonesia phone number
	private static String generateRandomPhoneNumber() {
		return "628" + String.format("%09d", random.nextInt(1000000000))
	}
}
