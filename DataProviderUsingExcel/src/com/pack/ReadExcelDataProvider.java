package com.pack;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcelDataProvider {
	public WebDriver driver;
	public WebDriverWait wait;
	String appURL = "file:///D:/AdminLTE/index.html";
	
	//Locators
	private By byEmail = By.id("email");
	private By byPassword = By.id("password");
	private By bySubmit = By.xpath("//*[@id='form']/div[3]/div/button");
	private By byError = By.xpath("html/body/div[1]/div[2]/form/div[1]/div");
	
	@BeforeClass
	public void testSetup() {
		System.setProperty("webdriver.gecko.driver", "geckodriver64bit.exe");
		driver=new FirefoxDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, 20);
	}
	

	@Test(dataProvider="empLogin")
	public void VerifyInvalidLogin(String userName, String password) {
		driver.navigate().to(appURL);
		driver.findElement(byEmail).sendKeys(userName);
		driver.findElement(byPassword).sendKeys(password);
		//wait for element to be visible and perform click
		wait.until(ExpectedConditions.visibilityOfElementLocated(bySubmit));
		driver.findElement(bySubmit).click();
		
		//Check for error message
		wait.until(ExpectedConditions.presenceOfElementLocated(byError));
		String actualErrorDisplayed = driver.findElement(byError).getText();
		String requiredErrorMessage = "Please enter email as kiran@gmail.com";
		Assert.assertEquals(requiredErrorMessage, actualErrorDisplayed);
		
	}
	
	@DataProvider(name="empLogin")
	public Object[][] loginData() throws IOException {
		Object[][] arrayObject = getExcelData("sampledoc.xls","Sheet1");
		return arrayObject;
	}

	public String[][] getExcelData(String fileName, String sheetName) throws IOException {
		String[][] arrayExcelData = null;
		try {
			FileInputStream fs = new FileInputStream(fileName);
			Workbook wb = Workbook.getWorkbook(fs);
			Sheet sh = wb.getSheet(sheetName);

			int totalNoOfCols = sh.getColumns();
			int totalNoOfRows = sh.getRows();
			
			arrayExcelData = new String[totalNoOfRows-1][totalNoOfCols];
			for (int i= 1 ; i < totalNoOfRows; i++) {
				for (int j=0; j < totalNoOfCols; j++) {
					arrayExcelData[i-1][j] = sh.getCell(j, i).getContents();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  catch (BiffException e) {
			e.printStackTrace();
		}
		return arrayExcelData;
	}

	@AfterSuite
	public void tearDown() {
		driver.quit();
	}
}




