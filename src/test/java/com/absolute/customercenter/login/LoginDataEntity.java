package com.absolute.customercenter.login;

import com.absolute.utils.SerenityUtil;
import com.absolute.utils.XlsDataSrc;
/**
 * 
 * @author Dieu Pham
 *
 */
public class LoginDataEntity {
	
	public static final String SCENARIO = "A";
	public static final String IS_RUN = "B";
	public static final String USERNAME_INDEX = "C";
    public static final String PASSWORD = "D";
    public static final String EXPECTATION = "E";
    
    private String username = "";
    private String password = "";
    private String scenarioNote = "";
    private String isRun = "";
    private String expectations = "";
    
    public String getIsRun() {
		return isRun;
	}
	public void setIsRun(String isRun) {
		this.isRun = isRun;
	}
	public String getExpectations() {
		return expectations;
	}
	public void setExpectations(String expectations) {
		this.expectations = expectations;
	}
   
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getScenarioNote() {
		return scenarioNote;
	}
	public void setScenarioNote(String scenarioNote) {
		this.scenarioNote = scenarioNote;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public static LoginDataEntity convertArrayToEntity(String[] fields) {
        LoginDataEntity entity = new LoginDataEntity();
        entity.setScenarioNote(fields[XlsDataSrc.toNumber(SCENARIO)].trim());
        entity.setIsRun(fields[XlsDataSrc.toNumber(IS_RUN)].trim());
        entity.setUsername(fields[XlsDataSrc.toNumber(USERNAME_INDEX)].trim());       
        entity.setPassword(fields[XlsDataSrc.toNumber(PASSWORD)].trim());
        if(SerenityUtil.getEnv("webdriver.driver").equals("appium"))
        	entity.setExpectations(fields[XlsDataSrc.toNumber(EXPECTATION) + 1].trim());
        else
        	entity.setExpectations(fields[XlsDataSrc.toNumber(EXPECTATION)].trim());        
        return entity;
    }
}
