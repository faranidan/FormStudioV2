package test.auto.ficxqa;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.TimeoutException;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.ExtentManager;
import base.Hooks;
import pageObjects.FormEditorObjects;

@Listeners(Resources.Listeners.class)

public class PreviewForms extends Hooks {

	public PreviewForms() throws IOException {
		super();
	}

	@Test
	public void previewfileUpload() throws InterruptedException, IOException {
		FormEditorObjects forms = new FormEditorObjects();
		try {
			System.out.println("Test previewfileUpload started");
			forms.openSavedForm("AutoFileUpload");
			forms.getPreviewForm().click();
			ExtentManager.pass("Opened form: AutoFileUpload & clicked Preview");
			forms.switchTab();
			ExtentManager.pass("Switched to preview tab");
			forms.previewUploadFiles1();
			forms.previewUploadFiles2();
			forms.previewUploadFiles3();
		} catch (NoSuchElementException | ElementClickInterceptedException | TimeoutException e) {
			System.out.println("There was a test failure under known exceptions.");
			e.printStackTrace();
		} finally {
			System.out.println("Test previewfileUpload ended.");
		}
	}

	@Test
	public void previewSum() throws InterruptedException, IOException {
		FormEditorObjects forms = new FormEditorObjects();
		try {
			ExtentManager.log("STARTING previwSum test...");
			System.out.println("Test previewSum started");
			forms.openSavedForm("Automation.v3");
			forms.getPreviewForm().click();
			forms.switchTab();
			forms.devTools("https://qa19.callvu.net/LAN/APIGateway/CallAPI?Name=Sum");
			forms.previewSum(12, 12);
			forms.previewDone();
		} catch (NoSuchElementException | ElementClickInterceptedException | TimeoutException e) {
			System.out.println("There was a test failure under known exceptions.");
			e.printStackTrace();
		} finally {
			System.out.println("Test previewSum ended.");
		}
	}

	@Test
	public void previewDropdown() throws IOException, InterruptedException {
		FormEditorObjects forms = new FormEditorObjects();
		try {
			ExtentManager.log("STARTING previwDropdown test...");
			System.out.println("Test previewDropdown started");
			forms.openSavedForm("idanActionsDD[4autoTest]");
			forms.getPreviewForm().click();
			forms.switchTab();
			forms.dropdownFill1();
			forms.dropdownFill2();
			forms.dropdownSelectedFieldClear();
			forms.dropdownDataClear1();
			forms.dropdownDataClear2();
		} catch (NoSuchElementException | ElementClickInterceptedException | TimeoutException e) {
			System.out.println("There was a test failure under known exceptions.");
			e.printStackTrace();
		} finally {
			System.out.println("Test previewDropdown ended.");
		}
	}

	@Test
	public void previewRules() throws IOException, InterruptedException {
		FormEditorObjects forms = new FormEditorObjects();
		try {
			ExtentManager.log("STARTING previewRules test...");
			System.out.println("Test previewRules Started");
			forms.openSavedForm("AutoRulesBasicFields");
			forms.getPreviewForm().click();
			forms.switchTab();
			forms.prvwRulesStep1();
			forms.prvwRulesStep2();
			forms.prvwRulesStep3();
			forms.prvwRulesStep4();
		} catch (NoSuchElementException | ElementClickInterceptedException | TimeoutException e) {
			System.out.println("There was a test failure under known exceptions.");
			e.printStackTrace();
		} finally {
			System.out.println("Test previewRules ended.");
		}
	}

	@Test
	public void previewSteps() throws IOException, InterruptedException {
		FormEditorObjects forms = new FormEditorObjects();
		try {
			ExtentManager.log("STARTING previewSteps test...");
			System.out.println("Test previewSteps Started");
			forms.openSavedForm("AutoSteps");
			forms.getPreviewForm().click();
			forms.switchTab();
			forms.testStepName(forms.nextStepBtnPrvw, "2");
			forms.nextStepBtnPrvw.click();
			Thread.sleep(600);
			forms.testStepName(forms.backStepBtnPrvw, "1");
			forms.testStepName(forms.nextStepBtnPrvw, "3");
			forms.nextStepBtnPrvw.click();
			Thread.sleep(600);
			forms.testStepName(forms.backStepBtnPrvw, "2");
			forms.testStepName(forms.nextStepBtnPrvw, "4");
			forms.nextStepBtnPrvw.click();
			Thread.sleep(600);
			forms.testStepName(forms.backStepBtnPrvw, "3");
			forms.testLastStepName("Finish");
			forms.backStepBtnPrvw.click();
			Thread.sleep(600);
			forms.testStepName(forms.backStepBtnPrvw, "2");
			forms.testStepName(forms.nextStepBtnPrvw, "4");
		} catch (NoSuchElementException | ElementClickInterceptedException | TimeoutException e) {
			System.out.println("There was a test failure under known exceptions.");
			e.printStackTrace();
		} finally {
			System.out.println("Test previewRules ended.");
		}
	}

	@Test
	public void previewButtons() throws InterruptedException, IOException {
		FormEditorObjects forms = new FormEditorObjects();
		try {
			System.out.println("Test previewButtons started...");
			forms.openSavedForm("AutoButtonStepTest");
			forms.testBtns1();
			forms.testBtns2();
			forms.testBtns3();
			forms.testBtns4();
			forms.testBtns5();
		} catch (NoSuchElementException | ElementClickInterceptedException | TimeoutException e) {
			System.out.println("There was a test failure under known exceptions.");
			e.printStackTrace();
		} finally {
			System.out.println("Test previewButtons ended.");
		}
	}

}
