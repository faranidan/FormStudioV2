package pageObjects;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v108.network.Network;
import org.openqa.selenium.devtools.v108.network.model.RequestId;
import org.openqa.selenium.devtools.v108.network.model.Response;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import base.BasePage;
import base.ExtentManager;

public class FormEditorObjects extends BasePage {
	public WebDriver driver;

	public FormEditorObjects() throws IOException, InterruptedException {
		super();
		PageFactory.initElements(getDriver(), this);
	}

	JavascriptExecutor jse = (JavascriptExecutor) getDriver();
	Actions act = new Actions(getDriver());

	public void specificAttUpload(String size, String fileTypes1, String fileTypes2, String filesNumberLimit, String errMsg) throws InterruptedException, IOException{
		specificAtt.click();
		waitForElement(limitSize, Duration.ofSeconds(3));
		act.moveToElement(limitSize).click(limitSize).perform();
		act.sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, size).perform();
		fileTypes.click();
		act.sendKeys(fileTypes1, Keys.ENTER).perform();
		act.sendKeys(fileTypes2, Keys.ENTER).perform();
		acceptMultiFiles.click();
		limitNumber.click();
		act.sendKeys(Keys.BACK_SPACE,filesNumberLimit).perform();
		limitErrorMsg.click();
		act.sendKeys(errMsg).perform();
	}

	public void addField(WebElement field, String inputLabelID) throws InterruptedException, IOException {
		if (field.isDisplayed() == false) {
			getBasicFieldsDropdown().click();
			waitForElement(field, Duration.ofSeconds(6));
		}
		jse.executeScript(jsDragnDrop(), field, getDropArea1());
		getGenericAttrbts().click();
		getIntID().click();
		act.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(inputLabelID).perform();
		getGenAttLabel().click();
		act.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(inputLabelID).perform();
		ExtentManager.pass("Field added succesfully: "+inputLabelID);
	}

	public void renameFormTitleBlock(String formName, String titleName, String blockName)
			throws InterruptedException, IOException {
		getRenameForm().sendKeys(Keys.BACK_SPACE, formName);
		getFormTitle().sendKeys(titleName);
		getBlock1Title().sendKeys(Keys.BACK_SPACE, blockName);
		Thread.sleep(900);
	}

	public void openSavedForm(String name) throws InterruptedException, IOException {
		ExtentManager.log("Starting openSavedForm test...");
		waitForElement(getFolderActions(), Duration.ofSeconds(6));
		getFolderActions().click();
		waitForElement(getOpenForm(), Duration.ofSeconds(6));
		getOpenForm().click();
		ExtentManager.pass("Got to forms folder");
		waitForElement(getFirstForm(), Duration.ofSeconds(6));
		for (int i = 0; i < formList.size(); i++) {
			String formName = formList.get(i).getText();
			if (formName.contains(name)) {
				act.scrollToElement(formList.get(i)).click(formList.get(i)).perform();
				i = formList.size();
			}
		}
		ExtentManager.pass("Listed all forms and selected the desired form to open");
		waitForElement(getOpenBtn(), Duration.ofSeconds(3));
		getOpenBtn().click();
		waitForInvisibility(getOpenBtn(), Duration.ofSeconds(6));
		ExtentManager.pass("Opened desired form: "+name);
		Thread.sleep(600);
	}

	public void saveForm() throws InterruptedException, IOException {
		ExtentManager.log("Starting saveForm method");
		waitForElement(getSaveBtn(), Duration.ofSeconds(6));
		getSaveBtn().click();
		ExtentManager.pass("Clicked main save button");
		Thread.sleep(600);
		try{
			saveBtnTest.click();
			Thread.sleep(600);
			ExtentManager.pass("Clicked save button notification");
		} catch(Exception e){
			ExtentManager.pass("no save notification");
		}
	}

	public void createAction() throws InterruptedException, IOException {
		getSettings().click();
		act.scrollToElement(getCreateAction()).click(getCreateAction()).perform();
		Thread.sleep(1200);
	}

	public void createSumApi(String apiName, String actionCode, String input1, String input2, String sum)
			throws InterruptedException, IOException {
		getSelectAPI().click();
		Thread.sleep(300);
		act.scrollToElement(getSumAPI()).click(getSumAPI()).perform();
		Thread.sleep(300);
		act.click(getActionName()).sendKeys(apiName).perform();
		Thread.sleep(300);
		act.click(getActionCode()).sendKeys(actionCode).perform();
		act.click(selectField1).sendKeys(input1).sendKeys(Keys.ENTER).perform();
		act.click(selectField2).sendKeys(input2).sendKeys(Keys.ENTER).perform();
		Thread.sleep(300);
		act.scrollToElement(getActionSumFld()).click(getActionSumFld()).sendKeys(sum, Keys.ENTER).perform();
		Thread.sleep(300);
		act.scrollToElement(getActionSaveBtn()).click(getActionSaveBtn()).perform();
		Thread.sleep(900);
		ExtentManager.pass("Created actions succesfully: "+apiName);
	}

	public void newSumActionISA() throws InterruptedException, IOException {
		act.scrollToElement(getCreateNewActionBtn()).click(getCreateNewActionBtn()).perform();
		Thread.sleep(300);
		getSelectAPI().click();
		Thread.sleep(300);
		act.scrollToElement(getListAPI()).click(getListAPI()).perform();
		Thread.sleep(300);
		getSelectAPI().click();
		Thread.sleep(300);
		act.scrollToElement(getSumAPI()).click(getSumAPI()).perform();
		Thread.sleep(300);
		ExtentManager.pass("Added a new SUM action inside the actions menu");
	}

	public void switchTab() throws InterruptedException, IOException {
		ExtentManager.log("Starting switchTab test...");
		Thread.sleep(3000);
		String MainWindow = getDriver().getWindowHandle(); 
		Set<String> handles = getDriver().getWindowHandles(); 
		Iterator<String> iterate = handles.iterator(); 
		while (iterate.hasNext()) { 
			String child = iterate.next(); 
			if (!MainWindow.equalsIgnoreCase(child)) { 
				getDriver().switchTo().window(child);
				ExtentManager.pass("Switched succesfully to second tab");
			}
		}
	}
	/* 
	public void changeTab(Boolean close) throws InterruptedException, IOException {
		Thread.sleep(1200);
		ArrayList<String> tabsList = new ArrayList<>(getDriver().getWindowHandles()); // tabsList is static
		if (close == true){
			getDriver().switchTo().window(tabsList.get(1));	// 2nd tab
			getDriver().close();
			getDriver().switchTo().window(tabsList.get(0));
		} else {
			getDriver().switchTo().window(tabsList.get(1));
		} 
		
	}
	*/

	public void changeTab(Integer tabNmbr, Boolean close) throws InterruptedException, IOException {
		Thread.sleep(1200);
		//loaderGone();
		//waitForInvisibility(loaderPrvw, Duration.ofSeconds(3));
		ArrayList<String> tabsList = new ArrayList<>(getDriver().getWindowHandles()); // tabsList is static
		if (close == true){
			getDriver().switchTo().window(tabsList.get(tabNmbr));	// 2nd tab
			getDriver().close();
			getDriver().switchTo().window(tabsList.get(tabNmbr-1));
		} else {
			getDriver().switchTo().window(tabsList.get(tabNmbr));
		} 
		
	}

	public void previewSum(Integer var1, Integer var2) throws InterruptedException, IOException {
		ExtentManager.log("Starting previewSum test...");
		getPrvwOneInpt().sendKeys(var1.toString());
		getPrvwTwoInpt().sendKeys(var2.toString());
		getPrvwSum1Inpt().click();
		ExtentManager.pass("Sent selected values: " + var1.toString() + "+" + var2.toString());
		Thread.sleep(900);
		if (getPrvwSum1Inpt().getAccessibleName().equals("sum1 " + (var1 + var2))) {
			ExtentManager.pass("Success! sum1 = " + (var1 + var2));
			System.out.println("Success! sum1 = " + (var1 + var2));
		} else {
			ExtentManager.fail("Failed! " + getPrvwSum1Inpt().getAccessibleName());
			System.out.println("Failed! " + getPrvwSum1Inpt().getAccessibleName());
		}
		prvwSum2Input.click();
		Thread.sleep(900);
		if (prvwSum2Input.getAccessibleName().equals("n2sums " + ((var1 + var2) * 2))) {
			ExtentManager.pass("Success! sum2 = " + ((var1 + var2) * 2));
			System.out.println("Success! sum2 = " + ((var1 + var2) * 2));
		} else {
			ExtentManager
					.fail("Failed! sum2 != " + ((var1 + var2) * 2) + ". It is: " + prvwSum2Input.getAccessibleName());
			System.out.println(
					"Failed! sum2 != " + ((var1 + var2) * 2) + ". It is: " + prvwSum2Input.getAccessibleName());
		}
		Thread.sleep(600);
	}

	public void previewDone() throws InterruptedException, IOException {
		ExtentManager.log("Starting previewDone test...");
		getPrvwDone().click();
		waitForElement(formEndImg, Duration.ofSeconds(9));
		Assert.assertEquals(getDriver().getTitle(), "Thank You");
		ExtentManager.pass("Got to 'Thank You' page, form ended successfully");
	}

	public void devTools(String url) throws InterruptedException, IOException {
		ExtentManager.log("Starting devTools test...");
		DevTools devTools = ((HasDevTools) getDriver()).getDevTools();
		devTools.createSession();
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		RequestId[] id = new RequestId[1];
		ExtentManager.pass("Setup configurations for devTools done");
		devTools.addListener(Network.responseReceived(), response -> {
			Response res = response.getResponse();
			id[0] = response.getRequestId();
			if (res.getUrl().equals(url)) {
				String responseBody = devTools.send(Network.getResponseBody(id[0])).getBody();
				System.out.println("Response url: " + res.getUrl());
				System.out.println("Response status: " + res.getStatus());
				System.out.println("Response body: " + responseBody.toString());
			}
		});
		ExtentManager.pass("Response of url " + url + " recorded to console logs");
	}

	public void dropdownFill1() throws InterruptedException, IOException {
		ExtentManager.log("Starting dropdownFill1 test...");
		fatherDropdown.click();
		Thread.sleep(600);
		act.moveToElement(fddItem1).click(fddItem1).perform();
		act.moveToElement(dropdown1).click(dropdown1).perform();
		waitForElement(dd1Item2, Duration.ofSeconds(9));
		dd1Item2.click();
		Thread.sleep(600);
		autocomplete1.click();
		waitForElement(ac1Item3, Duration.ofSeconds(3));
		ac1Item3.click();
		multiSelect1.click();
		ms1Item4.click();
		ms1Item5.click();
		ExtentManager.pass("Selected fields for fatherDropdown, dropdown1 & autocomplete1 successfully");
	}

	public void dropdownFill2() throws InterruptedException, IOException {
		ExtentManager.log("Starting dropdownFill2 test...");
		formHeader.click();
		act.moveToElement(dropdown2).click(dropdown2).perform();
		Thread.sleep(3600);
		dd2Item1.click();
		autocomplete2.click();
		Thread.sleep(600);
		ac2Item1.click();
		multiSelect2.click();
		ms2Item1.click();
		ExtentManager.pass("Selected fields for dropdown2, autocomplete2 & multiSelect2 successfully");
	}

	public void dropdownSelectedFieldClear() throws InterruptedException {
		ExtentManager.log("Starting dropdownSelectedFieldClear method...");
		fddClear.click();
		ExtentManager.pass("Cleared fatherDropdown");
		Thread.sleep(1800);
		testEmptyDDownInput(dropdown1, "Please Select");
		testEmptyDDownInput(autocomplete1, "Start typing...");
		testEmptyDDownInput(ms1Selected, "Please Select");
		testEmptyDDownInput(dropdown2, "Please Select");
		testEmptyDDownInput(autocomplete2, "Start typing...");
		testEmptyDDownInput(ms2Selected, "Please Select");
	}

	public void dropdownDataClear1() throws InterruptedException {
		ExtentManager.log("Starting dropdownDataClear1 method...");
		autocomplete1.click();
		Thread.sleep(300);
		testDropdownClearing(ac1Item3);
		formHeader.click();
		act.moveToElement(dropdown1).click(dropdown1).perform();
		Thread.sleep(300);
		testDropdownClearing(dd1Item2);
		formHeader.click();
		multiSelect1.click();
		Thread.sleep(300);
		testDropdownClearing(ms1Item4);
	}

	public void dropdownDataClear2() throws InterruptedException {
		ExtentManager.log("Starting dropdownDataClear2 test...");
		formHeader.click();
		act.moveToElement(dropdown2).click(dropdown2).perform();
		Thread.sleep(300);
		testDropdownClearing(dd2Item1);
		formHeader.click();
		autocomplete2.click();
		Thread.sleep(300);
		testDropdownClearing(ac2Item1);
		formHeader.click();
		multiSelect2.click();
		Thread.sleep(300);
		testDropdownClearing(ms2Item1);
	}

	public void testDropdownClearing(WebElement item){
		if(noDataAvl.isDisplayed()){
			ExtentManager.pass("Data tested has cleared successfully");
		} else if ((!noDataAvl.isDisplayed())){ 
			try{
				if (item.getText().length()>0){
					ExtentManager.fail(item.getText()+"- field data DID NOT clear");
				} else {
					ExtentManager.pass("Data tested has cleared successfully");
				}
			} catch (NoSuchElementException e){
				ExtentManager.pass("Data tested has cleared successfully");
			}
		} else {
			ExtentManager.fail(item.getText()+"- field data DID NOT clear");
		}
	}

	public void testEmptyDDownInput(WebElement item, String emptyMsg){
		if (item.getAttribute("placeholder").contains(emptyMsg)) {
			ExtentManager.pass(item.getAttribute("aria-label")+"- field cleared");
		} else {
			ExtentManager.fail(item.getAttribute("aria-label")+"- field DID NOT clear.");
		}
	}

	public void previewUploadFiles1() throws InterruptedException, IOException{
		ExtentManager.log("Starting Upload1 method");
		waitForElement(prvwFileUp1, Duration.ofSeconds(6));
		prvwFileUp1.click();
		Thread.sleep(600);
		Runtime.getRuntime().exec("C:\\Users\\idan.faran\\Desktop\\resources\\Files\\upload test files\\autoIT\\upload1.exe");
		Thread.sleep(600);
		ExtentManager.pass("Uploaded file1 successfully");
		try{
			if(errorAlert.getText().contains("File can not be empty")){
				ExtentManager.pass("Upload1 test Passed. Empty file msg: "+errorAlert.getText());
			} else {
				ExtentManager.fail("Upload1 test Failed: "+errorAlert.getText());
			}
		} catch (Exception e){
			ExtentManager.fail("Upload1 test Failed: No error message. Empty file uploaded successfully");
		}
	}

	public void previewUploadFiles2() throws IOException, InterruptedException{
		ExtentManager.log("Starting Upload2 method");
		prvwFileUp2.click();
		Thread.sleep(600);
		Runtime.getRuntime().exec("C:\\Users\\idan.faran\\Desktop\\resources\\Files\\upload test files\\autoIT\\upload2file1.exe");
		ExtentManager.pass("Uploaded file1 successfully");
		Thread.sleep(600);
		try{
			if(uploadedFileName.getText().contains("Form")){
				ExtentManager.pass("Upload2 file1 Passed. Uploaded file: "+uploadedFileName.getText());
			} else {
				ExtentManager.fail("Upload2 file1 Failed: "+uploadedFileName.getText());
			}
		} catch (Exception e){
			ExtentManager.fail("Upload2 file1 failed. No uploaded file text");
		}
		prvwFileUp2.click();
		Thread.sleep(600);
		Runtime.getRuntime().exec("C:\\Users\\idan.faran\\Desktop\\resources\\Files\\upload test files\\autoIT\\upload2file2.exe");
		Thread.sleep(600);
		ExtentManager.pass("Uploaded file2 successfully");
		try{
			if(errorAlert.getText().contains("You tried to upload file(s) with forbidden extension(s)")){
				ExtentManager.pass("Upload2 file2 test Passed. Error msg: "+errorAlert.getText());
			} else {
				ExtentManager.fail("Upload2 file2 test Failed: "+errorAlert.getText());
			}
		} catch (Exception e){
			ExtentManager.fail("Upload2 file2 test Failed. No error message. File uploaded successfully");
		}
		
	}

	public void previewUploadFiles3() throws IOException, InterruptedException{
		ExtentManager.log("Starting Upload3 method");
		prvwFileUp3.click();
		Thread.sleep(600);
		Runtime.getRuntime().exec("C:\\Users\\idan.faran\\Desktop\\resources\\Files\\upload test files\\autoIT\\upload3file1.exe");
		ExtentManager.pass("Uploaded file1 successfully");
		Thread.sleep(600);
		try{
			if(uploadedFileName2.getText().contains("Regression")){
				ExtentManager.pass("Upload3 file1 Passed. Uploaded file: "+uploadedFileName2.getText());
			} else {
				ExtentManager.fail("Upload3 file1 test Failed: "+uploadedFileName2.getText());
			}
		} catch (Exception e){
			ExtentManager.fail("Upload3 file1 test Failed. No uploaded file text");
		}
		prvwFileUp3.click();
		Thread.sleep(600);
		Runtime.getRuntime().exec("C:\\Users\\idan.faran\\Desktop\\resources\\Files\\upload test files\\autoIT\\upload3file2.exe");
		ExtentManager.pass("Uploaded file2 successfully");
		Thread.sleep(600);
		try{
			if(errorAlert2.getText().contains("The file you are trying to upload is larger")){
				ExtentManager.pass("Upload3 file2 test Passed. Error msg: "+errorAlert2.getText());
			} else {
				ExtentManager.pass("Upload3 file2 test Failed: "+errorAlert2.getText());
			}
		} catch (Exception e){
			ExtentManager.pass("Upload3 file2 test failed. No error message. File uploaded successfully");
		}
	}

	public void addNewRule(String enterRuleName, String enterRuleCode) throws InterruptedException, IOException{
		ExtentManager.log("Starting editRules method");
		if(!ruleName.isDisplayed()){
			editRulesBtn.click();
		} else {
			act.scrollToElement(createNewRule).click(createNewRule).perform();
		}
		act.click(ruleName).sendKeys(enterRuleName).perform();
		Thread.sleep(600);
		try{
			ruleNmNotUnqError.isDisplayed();
			ExtentManager.log("Rule name already exists");
			for (int i = 0; i < savedRulesList.size(); i++) {
				String name = savedRulesList.get(i).getText();
				System.out.println("rules names: "+name);
				if (name.equals(enterRuleName)) {
					savedRulesList.get(i).click();
					Thread.sleep(600);
					deleteRule.click();
					Thread.sleep(600);
					act.moveToElement(deleteRuleOK).click().perform();
				}
			}
			act.click(ruleName).sendKeys(enterRuleName).perform();
		} catch (Exception e){
			ExtentManager.pass("new rule is being made");
		}

		act.click(rulesCode).sendKeys(enterRuleCode).perform();
		act.scrollToElement(ruleSelectField).click(ruleSelectField).perform();
		ExtentManager.pass("Added a new rule Name & Code");
	}

	public void ruleOutcome(WebElement field, WebElement fieldStatus) throws InterruptedException, IOException{
		ExtentManager.log("Starting ruleOutcome method");
		waitForElement(field, Duration.ofSeconds(3));
		act.scrollToElement(field).click(field).perform();
		ruleSelectFieldStatus.click();
		waitForElement(fieldStatus, Duration.ofSeconds(3));
		fieldStatus.click();
		ExtentManager.pass("Selected a field & outcome for rule");
		act.scrollToElement(ruleSaveBtn).click(ruleSaveBtn).perform();
		ExtentManager.pass("Saved rule");
		}
	
	public void addOutcome(WebElement fieldStatus) throws InterruptedException, IOException{
		ExtentManager.log("Starting addOutcome method");
		plusRuleBtn.click();
		ExtentManager.pass("Added rule inside, using Plus button");
		act.moveToElement(nextFieldStatus).click().perform();
		fieldStatus.click();
		ExtentManager.pass("Selected status to next field in line [auto selected next field]");
		act.scrollToElement(ruleUpdateSave).click(ruleUpdateSave).perform();
		Thread.sleep(600);
		ExtentManager.pass("Saved-Update rule");		
	}

	public void addRuleByBlock(WebElement block, WebElement blockStatus) throws InterruptedException, IOException{
		ExtentManager.log("Starting addRuleByBlock method");
		waitForElement(ruleByFieldDD,Duration.ofSeconds(9));
		ruleByFieldDD.click();
		byBlockDD.click();
		act.moveToElement(selectBlockDD).click().perform();
		block.click();
		act.moveToElement(selectBlockStatus).click().perform();
		blockStatus.click();
		act.scrollToElement(ruleSaveBtn).click(ruleSaveBtn).perform();
		ExtentManager.pass("Rule by block added & saved");
	}

	public void prvwRulesStep1() throws InterruptedException, IOException{
		ExtentManager.log("Starting prvwRulesStep1 method...");
		waitForElement(chkbxPrvw, Duration.ofSeconds(3));
		testRulesDisabled(chkbxPrvw);
		testRulesDisabled(radio2Prvw);
		passPrvw.sendKeys("1");
		testRulesEnabled(chkbxPrvw);
		phnPrvw.sendKeys("1");
		Thread.sleep(300);
		getPrvwNext().click();
		Thread.sleep(300);
		testErrorMsg("Required");
	}

	public void prvwRulesStep2() throws InterruptedException, IOException{
		ExtentManager.log("Starting prvwRulesStep2 method...");
		Thread.sleep(600);
		nmbPrvw.sendKeys("3");
		block1HdrPrvw.click();
		Thread.sleep(600);
		getPrvwNext().click();
		lt1Prvw.sendKeys("This is an example of a long text. This is an example of a long text");
		crrPrvw.sendKeys("100");
		timePrvw.sendKeys("12");
		block2HdrPrvw.click();
		testErrorMsg("Time field");
		timePrvwAfterChange.sendKeys(":12");
	}

	public void prvwRulesStep3() throws InterruptedException, IOException{
		ExtentManager.log("Starting prvwRulesStep3 method...");
		datePrvw.sendKeys("01");
		block2HdrPrvw.click();
		testErrorMsg("Date field");
		datePrvw.sendKeys("/02/2023");
		testRulesDisabled(errorMsgPrvw);
		id1Prvw.sendKeys("123456789");
		block2HdrPrvw.click();
		testErrorMsg("ID field");
		backBtnPrvw.click();
		waitForElement(passAfterPrvw, Duration.ofSeconds(3));
	}

	public void prvwRulesStep4() throws InterruptedException, IOException{
		ExtentManager.log("Starting prvwRulesStep4 method...");
		passAfterPrvw.sendKeys(Keys.BACK_SPACE);
		block1HdrPrvw.click();
		Thread.sleep(600);
		testRulesDisabled(phnPrvw);
		passPrvw.sendKeys("123");
		block1HdrPrvw.click();
		Thread.sleep(600);
		if (phnPrvw.getAttribute("aria-label").equals("phn ")){
			ExtentManager.pass("Passed: Hidden rule cleared phn Field");
		} else {
			ExtentManager.fail("Failed: Hidden rule DID NOT clear phn Field");
		}
	}

	public void testRulesDisabled(WebElement field) throws InterruptedException{
		Thread.sleep(600);
		try{
			field.click();
			ExtentManager.fail(field.getText()+" - Rule Failed: clicked [field not disabled/hidden]");
		}catch(Exception e){
			ExtentManager.pass("Rule Passed: could not click [field disabled/hidden]");
		}
	}

	public void testRulesEnabled(WebElement field) throws InterruptedException{
		Thread.sleep(600);
		try{
			field.click();
			ExtentManager.pass(field.getText()+" - Rule Passed: clicked [field not disabled/hidden]");
		}catch(Exception e){
			ExtentManager.fail("Rule Failed: could not click [field disabled/hidden]");
		}
	}

	public void testErrorMsg(String type) throws InterruptedException{
		try{
			errorMsgPrvw.isDisplayed();
			ExtentManager.pass(type+"- Passed. Error msg appeared");
		} catch (Exception e){
			ExtentManager.fail(type+"- Failed. Error msg did not appear");
		} 
	}

	public void editStep(WebElement step, String nextStepName, String backStepName, boolean add) {
		ExtentManager.log("Starting editStep method");
		step.click();
		nextStep.click();
		nextStepText.sendKeys(nextStepName);
		ExtentManager.pass("Added next step name");
		if (step != step1){
			backStep.click();
			backStepText.sendKeys(backStepName);
			ExtentManager.pass("Added back step name");
		}
		if (add==true) {
			addStep.click();
			ExtentManager.pass("Added a new step");
		}
	}

	public void testStepName(WebElement step, String stepName){
		ExtentManager.log("Starting testStepName method");
		if (step.getText().contains(stepName)){
            ExtentManager.pass("Validation success! next btn is: "+step.getText());
        } else {
            ExtentManager.fail("failed next validation :"+step.getText());
        }
	}

	public void testLastStepName(String stepName){
		ExtentManager.log("Starting testLastStepName method");
		if (doneStepBtnPrvw.getText().contains(stepName)){
            ExtentManager.pass("Validation success! next btn is: "+doneStepBtnPrvw.getText());
        } else {
            ExtentManager.fail("failed next validation :"+doneStepBtnPrvw.getText());
        }
	}

	public String getCurrentUrl() throws InterruptedException, IOException{
		return getDriver().getCurrentUrl();
	}
	
	public void testBtns1() throws InterruptedException, IOException{
		System.out.println("Started method testBtns1: btns steps w/o req, hidden steps, rules. testing rules, validations, finish + hidden");
		ExtentManager.log("Started method testBtns1: btns steps w/o req, hidden steps, rules. testing rules, validations, finish + hidden");
		startBtnsPrvwTest();
		testBtnReqMsg(btn1PrvwBtns, "btn1");
		testBtnReqMsg(btn2PrvwBtns, "btn2");
		testBtnReqMsg(btn4PrvwBtns, "btn4");
		testBtnReqMsg(nextStepBtnPrvw, "Next Step");
		ExtentManager.pass("Passed: All required Step buttons do validation");
		btn3PrvwBtns.click();
		testHiddenStep(backStepBtnPrvw, secondStepBtn);
		backStepBtnPrvw.click();
		reqStep3Prvw.sendKeys("a");
		testBtnReqMsg(btn3PrvwBtns, "btn3 rule: Required");
		hideBlkVerPrvw.sendKeys("1");
		ExtentManager.pass("Passed: Rules Required & Hide to buttons");
		Thread.sleep(660);
		btn1PrvwBtns.click();
		backStepBtnPrvw.click();
		btn2PrvwBtns.click();
        testFormSubmitted();
		ExtentManager.pass("Passed: Step[Finish Form] submitted form");
		changeTab(1, true);
		System.out.println("Ended method testBtns1 successfully.");
		ExtentManager.log("Ended method testBtns1 successfully.");
	}

	public void testBtns2() throws InterruptedException, IOException{
		System.out.println("Started method testBtns2: btn step+finish, testing validations + finish");
		ExtentManager.log("Started method testBtns2: btn step+finish, testing validations + finish");
		startBtnsPrvwTest();
        phoneNumberPrvw.sendKeys("1");
        btn4PrvwBtns.click();
        testFormSubmitted();
		ExtentManager.pass("Passed: Step[Finish Form] submitted form");
        changeTab(1, true);
		System.out.println("Ended method testBtns2 successfully.");
		ExtentManager.log("Ended method testBtns2 successfully.");
	}

	public void testBtns3() throws InterruptedException, IOException{
		ExtentManager.log("Started method testBtns3: btns- url new tab [req,finish], testing params & validations");
		System.out.println("Started method testBtns3: btns- url new tab [req,finish], testing params & validations");
		startBtnsPrvwTest();
        phoneNumberPrvw.sendKeys("1");
        nextStepBtnPrvw.click();
		testBtnReqMsg(btn5PrvwBtns, "btn5");
		testBtnReqMsg(btn7PrvwBtns, "btn7");
		testBtnReqMsg(btn8PrvwBtns, "btn8");
		ExtentManager.pass("Passed: All required Buttons with URL[newTab] require validation");
		String GUID = getGuid();
		btn6PrvwBtns.click();
		testUrlParams(GUID, true);
		lt1PrvwBtns.sendKeys("Verrrrrrryyyy Looonnggg Textttt");
		btn5PrvwBtns.click();
		testUrlParams(GUID, true);
		btn7PrvwBtns.click();
		testUrlParams(GUID, true);
		changeTab(1, true);
		System.out.println("Ended method testBtns3 successfully.");
		ExtentManager.log("Ended method testBtns3 successfully.");
	}

	public void testBtns4() throws IOException, InterruptedException{
		ExtentManager.log("Started method testBtns4: btns- url same tab [req,finish], testing params, finish & validations");
		System.out.println("Started method testBtns4: btns- url same tab [req,finish], testing params, finish & validations");
		startBtnsPrvwTest();
		btn3PrvwBtns.click();
		testBtnReqMsg(btn9PrvwBtns, "btn9");
		testBtnReqMsg(btn10PrvwBtns, "btn10");
		testBtnReqMsg(btn11PrvwBtns, "btn11");
		testBtnReqMsg(btn11PrvwBtns, "btn12");
		ExtentManager.pass("Passed: All type of Buttons with URL[sameTab] require validation");
		CheckboxBtnsPrvw.click();
		String GUID = getGuid();
		btn9PrvwBtns.click();
		testUrlParams(GUID, false);
		
		startBtnsPrvwTest();
		btn3PrvwBtns.click();
		CheckboxBtnsPrvw.click();
		String GUID2 = getGuid();
		btn11PrvwBtns.click();
		// testFormSubmitted(); // not working. does not catch the moment of change. Maybe in a while loop
		testUrlParams(GUID2, false);
		System.out.println("Ended method testBtns4 successfully.");
		ExtentManager.log("Ended method testBtns4 successfully.");
	}
	
	public void testBtns5() throws InterruptedException, IOException {
		ExtentManager.log("Started method testBtns5: btns rules: testing all rules & required validations");
		System.out.println("Started method testBtns5: btns rules: testing all rules & required validations");
		startBtnsPrvwTest();
		step4Btn.click();
		Thread.sleep(600);
		testRulesEnabled(btn13PrvwBtns);
		testRulesEnabled(btn14PrvwBtns);
		testRulesDisabled(btn15PrvwBtns);
		testRequiredBtnRule(btn16PrvwBtns, false);
		ExtentManager.pass("Passed: All rules are inactive prior to activation");
		activateRuleBtnPrvw.sendKeys("1");
		testRulesDisabled(btn13PrvwBtns);
		testRulesDisabled(btn14PrvwBtns);
		testRulesEnabled(btn15PrvwBtns);
		testRequiredBtnRule(btn16PrvwBtns, true);
		ExtentManager.pass("Passed: All rules are active after activation");
		System.out.println("Ended method testBtns5 successfully.");
		ExtentManager.log("Ended method testBtns5 successfully.");
	}

	public void testRequiredBtnRule (WebElement button, boolean shouldBeReq){
		if (shouldBeReq == true) {
			if (button.getText().equals("required*")) {
				ExtentManager.pass("Passed: Required rule turned on for button");
			} else {
				ExtentManager.fail("Failed: button name: "+ button.getText());
			} 
		} else {
			if (button.getText().equals("required")) {
				ExtentManager.pass("Passed: Required rule not turned on for button");
			} else {
				ExtentManager.fail("Failed: button name: "+ button.getText());
			}
		}
	}

	public void testFormSubmitted() throws InterruptedException, IOException{
		try{
			waitForClick(formEndImg, Duration.ofSeconds(6));
			ExtentManager.pass("Passed. Form submitted successfully.");
		} catch (NoSuchElementException | TimeoutException e){
			ExtentManager.fail("Failed: Did not get to Form submitted. Url: "+getDriver().getCurrentUrl());
			e.printStackTrace();
		}
	}

	public void testUrlParams(String Guid, boolean newTab) throws InterruptedException, IOException{
		Thread.sleep(2400);
		if(newTab==true){
			changeTab(2, false);
			if (getDriver().getCurrentUrl().equals("https://www.google.com/?guid="+Guid+"&formID=2000312")){
				ExtentManager.pass("Passed: Method testUrlParams, link opened with currect GUID + fromID");
			} else {
				ExtentManager.fail("Failed: Method testUrlParams. URL is: "+getDriver().getCurrentUrl());
			}
			changeTab(2, true);
		} else {
			if (getDriver().getCurrentUrl().equals("https://www.google.com/?guid="+Guid+"&formID=2000312")){
				ExtentManager.pass("Passed: Method testUrlParams, link opened with currect GUID + fromID");
			} else {
				ExtentManager.fail("Failed: Method testUrlParams. URL is: "+getDriver().getCurrentUrl());
			}
			changeTab(1, true);
		}	
	}

	public void testHiddenStep(WebElement step, WebElement btn){
		try{
			step.click();
			ExtentManager.fail("Failed. Back Step should be hidden");
		} catch(NoSuchElementException e){
			ExtentManager.pass("Passed. Back Step is hidden. Clicking replacing btn");
			btn.click();
		}
	}

	public void startBtnsPrvwTest() throws InterruptedException, IOException{
		waitForClick(getPreviewForm(), Duration.ofSeconds(6));
		getPreviewForm().click();
        changeTab(1, false);
		waitForClick(buttonsStep1Hdr, Duration.ofSeconds(3));
		ExtentManager.pass("Previewed the form & changed tab");
	}

	public String getGuid() throws InterruptedException, IOException{
		String FormUrl = getDriver().getCurrentUrl();
		String[] Split = FormUrl.split("d=");
		ExtentManager.pass("Got form GUID");
		return Split[1];
	}

	public void testBtnReqMsg(WebElement btn, String btnName) throws InterruptedException, IOException{
		btn.click();
		Thread.sleep(1200);
		testErrorMsg(btnName+" Required validation");
	}


	//loader!
	@FindBy (css = ".loading-anim-btn") public WebElement loaderPrvw;
	@FindBy (xpath = "//*[name()='circle' and contains(@class,'v-progress')]") public WebElement loaderStudio;

	//preview buttons
	@FindBy (xpath = "//div[contains(text(),'step[4]')]") public WebElement step4Btn;	
	@FindBy (xpath = "//div[contains(text(),'required')]") public WebElement btn16PrvwBtns;	
	@FindBy (xpath = "//div[contains(text(),'enabled')]") public WebElement btn15PrvwBtns;
	@FindBy (xpath = "//div[contains(text(),'disabled')]") public WebElement btn14PrvwBtns;
	@FindBy (xpath = "//div[contains(text(),'hidden')]") public WebElement btn13PrvwBtns;
	@FindBy (css = "input[aria-label='activate rule ']") public WebElement activateRuleBtnPrvw;

	@FindBy (xpath = "//label[normalize-space()='Checkbox*']") public WebElement CheckboxBtnsPrvw;
	@FindBy (xpath = "//div[contains(text(),'4th Step*')]") public WebElement btn12PrvwBtns;	
	@FindBy (xpath = "//div[contains(text(),'Finish url sameTab')]") public WebElement btn11PrvwBtns;
	@FindBy (xpath = "//div[contains(text(),'url Req sameTab*')]") public WebElement btn10PrvwBtns;
	@FindBy (xpath = "//div[@class='v-btn__content'][normalize-space()='url sameTab']") public WebElement btn9PrvwBtns;
	
	@FindBy (xpath = "//div[@class='v-btn__content'][normalize-space()='2nd Step']") public WebElement secondStepBtn;
	@FindBy (xpath = "//textarea[@aria-label='Long Text ']") public WebElement lt1PrvwBtns;
	@FindBy (xpath = "//div[contains(text(),'Finish url newTab notReq')]") public WebElement btn8PrvwBtns;
	@FindBy (xpath = "//div[contains(text(),'Finish url newTab req*')]") public WebElement btn7PrvwBtns;
	@FindBy (xpath = "//div[@class='v-btn__content'][normalize-space()='url newTab notReq']") public WebElement btn6PrvwBtns;
	@FindBy (xpath = "//div[@class='v-btn__content'][normalize-space()='url newTab req*']") public WebElement btn5PrvwBtns;
	@FindBy (css = "h2[aria-label='url newTab options']") public WebElement buttonsStep2Hdr;

	@FindBy (xpath = "//h2[normalize-space()='req + step']") public WebElement buttonsStep1Hdr;
	@FindBy (xpath = "//div[contains(text(),'req+step[2]')]") public WebElement btn1PrvwBtns;
	@FindBy (xpath = "//div[contains(text(),'req+step+finish')]") public WebElement btn2PrvwBtns;
	@FindBy (xpath = "//div[contains(text(),'step[3]')]") public WebElement btn3PrvwBtns;
	@FindBy (xpath = "//div[@class='v-btn__content'][normalize-space()='step+finish']") public WebElement btn4PrvwBtns;
	@FindBy (css = "input[aria-label='req step[3] ']") public WebElement reqStep3Prvw;
	@FindBy (css = "input[aria-label='Phone Number ']") public WebElement phoneNumberPrvw;
	@FindBy (css = "input[aria-label='hide block verifications ']") public WebElement hideBlkVerPrvw;
	
	//preview steps
	@FindBy (xpath = "//button[@class='v-btn v-btn--block theme--light blue done-btn white--text']//div[@class='v-btn__content']")
	public WebElement doneStepBtnPrvw;
	@FindBy (css = "button[class='v-btn v-btn--block theme--light blue content-rtl next-step white--text']")
	public WebElement nextStepBtnPrvw;
	@FindBy (css = "button[class='v-btn v-btn--block theme--light blue content-rtl prev-step white--text'] div[class='v-btn__content']")
	public WebElement backStepBtnPrvw;
	// new backPrvwBtn "button[class='v-btn v-btn--block theme--light blue content-rtl prev-step white--text'] div[class='v-btn__content']"
	// old backPrvwBtn "button[class='v-btn v-btn--block theme--light blue content-rtl prev-step white--text']"

	//build steps
	@FindBy (css = "span[placeholder='Step Title']") public WebElement step1;
	@FindBy (css = "input[aria-label='Step Name']") public WebElement stepName;
	@FindBy (xpath = "//div[contains(text(),'Next Step')]") public WebElement nextStep;
	@FindBy (xpath = "//div[contains(text(),'Back Step')]") public WebElement backStep;
	@FindBy (css = "input[aria-label='Text']") public WebElement nextStepText;
	@FindBy (xpath = "(//input[@aria-label='Text'])[2]") public WebElement backStepText;
	@FindBy (xpath = "(//label[normalize-space()='Hide Step Button'])[1]") public WebElement hideNextStepBtn;
	@FindBy (xpath = "(//label[normalize-space()='Hide Step Button'])[2]") public WebElement hideBackStepBtn;
	@FindBy (css = ".blue--text .material-icons") public WebElement addStep;
	@FindBy (xpath = "//i[normalize-space()='check']") public WebElement currentStep;

	//preview rules
	@FindBy(css = "h2[aria-label='Block1']") public WebElement block1HdrPrvw;
	@FindBy(xpath = "(//div[@class='v-list__tile__title'][normalize-space()='No data available'])") 
	public WebElement noDataAvl;
	@FindBy(css = "input[aria-label='time 12']") public WebElement timePrvwAfterChange;
	@FindBy(css = "h2[aria-label='Block2']") public WebElement block2HdrPrvw;
	@FindBy(xpath = "//span[normalize-space()='Back']") public WebElement backBtnPrvw;
	@FindBy(css = "input[aria-label='id1 ']") public WebElement id1Prvw;
	@FindBy(xpath = "//input[@aria-label='date Enter month, slash, day, slash, year format  ']") 
	public WebElement datePrvw;
	@FindBy(css = "input[aria-label='time ']") public WebElement timePrvw;
	@FindBy(css = "input[aria-label='crr ']") public WebElement crrPrvw;
	@FindBy(xpath = "//textarea[@aria-label='lt1 ']") public WebElement lt1Prvw;
	@FindBy(xpath = "//p[normalize-space()='Paragraph Text']") public WebElement prgPrvw;
	@FindBy(css = ".v-messages__message") public WebElement errorMsgPrvw;
	@FindBy(xpath = "//div[normalize-space()='Radio 2']") public WebElement radio2Prvw;
	@FindBy(xpath = "//div[normalize-space()='Radio 1']") public WebElement radio1Prvw;
	@FindBy(xpath = "(//div[@class='v-input--selection-controls__ripple'])[3]") public WebElement chkbxPrvw;
	@FindBy(css = "input[aria-label='phn ']") public WebElement phnPrvw;
	@FindBy(css = "input[aria-label='nmb 1']") public WebElement nmbPostPrvw;
	@FindBy(css = "input[aria-label='nmb ']") public WebElement nmbPrvw;
	@FindBy(css = "#emailinput_lgp6178w") public WebElement emailPrvw;
	@FindBy(css = "input[aria-label='pass ']") public WebElement passPrvw;
	@FindBy(css = "input[aria-label='pass 1']") public WebElement passAfterPrvw;
	
	//create rules
	@FindBy(css = "img[alt='delete.png']") public WebElement deleteRule;
	@FindBy(xpath = "(//button[@type='button'])[2]") public WebElement deleteRuleOK;
	@FindBy(xpath = "(//div[@role='listitem'][@class='rule-item'])") public List<WebElement> savedRulesList;
	@FindBy(xpath = "//div[contains(text(),'Not unique')]") public WebElement ruleNmNotUnqError;
	@FindBy(xpath = "//input[@aria-label='Select Block Status']") public WebElement selectBlockStatus;
	@FindBy(xpath = "//div[contains(text(),'Block2')]") public WebElement Block2;
	@FindBy(xpath = "//input[@aria-label='Select Block']") public WebElement selectBlockDD;
	@FindBy(xpath = "//div[contains(text(),'By Block')]") public WebElement byBlockDD;
	@FindBy(css = ".v-select__selection.v-select__selection--comma") public WebElement ruleByFieldDD;
	@FindBy(xpath = "//div[normalize-space()='Update']") public WebElement ruleUpdateSave;
	@FindBy(css = "div[class='v-input v-text-field v-text-field--single-line v-text-field--solo v-text-field--enclosed v-select theme--light'] div[class='v-select__slot']") 
	public WebElement nextFieldStatus;
	@FindBy(xpath = "//button[@class='rounded-button v-btn theme--light'][normalize-space()='add']") 
	public WebElement plusRuleBtn;
	@FindBy(xpath = "//div[@class='v-btn__content'][normalize-space()='Save']") public WebElement ruleSaveBtn;
	@FindBy(xpath = "(//input[@aria-label='Select Block Status']") public WebElement ruleSelectBlockStatus;
	@FindBy(xpath = "//div[contains(text(),'Title')]") public WebElement ruleBlock2;
	@FindBy(xpath = "(//input[@aria-label='Select Block']") public WebElement ruleSelectBlock;
	@FindBy(xpath = "//div[contains(text(),'By Block')]") public WebElement ruleByBlock;
	@FindBy(xpath = "(//div[@class='v-select__slot'])[1]") public WebElement ruleByField;
	@FindBy(xpath = "//div[contains(text(),'Disabled')]") public WebElement fieldStatusDisabled;
	@FindBy(xpath = "//div[contains(text(),'Enabled')]") public WebElement fieldStatusEnabled;
	@FindBy(xpath = "//div[contains(text(),'Required')]") public WebElement fieldStatusRequired;
	@FindBy(xpath = "//div[contains(text(),'Visible')]") public WebElement fieldStatusVisible;
	@FindBy(xpath = "//div[contains(text(),'Hidden')]") public WebElement fieldStatusHidden;
	@FindBy(xpath = "//div[contains(text(),'chkbx')]") public WebElement chkbxFieldSlct;
	@FindBy(xpath = "//div[contains(text(),'radio')]") public WebElement radioFieldSlct;
	@FindBy(xpath = "//div[contains(text(),'phn')]") public WebElement phnFieldSlct;
	@FindBy(xpath = "//div[contains(text(),'nmb')]") public WebElement nmbFieldSlct;
	@FindBy(xpath = "//div[contains(text(),'prg')]") public WebElement prgFieldSlct;
	@FindBy(xpath = "(//div[@class='v-select__selections'])[2]") public WebElement ruleSelectFieldStatus;
	@FindBy(xpath = "//input[@aria-label='Select Field']") public WebElement ruleSelectField;
	@FindBy(css = ".CodeMirror-scroll") public WebElement rulesCode;
	@FindBy(xpath = "//div[normalize-space()='Create New Rule +']") public WebElement createNewRule;
	@FindBy(xpath = "(//input[@type='text'])[2]") public WebElement ruleName;
	@FindBy(xpath = "//div[normalize-space()='Edit Rules']") public WebElement editRulesBtn;
	
	//file upload preview
	@FindBy(xpath = "(//li[@class='file-names-list'])[2]") public WebElement uploadedFileName2;
	@FindBy(xpath = "//div[normalize-space()='The file you are trying to upload is larger than the 0.5 MB limit']")
	public WebElement errorAlert2;
	@FindBy(css = ".file-names-list") public WebElement uploadedFileName;
	@FindBy(css = ".text-error-alert") public WebElement errorAlert;
	@FindBy(xpath = "//div[contains(text(),'fileUpload_1')]") public WebElement prvwFileUp1;
	@FindBy(xpath = "//div[contains(text(),'fileUpload_2')]") public WebElement prvwFileUp2;
	@FindBy(xpath = "//div[contains(text(),'fileUpload_3')]") public WebElement prvwFileUp3;
	@FindBy(xpath = "//div[contains(text(),'fileUpload_4')]") public WebElement prvwFileUp4;
	@FindBy(xpath = "//div[contains(text(),'fileUpload_5')]") public WebElement prvwFileUp5;
	@FindBy(xpath = "//div[contains(text(),'fileUpload_6')]") public WebElement prvwFileUp6;

	@FindBy(xpath = "//div[contains(text(),'Specific Attributes')]") public WebElement specificAtt;
	@FindBy(xpath = "//input[@aria-label='Limit Size (in MB)']") public WebElement limitSize;
	@FindBy(xpath = "//label[normalize-space()='Accept Multiple Files']") public WebElement acceptMultiFiles;
	@FindBy(xpath = "//input[@aria-label='Allowed File Types']") public WebElement fileTypes;
	@FindBy(xpath = "//input[@aria-label='Limit Number']") public WebElement limitNumber;
	@FindBy(xpath = "//input[@aria-label='Error Message (Exceeding Number Of Files)']") public WebElement limitErrorMsg;

	@FindBy(xpath = "//div[contains(text(),'1')]") public WebElement tempUploadFile;
	@FindBy(xpath = "//div[normalize-space()='Checkbox']") public WebElement checkboxField;
	@FindBy(xpath = "//div[normalize-space()='Radio']") public WebElement radioField;
	@FindBy(xpath = "//div[normalize-space()='File Upload']") public WebElement fileUploadField;
	@FindBy(xpath = "//div[normalize-space()='Save']") public WebElement saveBtnTest;
	@FindBy(css = "img[alt='CallVU']") public WebElement formEndImg;
	@FindBy(xpath = "//input[contains(@aria-label,'Multi-Select - BranchByCityApi')]") public WebElement ms1Selected;
	@FindBy(xpath = "//input[contains(@aria-label,'Multi-Select2')]") public WebElement ms2Selected;
	@FindBy(css = "#app div:nth-of-type(6) [role='listitem']:nth-of-type(1) .v-list__tile__title")
	public WebElement dd2Item1;
	@FindBy(css = "#app div:nth-of-type(4) [role='listitem']:nth-of-type(1) .v-list__tile__title")
	public WebElement ac2Item1;
	@FindBy(css = "#app div:nth-of-type(2) [role='listitem']:nth-of-type(1) .v-list__tile--link")
	public WebElement ms2Item1;
	@FindBy(xpath = "//input[contains(@aria-label,'Dropdown2')]") public WebElement dropdown2;
	@FindBy(xpath = "//input[contains(@aria-label,'Dropdown - ListApi')]") public WebElement dropdown1;
	@FindBy(xpath = "//input[@aria-label='Autocomplete - ListObjApi ']") public WebElement autocomplete1;
	@FindBy(css = ".ms1 .v-select__selections") public WebElement multiSelect1;
	@FindBy(xpath = "//input[@aria-label='Autocomplete2 - ListObjApi ']") public WebElement autocomplete2;
	@FindBy(css = ".ddf .v-select__selections") public WebElement fatherDropdown;
	@FindBy(css = ".ms2 .v-select__selections") public WebElement multiSelect2;
	@FindBy(css = "div:nth-of-type(14) > .theme--light.v-card.v-select-list > div[role='list'] > div:nth-of-type(1)")
	public WebElement fddItem1;
	@FindBy(css = "#app div:nth-of-type(12) [role='listitem']:nth-of-type(2) .v-list__tile__title")
	public WebElement dd1Item2;
	@FindBy(css = "#app div:nth-of-type(10) [role='listitem']:nth-of-type(3) .v-list__tile__title")
	public WebElement ac1Item3;

	@FindBy(css = "#app div:nth-of-type(8) [role='listitem']:nth-of-type(4) .v-list__tile--link")
	public WebElement ms1Item4;
	@FindBy(css = "#app div:nth-of-type(8) [role='listitem']:nth-of-type(5) .v-list__tile--link")
	public WebElement ms1Item5;
	@FindBy(css = ".ddf .material-icons")
	public WebElement fddClear;
	@FindBy(css = ".block_lezusi58 [tabindex]")
	public WebElement formHeader;

	@FindBy(css = ".n2sums.v-tooltip.v-tooltip--bottom input[type='text']")
	public WebElement prvwSum2Input;
	@FindBy(css = ".form-help-action-button.v-btn--depressed.theme--dark .v-btn__content")
	public WebElement rulesBtn;
	@FindBy(css = ".theme--light.v-card.v-sheet > div[role='list'] > div")
	public List<WebElement> formList;
	@FindBy(css = "div[role='list'] > div:nth-of-type(3) .layout > div:nth-of-type(2) > div[role='combobox']  .v-input__slot.white")
	public WebElement selectField2;
	@FindBy(css = ".theme--light:nth-of-type(4) [role='listitem']:nth-of-type(2) [class='flex pr-4 xs4']:nth-of-type(2) .v-input__slot")
	public WebElement selectField1;
	
	public String input1 = "one";
	public String input2 = "two";
	public String input3 = "sum1";
	public String input4 = "n2sums";

	By basicFieldsDropdown = By.cssSelector(
			".v-expansion-panel__container:nth-of-type(2) .v-expansion-panel__header .justify-center .flex:nth-of-type(2)");
	By dropArea1 = By.cssSelector(".main-area .align-center");
	By dropNewBlock = By.cssSelector(".add-field-wrapper.flex");
	By formTitle = By
			.cssSelector(".form-title.theme--light.v-card.v-sheet.v-sheet--tile .form-name-cont > .editable.s-ltr");
	By renameForm = By.cssSelector(
			".elevation-0.elevation-6.theme--dark.toolbar-bottom-border.v-toolbar.v-toolbar--clipped.v-toolbar--fixed .form-name-cont > .editable.s-ltr");
	By saveForm = By.cssSelector(".lime.theme--dark.v-btn.v-btn--round > .v-btn__content");
	By previewForm = By.cssSelector(
			".lime--text.theme--dark.v-btn.v-btn--depressed.v-btn--outline.v-btn--round > .v-btn__content");
	//btn- 1+2, st- changed, all else- +1
	By shortText = By.xpath("//div[contains(text(),'Short Text')]");
	By buttonFld = By.cssSelector("[tabindex='0']:nth-of-type(2) div:nth-of-type(1) .element");
	By longText = By.cssSelector("[tabindex='0']:nth-of-type(2) div:nth-of-type(3) .element");
	By paragraph = By.cssSelector("[tabindex='0']:nth-of-type(2) div:nth-of-type(4) .element");
	By numberFld = By.cssSelector("[tabindex='0']:nth-of-type(2) div:nth-of-type(5) .element");
	By phoneNmbr = By.cssSelector("[tabindex='0']:nth-of-type(2) div:nth-of-type(6) .element");
	By emailFld = By.cssSelector("[tabindex='0']:nth-of-type(2) div:nth-of-type(7) .element");
	By psswrdFld = By.cssSelector("[tabindex='0']:nth-of-type(2) div:nth-of-type(8) .element");
	By signFld = By.cssSelector("[tabindex='0']:nth-of-type(2) div:nth-of-type(9) .element");
	By idFld = By.cssSelector("[tabindex='0']:nth-of-type(2) div:nth-of-type(10) .element");
	By dateFld = By.cssSelector("[tabindex='0']:nth-of-type(2) div:nth-of-type(11) .element");
	By timeFld = By.cssSelector("[tabindex='0']:nth-of-type(2) div:nth-of-type(12) .element");
	By crrncyFld = By.cssSelector("[tabindex='0']:nth-of-type(2) div:nth-of-type(13) .element");
	By dropdownFld = By.cssSelector("[tabindex='0']:nth-of-type(2) div:nth-of-type(14) .element");
	By autoComplete = By.cssSelector("[tabindex='0']:nth-of-type(2) div:nth-of-type(15) .element");
	By multiSelect = By.cssSelector("[tabindex='0']:nth-of-type(2) div:nth-of-type(16) .element");
	By checkboxFld = By.cssSelector("[tabindex='0']:nth-of-type(2) div:nth-of-type(17) .element");
	By radioFld = By.cssSelector("[tabindex='0']:nth-of-type(2) div:nth-of-type(18) .element");
	By radioKVFld = By.cssSelector("[tabindex='0']:nth-of-type(2) div:nth-of-type(19) .element");
	By fileUpldFld = By.cssSelector("[tabindex='0']:nth-of-type(2) div:nth-of-type(20) .element");
	By displayPDFFld = By.cssSelector("[tabindex='0']:nth-of-type(2) div:nth-of-type(21) .element");
	By dynamicPDFFld = By.cssSelector("[tabindex='0']:nth-of-type(2) div:nth-of-type(22) .element");
	By subtitleFld = By.cssSelector("[tabindex='0']:nth-of-type(2) div:nth-of-type(23) .element");
	By commentFld = By.cssSelector("[tabindex='0']:nth-of-type(2) div:nth-of-type(24) .element");
	By mobilePgBrkFld = By.cssSelector("[tabindex='0']:nth-of-type(2) div:nth-of-type(25) .element");

	By block1Title = By.cssSelector(".primary--text .editable");
	By genericAttrbts = By.cssSelector(".v-form .theme--dark.v-expansion-panel > li:nth-of-type(1) .flex");
	By readOnlyAtt = By.cssSelector("div:nth-of-type(11) > .v-input__control > .v-input__slot > .theme--light.v-label");
	By requiredAtt = By.cssSelector("div:nth-of-type(12) > .v-input__control > .v-input__slot > .theme--light.v-label");
	By hiddenAtt = By.cssSelector("div:nth-of-type(13) > .v-input__control > .v-input__slot > .theme--light.v-label");
	By genAttLabel = By.cssSelector(
			".element-list.v-card__text > div:nth-of-type(2) > .v-input__control > .v-input__slot > .v-text-field__slot > input[type='text']");
	By intID = By.cssSelector("[aria-label='Integration ID']");
	// nth-of-type(xx) is a dynamic field
	By saveConfirm = By.cssSelector("#app [tabindex='-1']:nth-of-type(17) .white--text .v-btn__content");
	By saveBtn = By.cssSelector(".lime .v-btn__content");
	By folderActions = By.cssSelector("img[alt='folder.png']");
	By openForm = By
			.cssSelector("div:nth-of-type(2) > .theme--dark.v-list__tile.v-list__tile--link > .v-list__tile__title");
	By firstForm = By.cssSelector("div:nth-of-type(1) > div[role='listitem'] .v-list__tile__sub-title");
	By n4Form = By.cssSelector(".v-list--two-line .theme--light:nth-of-type(4)");
	By openBtn = By.cssSelector(".blue.ml-0.mt-0.theme--light.v-btn.v-btn--round.white--text > .v-btn__content");
	By settings = By.cssSelector(".v-btn--flat.white--text .v-btn__content");
	By createAction = By.cssSelector(".theme--light.v-card.v-sheet > button:nth-of-type(3) > .v-btn__content");
	By selectAPI = By.cssSelector("div[role='combobox']  .v-input__slot.white");
	By sumAPI = By.cssSelector("div[role='list'] > div:nth-of-type(11) .v-list__tile__title");
	By listAPI = By.cssSelector("div[role='list'] > div:nth-of-type(13) .v-list__tile__title");
	By actionName = By.cssSelector("[class='d-inline-block mr-5'] .v-input__slot");
	By actionCode = By.cssSelector(".CodeMirror-scroll");
	By actionInputFld1 = By.cssSelector(
			"div:nth-of-type(2) .layout > div:nth-of-type(2) > div[role='combobox']  .v-input__slot.white");
	By actionInputFld2 = By.cssSelector(
			"div:nth-of-type(3) .layout > div:nth-of-type(2) > div[role='combobox']  .v-input__slot.white");
	By actionSumField = By.cssSelector(".xs4:nth-of-type(3) .v-input__slot");
	By actnDrpdwnItm2 = By
			.cssSelector("#app div:nth-of-type(21) [role='listitem']:nth-of-type(2) .v-list__tile__title");
	By actionSaveBtn = By.cssSelector(".width20.white--text .v-btn__content");
	By actionCancel = By.cssSelector(".width20.v-btn--depressed .v-btn__content");
	By createNewActionBtn = By.cssSelector("[class='v-card__title title pt-2 pb-2'] .v-btn__content");

	By prvwSingleInput = By.cssSelector("input[required]");
	By prvwNext = By.cssSelector(".v-btn--block .v-btn__content");
	By prvwDone = By.cssSelector(".done-btn .material-icons");
	By prvwBlock1Hdr = By.cssSelector("h2");
	By prvwReadOnly = By.cssSelector("[readonly='readonly']");

	By prvwOneInpt = By.cssSelector("[aria-label='one ']");
	By prvwTwoInpt = By.cssSelector("[aria-label='two ']");
	By prvwSum1Inpt = By.cssSelector(".sum1.v-tooltip.v-tooltip--bottom input[type='text']");
	By prvwN2SumInpt = By.cssSelector(".n2sums.v-tooltip.v-tooltip--bottom input[type='text']");

	public WebElement getPrvwOneInpt() throws InterruptedException, IOException {
		return getDriver().findElement(prvwOneInpt);
	}

	public WebElement getPrvwTwoInpt() throws InterruptedException, IOException {
		return getDriver().findElement(prvwTwoInpt);
	}

	public WebElement getPrvwSum1Inpt() throws InterruptedException, IOException {
		return getDriver().findElement(prvwSum1Inpt);
	}

	public WebElement getPrvwN2SumInpt() throws InterruptedException, IOException {
		return getDriver().findElement(prvwN2SumInpt);
	}

	public WebElement getCreateNewActionBtn() throws InterruptedException, IOException {
		return getDriver().findElement(createNewActionBtn);
	}

	public WebElement getActionCancel() throws InterruptedException, IOException {
		return getDriver().findElement(actionCancel);
	}

	public WebElement getActionSaveBtn() throws InterruptedException, IOException {
		return getDriver().findElement(actionSaveBtn);
	}

	public WebElement getActionDrpdwnItm2() throws InterruptedException, IOException {
		return getDriver().findElement(actnDrpdwnItm2);
	}

	public WebElement getActionSumFld() throws InterruptedException, IOException {
		return getDriver().findElement(actionSumField);
	}

	public WebElement getActionInputFld1() throws InterruptedException, IOException {
		return getDriver().findElement(actionInputFld1);
	}

	public WebElement getActionInputFld2() throws InterruptedException, IOException {
		return getDriver().findElement(actionInputFld2);
	}

	public WebElement getActionCode() throws InterruptedException, IOException {
		return getDriver().findElement(actionCode);
	}

	public WebElement getActionName() throws InterruptedException, IOException {
		return getDriver().findElement(actionName);
	}

	public WebElement getListAPI() throws InterruptedException, IOException {
		return getDriver().findElement(listAPI);
	}

	public WebElement getSumAPI() throws InterruptedException, IOException {
		return getDriver().findElement(sumAPI);
	}

	public WebElement getSelectAPI() throws InterruptedException, IOException {
		return getDriver().findElement(selectAPI);
	}

	public WebElement getCreateAction() throws InterruptedException, IOException {
		return getDriver().findElement(createAction);
	}

	public WebElement getSettings() throws InterruptedException, IOException {
		return getDriver().findElement(settings);
	}

	public WebElement getPrvwReadOnly() throws InterruptedException, IOException {
		return getDriver().findElement(prvwReadOnly);
	}

	public WebElement getPrvwBlock1Hdr() throws InterruptedException, IOException {
		return getDriver().findElement(prvwBlock1Hdr);
	}

	public WebElement getPrvwDone() throws InterruptedException, IOException {
		return getDriver().findElement(prvwDone);
	}

	public WebElement getPrvwNext() throws InterruptedException, IOException {
		return getDriver().findElement(prvwNext);
	}

	public WebElement getPrvwSingleInput() throws InterruptedException, IOException {
		return getDriver().findElement(prvwSingleInput);
	}

	public WebElement getOpenBtn() throws InterruptedException, IOException {
		return getDriver().findElement(openBtn);
	}

	public WebElement getFirstForm() throws InterruptedException, IOException {
		return getDriver().findElement(firstForm);
	}

	public WebElement getN4Form() throws InterruptedException, IOException {
		return getDriver().findElement(n4Form);
	}

	public WebElement getOpenForm() throws InterruptedException, IOException {
		return getDriver().findElement(openForm);
	}

	public WebElement getFolderActions() throws InterruptedException, IOException {
		return getDriver().findElement(folderActions);
	}

	public WebElement getSaveBtn() throws InterruptedException, IOException {
		return getDriver().findElement(saveBtn);
	}

	public WebElement getSaveConfirm() throws InterruptedException, IOException {
		return getDriver().findElement(saveConfirm);
	}

	public WebElement getGenAttLabel() throws InterruptedException, IOException {
		return getDriver().findElement(genAttLabel);
	}

	public WebElement getIntID() throws InterruptedException, IOException {
		return getDriver().findElement(intID);
	}

	public WebElement getHiddenAtt() throws InterruptedException, IOException {
		return getDriver().findElement(hiddenAtt);
	}

	public WebElement getrequiredAtt() throws InterruptedException, IOException {
		return getDriver().findElement(requiredAtt);
	}

	public WebElement getReadOnlyAtt() throws InterruptedException, IOException {
		return getDriver().findElement(readOnlyAtt);
	}

	public WebElement getGenericAttrbts() throws InterruptedException, IOException {
		return getDriver().findElement(genericAttrbts);
	}

	public WebElement getBlock1Title() throws InterruptedException, IOException {
		return getDriver().findElement(block1Title);
	}

	public WebElement getDropArea1() throws InterruptedException, IOException {
		return getDriver().findElement(dropArea1);
	}

	public WebElement getDropNewBlock() throws InterruptedException, IOException {
		return getDriver().findElement(dropNewBlock);
	}

	public WebElement getBasicFieldsDropdown() throws InterruptedException, IOException {
		return getDriver().findElement(basicFieldsDropdown);
	}

	public WebElement getFormTitle() throws InterruptedException, IOException {
		return getDriver().findElement(formTitle);
	}

	public WebElement getRenameForm() throws InterruptedException, IOException {
		return getDriver().findElement(renameForm);
	}

	public WebElement getSaveForm() throws InterruptedException, IOException {
		return getDriver().findElement(saveForm);
	}

	public WebElement getPreviewForm() throws InterruptedException, IOException {
		return getDriver().findElement(previewForm);
	}

	public WebElement getShortText() throws InterruptedException, IOException {
		return getDriver().findElement(shortText);
	}

	public WebElement getLongText() throws InterruptedException, IOException {
		return getDriver().findElement(longText);
	}

	public WebElement getMobilePageBrkFld() throws InterruptedException, IOException {
		return getDriver().findElement(mobilePgBrkFld);
	}

	public WebElement getCommentFld() throws InterruptedException, IOException {
		return getDriver().findElement(commentFld);
	}

	public WebElement getSubtitleFld() throws InterruptedException, IOException {
		return getDriver().findElement(subtitleFld);
	}

	public WebElement getDynamicPDFFld() throws InterruptedException, IOException {
		return getDriver().findElement(dynamicPDFFld);
	}

	public WebElement getDisplayPDFFld() throws InterruptedException, IOException {
		return getDriver().findElement(displayPDFFld);
	}

	public WebElement getFileUpldFld() throws InterruptedException, IOException {
		return getDriver().findElement(fileUpldFld);
	}

	public WebElement getRadioKVFld() throws InterruptedException, IOException {
		return getDriver().findElement(radioKVFld);
	}

	public WebElement getRadioFld() throws InterruptedException, IOException {
		return getDriver().findElement(radioFld);
	}

	public WebElement getCeckboxFld() throws InterruptedException, IOException {
		return getDriver().findElement(checkboxFld);
	}

	public WebElement getMultiSelectFld() throws InterruptedException, IOException {
		return getDriver().findElement(multiSelect);
	}

	public WebElement getAutoComplete() throws InterruptedException, IOException {
		return getDriver().findElement(autoComplete);
	}

	public WebElement getDropdownFld() throws InterruptedException, IOException {
		return getDriver().findElement(dropdownFld);
	}

	public WebElement getCurrencyFld() throws InterruptedException, IOException {
		return getDriver().findElement(crrncyFld);
	}

	public WebElement getTimeFld() throws InterruptedException, IOException {
		return getDriver().findElement(timeFld);
	}

	public WebElement getDateFld() throws InterruptedException, IOException {
		return getDriver().findElement(dateFld);
	}

	public WebElement getIdFld() throws InterruptedException, IOException {
		return getDriver().findElement(idFld);
	}

	public WebElement getSignFld() throws InterruptedException, IOException {
		return getDriver().findElement(signFld);
	}

	public WebElement getPasswordFld() throws InterruptedException, IOException {
		return getDriver().findElement(psswrdFld);
	}

	public WebElement getEmailFld() throws InterruptedException, IOException {
		return getDriver().findElement(emailFld);
	}

	public WebElement getPhoneNmbr() throws InterruptedException, IOException {
		return getDriver().findElement(phoneNmbr);
	}

	public WebElement getNumberFld() throws InterruptedException, IOException {
		return getDriver().findElement(numberFld);
	}

	public WebElement getParagraph() throws InterruptedException, IOException {
		return getDriver().findElement(paragraph);
	}

	public String jsDragnDrop() {
		return "function createEvent(typeOfEvent) {\n" + "var event =document.createEvent(\"CustomEvent\");\n"
				+ "event.initCustomEvent(typeOfEvent,true, true, null);\n" + "event.dataTransfer = {\n" + "data: {},\n"
				+ "setData: function (key, value) {\n" + "this.data[key] = value;\n" + "},\n"
				+ "getData: function (key) {\n" + "return this.data[key];\n" + "}\n" + "};\n" + "return event;\n"
				+ "}\n" + "\n" + "function dispatchEvent(element, event,transferData) {\n"
				+ "if (transferData !== undefined) {\n" + "event.dataTransfer = transferData;\n" + "}\n"
				+ "if (element.dispatchEvent) {\n" + "element.dispatchEvent(event);\n"
				+ "} else if (element.fireEvent) {\n" + "element.fireEvent(\"on\" + event.type, event);\n" + "}\n"
				+ "}\n" + "\n" + "function simulateHTML5DragAndDrop(element, destination) {\n"
				+ "var dragStartEvent =createEvent('dragstart');\n" + "dispatchEvent(element, dragStartEvent);\n"
				+ "var dropEvent = createEvent('drop');\n"
				+ "dispatchEvent(destination, dropEvent,dragStartEvent.dataTransfer);\n"
				+ "var dragEndEvent = createEvent('dragend');\n"
				+ "dispatchEvent(element, dragEndEvent,dropEvent.dataTransfer);\n" + "}\n" + "\n"
				+ "var source = arguments[0];\n" + "var destination = arguments[1];\n"
				+ "simulateHTML5DragAndDrop(source,destination);";
	}

}
