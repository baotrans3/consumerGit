package com.absolute.customercenter.login;

import net.thucydides.core.annotations.Step;
/**
 * 
 * @author Dieu Pham
 *
 */
public class LoginSteps{
	
	public LoginPage loginPage;
		
	@Step
	public void inputEmail(String email){
		loginPage.setEmail(email);
	}
	@Step
	public void inputPwd(String pwd){
		loginPage.setPassword(pwd);
	}
	@Step
	public void clickLogin(){
		loginPage.clickLogin();		
	}
	
	@Step
	public void chooseLanguage(){
		loginPage.chooseLanguage();
	}
	@Step
	public void gotoLogin(){
		loginPage.gotoLogin();
	}	
	
	@Step
	public void verifyExpectation(String exp){
			loginPage.verifyExpectations(exp);
		
	}
	
	@Step("Login as email: {0} and password: {1}")
    public void login(String user, String pass){        
        //loginPage.getDriver().manage().window().maximize();
        loginPage.setEmail(user);
        loginPage.setPassword(pass);
        loginPage.chooseLanguage();
        loginPage.clickLogin();        
    }	
}
