package com.absolute.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import org.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.google.common.io.Files;
/**
 * This class is for all Data Entity
 *
 */
public class readTestcaseFiles
{
	/**
	 * Get Test Data from a file with number lines of header, run Column and run Value, opts is sheetName in case xls file
	 * @param fileName
	 * @param headerNo
	 * @param runCol
	 * @param runVal
	 * @param opts
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public List<String[]> getTestData(String fileName, int headerNo, int runCol, String runVal, String...opts) throws IOException, InvalidFormatException {
        String ext = Files.getFileExtension(fileName);
        List<String[]> datasrc = null;	        
        switch(ext){
        case "csv": 	TextDataSrc csv = new TextDataSrc();
        				datasrc = csv.readCSVFile(fileName);
	        			break;
       
        }		
        
        List<String[]> trueData = new ArrayList<>();
        //Remove header
        for (int key = 0 + headerNo; key < datasrc.size(); key++) {
            String[] row = datasrc.get(key);
            trueData.add(row);            
        }
        List<String[]> entitys = new ArrayList<>();
        for (String[] row : trueData) {
            if(row[runCol].equals(runVal)){            	
            	entitys.add(row);
            }
        }
        return entitys;
    }
	
	public static int toNumber(String name) {
        int number = 0;
        for (int i = 0; i < name.length(); i++) {
            number = number * 26 + (name.charAt(i) - ('A' - 1));
        }
        return number - 1;
    }

}
