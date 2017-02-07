package com.absolute.customercenter.login;

import org.junit.Assert;

import com.absolute.utils.CCPagedObject;
import com.absolute.utils.SerenityUtil;
import net.serenitybdd.core.pages.WebElementFacade;
/**
 * 
 * @author 
 *
 */
public class LoginPage extends CCPagedObject{

    //@FindBy(id = "Email")
    WebElementFacade emailInput;
    String emailElementString;
    
    //@FindBy(id = "Password")
    WebElementFacade pwInput;
    String pwElementString;
        
    //@FindBy(id = "btn-login")
    WebElementFacade loginBtn;
    String loginBtnString;
    
    WebElementFacade localeSelect;
    String localeSelectString;
    
   public LoginPage(){
    	if(SerenityUtil.getEnv("webdriver.driver").equals("appium")){
    		System.out.println("Running for Mobile....");
			emailElementString = ".//*[@id='Email']";
			pwElementString = ".//*[@id='Password']";
			loginBtnString = ".//*[@id='btn-login']";
			localeSelectString = ".//*[@id='locale']";
    	}
    	else
    	{	System.out.println("Running for Desktop....");
			emailElementString = ".//*[@id='Email']";
			pwElementString = ".//*[@id='Password']";
			loginBtnString = ".//*[@id='btn-login']";
			localeSelectString = ".//*[@id='locale']";
    	}
    }
    public void setEmail(String email){
    	emailInput = findBy(emailElementString);
    	emailInput.type(email);
    }
    
    public void setPassword(String pwd){
    	pwInput = findBy(pwElementString);
    	pwInput.type(pwd);
    }
    
    public void clickLogin(){
    	loginBtn = findBy(loginBtnString);
    	loginBtn.click();
    	waitABit(10);
    }
    public void chooseLanguage(){
    	String configLanguage = "en-US"; 
		if(SerenityUtil.getDataLanguage() != null)
			configLanguage = SerenityUtil.getDataLanguage();
    	localeSelect = findBy(localeSelectString);
    	localeSelect.selectByValue(configLanguage);
    }
   public void gotoLogin(){
	   getDriver().get(SerenityUtil.getBaseUrl());
   }
   
   public void verifyExpectations(String exp){
	   waitABit(5);
	   if(exp.equals(""))
			Assert.assertTrue(true);
		else
			Assert.assertTrue(areAllElementsExist(exp));
   }
}
