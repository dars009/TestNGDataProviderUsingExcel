package com.dataproviderDemo;

import java.util.ArrayList;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
//import org.testng.annotations.DataProvider;

import org.testng.annotations.Test;

public class TestCase1 {
	WebDriver wd = null;
	By ByEmail = By.id("email");
	By byPassword = By.id("password");

	@BeforeSuite
	void preRequisite() {
		wd = new FirefoxDriver();
		wd.get("file:///D:/AdminLTE/index.html");
		System.out.println("111111");
	}

	@Test(dataProvider = "logindata", dataProviderClass = Repo.class)
	public void UserNametest(String tcId, String testDescr, String uname, String passwd, String expMessage) {
	
		System.out.println("tcId>>>>" + tcId);
		System.out.println("testDecr>>>>" + testDescr);
		System.out.println("passwd>>>>" + passwd);
		System.out.println("expMessage>>>>" + expMessage);

		System.out.println("testcase>>" + new Date() + ">>" + tcId + "testDescr>>" + testDescr);
		
		WebElement w1 = wd.findElement(ByEmail);
		w1.clear();
		w1.sendKeys(uname);

		WebElement w2 = wd.findElement(byPassword);
		w2.clear();
		w2.sendKeys(passwd);

		WebElement w3submit = wd.findElement(By.xpath(".//*[@id='form']/div[3]/div/button"));
		w3submit.click();

		if (!"ValidUser".equals(testDescr)) {
			WebElement weError = wd.findElement(By.xpath(".//*[@id='email_error']"));
			String actLable = weError.getText();
			System.out.println(actLable);
			Assert.assertEquals(actLable, expMessage);
			System.out.println(tcId + testDescr + expMessage);
		} else {
			String actTitle = wd.getTitle();
			Assert.assertEquals(actTitle, expMessage);
		}
	}
}

