package com.qeautos.selenium_examples;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class demoTest extends SeleniumTestLib {

	@BeforeMethod
	public void setup() {
		//Start ff browser and open google page(ff and url are set as default)
		start();
	}
	
	@Test
	public void demoTest() {
		//input hello world and click search button
		type(ElementXpath.searchField, "hello world");
		clickAndWaitPageLoad(ElementXpath.searchBtn);
	}

	@AfterMethod
	public void end() {
		quit();
	}

}
