package test.auto.ficxqa;

import java.io.IOException;

import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.ExtentManager;
import base.Hooks;
import pageObjects.FormEditorObjects;

@Listeners(Resources.Listeners.class)

public class BuildForms extends Hooks {

    public BuildForms() throws IOException {
        super();
    }

    @Test
    public void bulidFileUpload() throws InterruptedException, IOException {
        ExtentManager.log("Starting bulidFileUpload test...");
        System.out.println("Test bulidFileUpload started");
        FormEditorObjects forms = new FormEditorObjects();
        try{
            forms.renameFormTitle("AutoFileUpload");
            forms.renameFormBlocks("Upload files test", "block1");
            ExtentManager.pass("Renamed form & titles");
            forms.addField(forms.fileUploadField, "fileUpload_3-multipleSizeLimit[pmg]");
            forms.specificAttUpload("0.5", "txt", "png", "2", "Too many!");
            ExtentManager.pass("Added field fileUpload3 with specific attributes");
            forms.addField(forms.fileUploadField, "fileUpload_2-multipleBadType[png]");
            forms.specificAttUpload("0.5", "txt", "json", "3", "Too many!");
            ExtentManager.pass("Added field fileUpload2 with specific attributes");
            forms.addField(forms.fileUploadField, "fileUpload_1-Empty");
            ExtentManager.pass("Added field fileUpload1");
            forms.addStep.click();
            forms.addField(forms.fileUploadField, "fileUpload_6-multipleLimit[txt]");
            forms.specificAttUpload("0.5", "txt", "", "1", "Too many!");
            ExtentManager.pass("Added step & field fileUpload6 with specific attributes");
            forms.addField(forms.fileUploadField, "fileUpload_5-multiEmpty");
            forms.specificAttUpload("0.5", "txt", "", "2", "Too many!");
            ExtentManager.pass("Added field fileUpload5 with specific attributes");
            forms.addField(forms.fileUploadField, "fileUpload_4-any");
            ExtentManager.pass("Added field fileUpload4 with specific attributes");
            forms.saveForm();
            ExtentManager.pass("Created form successfully & Saved");
            System.out.println("Test bulidFileUpload finished execution successfully.");
        } catch (NoSuchElementException | ElementNotInteractableException | TimeoutException e){
            System.out.println("Test bulidFileUpload failed with a known excepion");
            e.printStackTrace();
            Assert.fail("There was a test failure: "+e.getMessage());
        } finally {
            System.out.println("Test bulidFileUpload end.");
        }
    }

    @Test
    public void buildSumApi() throws InterruptedException, IOException {
        FormEditorObjects forms = new FormEditorObjects();
        ExtentManager.log("Staring buildSumApi test...");
        System.out.println("Test buildSumApi started");
        try{         
            forms.renameFormTitle("Auto SumAPI");
            forms.renameFormBlocks("Page1", "Block1");
            forms.addField(forms.shortTextFld, "four");
            forms.addField(forms.shortTextFld, "three");
            forms.addField(forms.shortTextFld, "two");
            forms.addField(forms.shortTextFld, "one");
            forms.createAction();
            forms.createSumApi("SUM-1", "one && two", "one", "two", "three");
            forms.newSumActionISA();
            forms.createSumApi("SUM-2", "three", "three", "three", "four");
            forms.actionCancelBtn.click();
            forms.saveForm();
            System.out.println("Test buildSumApi finished execution successfully.");
        } catch (NoSuchElementException | ElementNotInteractableException | TimeoutException e){
            System.out.println("Test buildBasicFields failed with a known excepion");
            e.printStackTrace();
            Assert.fail("There was a test failure: "+e.getMessage());
        } finally {
            System.out.println("Test buildSumApi end.");
        }
    }

