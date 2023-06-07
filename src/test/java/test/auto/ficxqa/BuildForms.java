package test.auto.ficxqa;

import java.io.IOException;
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
    public void bulidFileUpload() throws InterruptedException, IOException{
        ExtentManager.log("Starting bulidFileUpload test...");
        System.out.println("Test bulidFileUpload started");
        FormEditorObjects forms = new FormEditorObjects();
        forms.renameFormTitleBlock("AutoFileUpload", "Upload files test", "block1");
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
        System.out.println("Test bulidFileUpload ended successfully.");
    }

	@Test
	public void buildSumApi() throws InterruptedException, IOException {
		FormEditorObjects forms = new FormEditorObjects();
		ExtentManager.log("Staring buildSumApi test...");
        System.out.println("Test buildSumApi started");
		forms.renameFormTitleBlock("Auto SumAPI", "Page1", "Block1");
		forms.addField(forms.getShortText(), forms.input4);
		forms.addField(forms.getShortText(), forms.input3);
		forms.addField(forms.getShortText(), forms.input2);
		forms.addField(forms.getShortText(), forms.input1);

		forms.createAction();
		forms.createSumApi("SUM-1", "one != '' && two != ''", forms.input1, forms.input2, forms.input3);
		forms.newSumActionISA();
		forms.createSumApi("SUM-2", "sum1 != ''", forms.input3, forms.input3, forms.input4);

		forms.getActionCancel().click();
		forms.saveForm();
        System.out.println("Test buildSumApi ended successfully.");

	}

	@Test
    public void buildBasicFields() throws IOException, InterruptedException{
        ExtentManager.log("Starting buildBasicFields test...");
        FormEditorObjects forms = new FormEditorObjects();
        System.out.println("Test buildBasicFields started");
        forms.renameFormTitleBlock("AutoRulesBasicFields", "Auto Rules w. BasicFields", "Block1");
        forms.addField(forms.getParagraph(), "prg");
        forms.addField(forms.getNumberFld(), "nmb");
        forms.addField(forms.getPhoneNmbr(), "phn");
        forms.addField(forms.radioField, "radio");
        forms.addField(forms.checkboxField, "chkbx");
        forms.addField(forms.getEmailFld(), "email");
        forms.addField(forms.getPasswordFld(), "pass");
        forms.addStep.click();
        forms.renameFormTitleBlock("AutoRulesBasicFields", "Auto Rules w. BasicFields", "Block2");
        forms.addField(forms.getIdFld(), "id1");
        forms.addField(forms.getDateFld(), "date");
        forms.addField(forms.getTimeFld(), "time");
        forms.addField(forms.getCurrencyFld(), "crr");
        forms.addField(forms.getLongText(), "lt1");
        forms.saveForm();
        System.out.println("Test buildBasicFields ended successfully.");
    }

    @Test
    public void buildRules() throws IOException, InterruptedException{
        ExtentManager.log("Starting buildRules test...");
        FormEditorObjects forms = new FormEditorObjects();
        System.out.println("Test buildRules started");
        forms.openSavedForm("AutoRulesBasicFields");

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

        forms.getActionCancel().click();
		forms.saveForm();
        System.out.println("Test buildRules ended successfully.");
    }

    @Test
    public void createSteps() throws InterruptedException, IOException{
        ExtentManager.log("Starting createSteps test...");
        System.out.println("Test createSteps started");
        FormEditorObjects forms = new FormEditorObjects();
        forms.renameFormTitleBlock("AutoSteps", "Automated Steps test form", "Block1");
        forms.editStep(forms.step1, "2", "", true);
        forms.editStep(forms.currentStep, "3", "1", true);
        forms.editStep(forms.currentStep, "4", "2", true);
        forms.editStep(forms.currentStep, "Finish", "3", false);
        forms.saveForm();
        System.out.println("Test createSteps ended successfully.");
    }
}
