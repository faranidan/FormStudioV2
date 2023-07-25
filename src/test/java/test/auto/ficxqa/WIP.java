package test.auto.ficxqa;

import java.io.IOException;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.Hooks;
import pageObjects.FormEditorObjects;

@Listeners(Resources.Listeners.class)

public class WIP extends Hooks {
    public WIP() throws IOException{
        super();
    }

    @Test
	public void testGetUrl() throws IOException, InterruptedException {
		FormEditorObjects forms = new FormEditorObjects();
        forms.openSavedForm("AutoButtonStepTest", false);
        System.out.println(forms.getCurrentUrl()); 
        forms.previewForm.click();
		forms.changeTab(1, false);
        String url = forms.getCurrentUrl();
        System.out.println(url); 
        url = url.replace("LAN", "DMZ");
        System.out.println(url); 
        forms.changeTab(1, true);
        System.out.println(forms.getCurrentUrl());
    }

    @Test
    public void previewButtons() throws InterruptedException, IOException{
        FormEditorObjects forms = new FormEditorObjects();
        try{
            forms.openSavedForm("AutoButtonStepTest", true);
            forms.testBtns1();
            forms.testBtns2();
            forms.testBtns3();
            forms.testBtns4();
            forms.testBtns5();
        } catch (NoSuchElementException | ElementClickInterceptedException | TimeoutException e){
            System.out.println("There was a test failure under known exceptions.");
            e.printStackTrace();
        } finally {
            System.out.println("Test previewButtons ended.");
        }
    }

}

