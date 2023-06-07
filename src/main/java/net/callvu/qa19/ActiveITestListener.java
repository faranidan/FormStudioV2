package net.callvu.qa19;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class ActiveITestListener implements ITestListener {
	public void onTestFailure(ITestResult result) {
	    System.out.println("Test failed [iTest Listener interface]");
	  }
	
	public void onTestSuccess(ITestResult result) {
		System.out.println("Test passed [iTest Listener interface]");
	  }
}
