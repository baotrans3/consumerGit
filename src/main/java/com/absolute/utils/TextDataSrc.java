package com.absolute.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
/**
 * This class is for reading Text Data Source
 * @author Dieu Pham
 *
 */
public class TextDataSrc
{	
	/**
	 * Read text file with defined splitter and return all rows in array list
	 * @param fileName
	 * @param splitter
	 * @return
	 * @throws IOException
	 */

	public List<String[]> readTextFile(String fileName, String splitter) throws IOException{
		List<String[]> allLogRows = new ArrayList<String[]>();		
		FileInputStream fis=null;
		try {
			fis = new FileInputStream(fileName);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();			
			return allLogRows;
		}		 
		//Construct BufferedReader from InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));	 
		String line = null;
		try {
			while ((line = br.readLine()) != null) {								
				//line = line.replaceAll("(^\\[|\\]$)", "");split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)")
				String[] arr = line.split(splitter + "(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				String[] brr = new String[arr.length];
				for(int i=0; i<arr.length; i++)
					brr[i] = arr[i].trim();
				allLogRows.add(brr);
			}
		}finally{
			br.close();
		}
		
		return allLogRows;
	}
	/**
	 * Read CSV File and return allLines each row is a String array
	 * @param fileName
	 * @return List<String[]>
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public List<String[]> readCSVFile(String fileName) throws IOException{
		CSVReader reader = new CSVReader(new FileReader(fileName));
	    List<String[]> allLines = reader.readAll();	    	
	    return allLines;
	}
	
}
