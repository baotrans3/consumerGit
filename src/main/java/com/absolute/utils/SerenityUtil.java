package com.absolute.utils;
import java.util.Random;//Bao Tran
import java.io.File;
import net.thucydides.core.guice.ThucydidesModule;
import net.thucydides.core.util.EnvironmentVariables;

/**
 *

 */
public class SerenityUtil {

    private static EnvironmentVariables evs;
	static Random rnd = new Random();//Bao Tran
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //Bao Tran   
    public static String randomString( int len ) 
    {
       StringBuilder sb = new StringBuilder( len );
       for( int i = 0; i < len; i++ ) 
          sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
       return sb.toString();
    }
   
    public static String getEnv(String key) {
        if (evs == null) {
            evs = (new ThucydidesModule()).provideEnvironmentVariables();
        }
        return evs.getProperty(key);
    }
    
    public static String getEnvironment(){
    	return getEnv("testParams.ENVIRONMENT");
    }
        
    public static String getPartnerCode(){
    	return getEnv("testParams.PARTNERCODE");
    }
    
    public static String getUIDataFile(){
    	return getEnv("data.FILENAME_UI");
    }
    
    public static String getDataExtension(){
    	return getEnv("data.FILEEXTENSION");
    }
    
    public static int getDataHeaderNo(){
    	return Integer.parseInt(getEnv("data.HEADER_NO"));
    }
    
    public static String getDataLanguage(){
    	return getEnv("data.LANGUAGE");
    }
    
    public static String getAPIDataFile(){
    	return getEnv("data.FILENAME_API");
    }
    //HEADER_NO
    public static String getFullDataFile4UI(){
    	return System.getProperty("user.dir") + File.separator + "testcases" + File.separator + getEnvironment() + File.separator + getPartnerCode() + File.separator + getUIDataFile();  
    }
    
    public static String getFullDataPath(){
    	return System.getProperty("user.dir") + File.separator + "testcases" + File.separator + getEnvironment() + File.separator + getPartnerCode() + File.separator;
    }
    public static String getBaseUrl() {
    	String chosenPartner = "partnerLink." + getPartnerCode();
        return getEnv(chosenPartner);
    }
    
    public static String data4LOGIN(){
    	return getEnv("datasheet.LOGIN");
    }
    
    public static String data4RESETPIN(){
    	return getEnv("datasheet.RESETPIN");
    }
    public static String data4CHANGEPIN(){
    	return getEnv("datasheet.CHANGEPIN");
    }
    public static String data4FORGOTPIN(){
    	return getEnv("datasheet.FORGOTPIN");
    }
    public static String data4SEARCH(){
    	return getEnv("datasheet.SEARCH");
    }
    public static String data4ContactInfo(){
    	return getEnv("datasheet.CONTACTINFO");
    }
    public static String data4CHANGERMAIL(){
    	return getEnv("datasheet.CHANGERMAIL");
    }
    public static String data4CHANGEPASSWORD(){
    	return getEnv("datasheet.CHANGEPASSWORD");
    }
    public static String data4CREATEACCOUNT(){
    	return getEnv("datasheet.CREATEACCOUNT");
    }
    public static String data4DEVICELOCK(){
    	return getEnv("datasheet.DEVICELOCK");
    }
    public static String data4DATADELETE(){
    	return getEnv("datasheet.DATADELETE");
    }
    public static String data4GeoLocation(){
    	return getEnv("datasheet.GEOLOCATION");
    }
    public static String data4DeviceName(){
    	return getEnv("datasheet.DEVICENAME");
    }
    public static String data4PhoneNumber(){
    	return getEnv("datasheet.PHONENUMBER");
    }
    public static String data4TheftRecovery(){
    	return getEnv("datasheet.THEFTRECOVERY");
    }
    public static String data4ValidateRegcode(){
    	return getEnv("datasheet.VALIDATEREGCODE");
    }
    public static String data4AddDevice(){
    	return getEnv("datasheet.ADDDEVICE");
    }
    public static String data4PRINTLICENSE(){
    	return getEnv("datasheet.PRINTLICENSE");
    }
    public static String data4REINSTALL(){
    	return getEnv("datasheet.REINSTALL");
    }
    public static String data4UNINSTALL(){
    	return getEnv("datasheet.UNINSTALL");
    }
    public static String data4EMERGENCY(){
    	return getEnv("datasheet.EMERGENCY");
    }
}
