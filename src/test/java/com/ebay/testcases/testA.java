package com.ebay.testcases;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ebay.basetest.BaseTest;

public class testA extends BaseTest {

	@BeforeTest
	public void setup() {
		launchApp();
	}

	@Test(priority = 1)
	public void login() {
		// Parameterization can be added here for user name and password using testng dataprovider

	}

	@Test(priority = 2)
	public void addProduct() {

	}

	@AfterTest
	public void cleanup() {
		quitApp();
	}

}