    @Test
    public void buildBasicFields() throws IOException, InterruptedException {
        ExtentManager.log("Starting buildBasicFields test...");
        FormEditorObjects forms = new FormEditorObjects();
        System.out.println("Test buildBasicFields started");
        try{
            forms.renameFormTitle("AutoRulesBasicFields");
            forms.renameFormBlocks("Auto Rules w. BasicFields", "Block1");
            forms.addField(forms.prgrphFld, "prg");
            forms.addField(forms.numberFld, "nmb");
            forms.addField(forms.phoneFld, "phn");
            forms.addField(forms.radioFld, "radio");
            forms.addField(forms.checkboxFld, "chkbx");
            forms.addField(forms.emailFld, "email");
            forms.addField(forms.passwordFld, "pass");
            forms.addStep.click();
            forms.renameFormBlocks("Auto Rules w. BasicFields", "Block2");
            forms.addField(forms.idFld, "id1");
            forms.addField(forms.dateFld, "date");
            forms.addField(forms.timeFld, "time");
            forms.addField(forms.crrncyFld, "crr");
            forms.addField(forms.longTextFld, "lt1");
            forms.addField(forms.signFld, "sign");
            forms.saveForm();
            System.out.println("Test buildBasicFields finished execution successfully.");
        } catch (NoSuchElementException | ElementNotInteractableException | TimeoutException e){
            System.out.println("Test buildBasicFields failed with a known excepion");
            e.printStackTrace();
            Assert.fail("There was a test failure: "+e.getMessage());
        } finally {
            System.out.println("Test buildBasicFields end.");
        }
    }

    @Test
    public void buildRules() throws IOException, InterruptedException {
        ExtentManager.log("Starting buildRules test...");
        FormEditorObjects forms = new FormEditorObjects();
        System.out.println("Test buildRules started");
        forms.openSavedForm("AutoRulesBasicFields");
        try{
            forms.addNewRule("1", "pass");
            forms.ruleOutcome(forms.chkbxFieldSlct, forms.fieldStatusEnabled);
            forms.addOutcome(forms.fieldStatusDisabled);
            forms.addOutcome(forms.fieldStatusVisible);
            forms.addOutcome(forms.fieldStatusRequired);
            forms.addOutcome(forms.fieldStatusHidden);
            forms.addNewRule("2", "email");
            forms.ruleOutcome(forms.chkbxFieldSlct, forms.fieldStatusDisabled);
            forms.addOutcome(forms.fieldStatusEnabled);
            forms.addOutcome(forms.fieldStatusHidden);
            forms.addOutcome(forms.fieldStatusRequired);
            forms.addOutcome(forms.fieldStatusVisible);
            forms.addNewRule("off", "!phn");
            forms.addRuleByBlock(forms.Block2, forms.fieldStatusHidden);
            forms.addNewRule("on", "phn");
            forms.addRuleByBlock(forms.Block2, forms.fieldStatusVisible);
            forms.actionCancelBtnRules.click();
            forms.saveForm();
            System.out.println("Test buildRules ended successfully.");    
        } catch (NoSuchElementException | ElementNotInteractableException | TimeoutException e){
            System.out.println("Test buildRules failed with a known excepion");
            e.printStackTrace();
            Assert.fail("There was a test failure: "+e.getMessage());
        } finally {
            System.out.println("Test buildRules ended.");
        }
    }

    @Test
    public void createSteps() throws InterruptedException, IOException {
        ExtentManager.log("Starting createSteps test...");
        System.out.println("Test createSteps started");
        FormEditorObjects forms = new FormEditorObjects();
        try{
            forms.renameFormTitle("AutoSteps");
            forms.renameFormBlocks("Automated Steps test form", "Block1");
            forms.editStep(forms.step1, "2", "", true);
            forms.editStep(forms.currentStep, "3", "1", true);
            forms.editStep(forms.currentStep, "4", "2", true);
            forms.editStep(forms.currentStep, "Finish", "3", false);
            forms.saveForm();
            System.out.println("Test createSteps ended successfully.");
        } catch (NoSuchElementException | ElementNotInteractableException | TimeoutException e){
            System.out.println("Test createSteps failed with a known excepion");
            e.printStackTrace();
            Assert.fail("There was a test failure: "+e.getMessage());
        } finally {
            System.out.println("Test createSteps ended.");
        }
    }
}
