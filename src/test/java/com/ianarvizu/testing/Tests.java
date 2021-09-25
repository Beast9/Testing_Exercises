package com.ianarvizu.testing;

import java.io.File;

import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.Locators;
import utils.SeleniumMethods;

/**
 * 
 * @author ian.arvizu
 *
 * INSTRUCCIONS:
 * 		Script run from testng.xml file as TestNG suite.
 * 		Every test runs in chrome and firefox.
 * 		Test results are storage in "Testing_Exercises\src\ExtentReport\TestReport.html".
 *  
 */

public class Tests extends SeleniumMethods {

	/* TEST 1 Add/Remove Elements
	 * 
	 * Description: Add and elements clicking on the add button 20 times then remove each element one by one.
	 * Steps:
	 * 		Navigate to URL
	 * 		Create the test report Add/Remove Elements with its browser name
	 * 		Create a bucle for add the 20 elements
	 * 		Add the element clicking the button "ADD ELEMENT"
	 * 		Validate the Element was added
	 *		Create a bucle for remove the 20 elements
	 *		Remove Element clicking one bye one
	 *		Validate the Element was removed
	 * 
	 * @param browserType
	 * @throws Exception
	*/
	@Parameters({ "BrowserType" })
	@Test(description = "Add/Remove Elements", enabled = true, priority = 1)
	public static void AddRemoveElements(String browserType) throws Exception {
		
		driver.get("https://ss-testing-automated-exercise.herokuapp.com/add_remove_elements/");
		test = extent.createTest( browserType + " - Add/Remove Elements");
		for (int i = 1; i < 21; i++) {
			Locators.buttonAddElement().click();
			//for view purpose only
			Thread.sleep(100);
			if ( Locators.buttonDelete(i).isDisplayed() ) {
				test.pass("Button " + i + " Added");
				captureScreenshot(driver, "Button " + i + " Added");
			}
		}
		
		for (int i = 20; i > 0; i--) {
			Locators.buttonDelete(i).click();
			//for view purpose only
			Thread.sleep(100);
			try {
				Locators.buttonDelete(i).isDisplayed();
			} catch (org.openqa.selenium.NoSuchElementException e) {
				test.pass("Button " + i + " Deleted");
				captureScreenshot(driver, "Button " + i + " Deleted");
		    }
		}
	}
	
	
	/* TEST 2 Context Menu
	 * 
	 * Description: Right click on the box and validate the text thats appears on the alert box, 
	 * 				close the alert box and validate that its not displayed.
	 * Steps:
	 * 		Navigate to URL
	 * 		Create the test report Context Menu with its browser name
	 * 		Right click on the box
	 * 		Switch to the alert message and validate the text "You selected a context menu"
	 * 		Switch to the alert message ang click on accept button
	 *		Validate that the alert message is not displayed
	 *
	 * @param browserType
	 * @throws Exception
	*/
	@Parameters({ "BrowserType" })
	@Test(description = "Context Menu", enabled = true, priority = 2)
	public static void ContextMenu(String browserType) throws Exception {

		//navigate to this URL
		driver.get("https://ss-testing-automated-exercise.herokuapp.com/context_menu");
		test = extent.createTest( browserType + " - Context Menu");
		Actions actions = new Actions(driver);
		actions.contextClick(Locators.box()).perform();
		if ( driver.switchTo().alert().getText().equals("You selected a context menu") ) {
			//for view purpose only
			Thread.sleep(5000);
			test.pass("Alert Text: " + driver.switchTo().alert().getText());
			driver.switchTo().alert().accept();
		}
		else 
			test.fail("Alert Text: " + driver.switchTo().alert().getText());
		
		try {
			driver.switchTo().alert();
		} catch (org.openqa.selenium.NoAlertPresentException e) {
			test.pass("Alert Close");
	    }
	}
	
	
	/* TEST 3 Disappearing Elements
	 * 
	 * Description: Reload the page until the gallery button is loaded then reload the page until the button is not loaded.
	 * Steps:
	 * 		Navigate to URL
	 * 		Create the test report Disappearing Elements with its browser name
	 * 		Validate if the gallery button is deplayed
	 * 		if the gallery button is not displayed reload the page 
	 * 		Validate if the gallery button is not deplayed
	 * 		if the gallery button is displayed reload the page 
	 *
	 * @param browserType
	 * @throws Exception
	*/
	@Parameters({ "BrowserType" })
	@Test(description = "Disappearing Elements", enabled = true, priority = 3)
	public static void DisappearingElements(String browserType) throws Exception {
		driver.get("https://ss-testing-automated-exercise.herokuapp.com/disappearing_elements");
		test = extent.createTest( browserType + " - Disappearing Elements");
		boolean displayed = false;
		do{
			try {
				if ( Locators.galleryButton().isDisplayed() ) {
					test.pass("Gallery Button is Displayed");
					captureScreenshot(driver, "Gallery Button Displayed");
				}
				displayed = true;
			} catch (org.openqa.selenium.NoSuchElementException e) {
				driver.navigate().refresh();
		    }
		} while(!displayed);
		
		do{
			try {
				if ( Locators.galleryButton().isDisplayed() ) 
					driver.navigate().refresh();
			} catch (org.openqa.selenium.NoSuchElementException e) {
				displayed = false;
				test.pass("Gallery Button is NOT Displayed");
				captureScreenshot(driver, "Gallery Button not Displayed");
		    }
		} while(displayed);
	}
	
	
	/* TEST 4 File Download
	 * 
	 * Description: Download the file text and validate the file has been dowloaded.
	 * Steps:
	 * 		Navigate to URL
	 * 		Create the test report File Download with its browser name
	 * 		Click on the "some-file.txt" text
	 * 		Go to the dowload path files 
	 * 		Validate if the file "some-file.txt" is in the directory
	 *
	 * @param browserType
	 * @throws Exception
	*/
	@Parameters({ "BrowserType" })
	@Test(description = "File Download", enabled = true, priority = 4)
	public static void FileDownload(String browserType) throws Exception {
		driver.get("https://ss-testing-automated-exercise.herokuapp.com/download");
		test = extent.createTest( browserType + " - File Download");
		Locators.downloadButton().click();
		//for view purpose only
		Thread.sleep(5000);
		File dir = new File(System.getProperty("user.home")+"/Downloads");
		File[] dirContents = dir.listFiles();
		for (int i = 0; i < dirContents.length; i++) {
			if (dirContents[i].getName().equals("some-file.txt")) {
				test.pass("File download succesfully");
				dirContents[i].delete();
			}
		}
	}
	
	
	/* TEST 5 File Upload
	 * 
	 * Description: Upload the file "test" and validate if the file is uploaded.
	 * Steps:
	 * 		Navigate to URL
	 * 		Create the test report File Upload with its browser name
	 * 		Send file to the locator upload file
	 * 		Click on the button to upload the file
	 * 		Validate the text "File Uploaded!" is displayed
	 *
	 * @param browserType
	 * @throws Exception
	*/
	@Parameters({ "BrowserType" })
	@Test(description = "File Upload", enabled = true, priority = 5)
	public static void FileUpload(String browserType) throws Exception {
		driver.get("https://ss-testing-automated-exercise.herokuapp.com/upload");
		test = extent.createTest( browserType + " - File Upload");
		Locators.selectFileButton().sendKeys(System.getProperty("user.dir") + "\\src\\test\\resources\\data\\test.txt");
		// for view purpose only
		Thread.sleep(3000);
		Locators.uploadButton().click();
		if (Locators.fileUploadedText().getText().equalsIgnoreCase("File Uploaded!")) {
			test.pass("File uploaded succesfully");
			captureScreenshot(driver, "File uploaded succesfully");
		} else {
			test.pass("File uploaded fail");
			captureScreenshot(driver, "File uploaded fail");
		}     
	}
	
	
	/* TEST 6 JavaScript Alerts
	 * 
	 * Description: Click on the js Alert button to open the JS alert and validate the text when the alert closed.
	 * 				Click on the js Prompt button to open the JS Alert and fill the text with "testing" and validate the text when the alert closed.
	 * Steps:
	 * 		Navigate to URL
	 * 		Create the test report JavaScript Alerts with its browser name
	 * 		Click on the js Alert button
	 * 		Switch to the alert box and click on accept button
	 * 		Validate the message "You successfuly clicked an alert"
	 * 		Click on the js Prompt button
	 * 		Switch to the alert box and fill the text "testing"
	 * 		Click on accept button
	 * 		Validate the message "You entered: testing"
	 *
	 * @param browserType
	 * @throws Exception
	*/
	@Parameters({ "BrowserType" })
	@Test(description = "JavaScript Alerts", enabled = true, priority = 6)
	public static void JavaScriptAlerts(String browserType) throws Exception {
		driver.get("https://ss-testing-automated-exercise.herokuapp.com/javascript_alerts");
		test = extent.createTest( browserType + " - JavaScript Alerts");
		Locators.jsAlertButton().click();
		driver.switchTo().alert().accept();
		if(Locators.resultText().getText().equals("You successfuly clicked an alert")) {
			test.pass("You successfuly clicked an alert");
			captureScreenshot(driver, "Text is Displayed");
		} else {
			test.fail("text not displayed");
			captureScreenshot(driver, "Text not Displayed");
		}
		Locators.jsPromptButton().click();
		driver.switchTo().alert().sendKeys("testing");
		driver.switchTo().alert().accept();
		if(Locators.resultText().getText().equals("You entered: testing")) {
			test.pass("You entered: testing");
			captureScreenshot(driver, "Text is Displayed");
		} else {
			test.fail("text not displayed");
			captureScreenshot(driver, "Text not Displayed");
		}
			
	}

}
