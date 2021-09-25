package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utils.SeleniumMethods;

public class Locators extends SeleniumMethods{

	static WebElement element;

	
	//Test 1
	public static WebElement buttonAddElement() {
		//we use a condition to wait the element to be clickable
		element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='content']/div/button")));
		return element;
	}

	public static WebElement buttonDelete(int i) {
		//we use a condition to wait the element to be clickable and the number of this element "i"
		element = driver.findElement(By.xpath("//*[@id='elements']/button["+i+"]"));
		return element;
	}
	
	//------------------------------------------------------------------------------------------------------------------
	
	//Test 2
	public static WebElement box() {
		element = driver.findElement(By.xpath("//*[@id='hot-spot']"));
		return element;
	}
	
	
	//------------------------------------------------------------------------------------------------------------------
	
	//Test 3
	public static WebElement galleryButton() {
		element = driver.findElement(By.xpath("//*[@id='content']/div/ul/li[5]/a"));
		return element;
	}
	
	
	//------------------------------------------------------------------------------------------------------------------
	
	//Test 4
	public static WebElement downloadButton() {
		element = driver.findElement(By.xpath("//*[@id='content']/div/a"));
		return element;
	}
	
	
	//------------------------------------------------------------------------------------------------------------------
	
	//Test 5
	public static WebElement selectFileButton() {
		element = driver.findElement(By.xpath("//*[@id='file-upload']"));
		return element;
	}
	
	public static WebElement uploadButton() {
		element = driver.findElement(By.xpath("//*[@id='file-submit']"));
		return element;
	}
	
	public static WebElement fileUploadedText() {
		element = driver.findElement(By.xpath("//*[@id='content']/div/h3"));
		return element;
	}
	
	public static WebElement fileNameText() {
		element = driver.findElement(By.xpath("//*[@id='uploaded-files']"));
		return element;
	}

	
	//------------------------------------------------------------------------------------------------------------------
	
	//Test 6
	public static WebElement jsAlertButton() {
		element = driver.findElement(By.xpath("//*[@id='content']/div/ul/li[1]/button"));
		return element;
	}
	
	public static WebElement jsPromptButton() {
		element = driver.findElement(By.xpath("//*[@id='content']/div/ul/li[3]/button"));
		return element;
	}
	
	public static WebElement resultText() {
		element = driver.findElement(By.xpath("//*[@id='result']"));
		return element;
	}
		

}