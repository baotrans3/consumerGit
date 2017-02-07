/**
https://spiratest.absolute.com/SpiraTest/7/TestCase/222684.aspx
https://spiratest.absolute.com/SpiraTest/7/TestCase/222655.aspx
https://spiratest.absolute.com/SpiraTest/7/TestCase/222657.aspx
https://spiratest.absolute.com/SpiraTest/7/TestCase/222749.aspx
https://spiratest.absolute.com/SpiraTest/7/TestCase/222658.aspx

 */
package com.absolute.customercenter.login;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import com.absolute.utils.readTestcaseFiles;
import com.absolute.utils.SerenityUtil;

import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import net.thucydides.junit.annotations.Qualifier;
import net.thucydides.junit.annotations.TestData;
/**
 * 
 * @author 
 *
 */
@RunWith(SerenityParameterizedRunner.class)
public class LoginTest {		
	 	
	    @Managed(uniqueSession = true)
	    WebDriver driver;
	    
	    Actor user = Actor.named("User");

	    @Steps
	    LoginSteps loginSteps;

	    @Before
	    public void setup() throws MalformedURLException {  	
	    		    	
	    	System.setProperty("webdriver.gecko.driver", SerenityUtil.getEnv("webdriver.firefoxDriver"));
	    	user.can(BrowseTheWeb.with(driver));
	    }

	    @Qualifier
	    public String qualifier() {
	        return dataEntity.getScenarioNote();
	    }

	    @TestData
	    public static Collection<Object[]> testData() throws IOException, InvalidFormatException {
	    	readTestcaseFiles ccData = new readTestcaseFiles();   
	    	LoginDataEntity entity = new LoginDataEntity();
	    	List<String[]> data = null;
	    	try{
	    		data = ccData.getTestData(SerenityUtil.getFullDataPath() + SerenityUtil.data4LOGIN() + SerenityUtil.getDataExtension(), SerenityUtil.getDataHeaderNo(), 1, "Y");
	    	}catch(Exception ex){
	    		System.out.println("Failed to read testcase files"+ex);
	    	}
	    	List<Object[]> entitys = new ArrayList<>();
	        for (String[] row : data) {
	            entity = LoginDataEntity.convertArrayToEntity(row);
	            LoginDataEntity[] ad = new LoginDataEntity[]{entity};            
	            entitys.add(ad);
	        }
	        return entitys;
	    }

	    
	 	private final LoginDataEntity dataEntity;
	 	
	    public LoginTest(LoginDataEntity dataEntity) {
	        this.dataEntity = dataEntity;
	    }

	    @Title("Verify login workflow")
	    @Test
	    public void verifyLoginWorkFlow() throws Exception {	       
	        loginSteps.gotoLogin();
	    	loginSteps.inputEmail(dataEntity.getUsername());
	        loginSteps.inputPwd(dataEntity.getPassword());
	        loginSteps.chooseLanguage();
	        loginSteps.clickLogin();
	        loginSteps.verifyExpectation(dataEntity.getExpectations());	        
	    }

	    @After
	    public void tearDown() {
	    	ThucydidesWebDriverSupport.getDriver().close();	       
	    }
}
