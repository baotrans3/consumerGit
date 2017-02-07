package com.absolute.utils;

import java.util.List;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

public class CCPagedObject extends PageObject{
	
		
	public boolean checkTextInElement(List<WebElementFacade> l, String s){
		for(WebElementFacade e:l){
			if(e != null){
				if(!(s.contains("ttribute:"))){
					if(e.isCurrentlyVisible()){
						try{
							if(e.getText().contains(s)) return true;
						}
						catch(Exception ex){					
							ex.getMessage();
						}
					}
				}
				else{
					try{
						if(s.contains("disabled")&&!(e.isCurrentlyVisible()))
							return true;
					}
					catch(Exception ex){					
						ex.getMessage();
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Check if all elements are displayed in UI
	 * @param identifiers
	 * @return true if all elements are matched
	 */
	public boolean areAllElementsExist(String identifiers){
		if (null == identifiers)
			return true;
		String[] identifier = identifiers.split("\n");
		boolean result = true;	
		for (String s:identifier){
			s = s.trim();
			String[] parts = s.split(", ");
				if(parts.length == 2)
					result = getElements(parts[0], parts[1]) != null? true:false;
				if(parts.length == 3){
					String configLanguage = "en-US"; 
					if(SerenityUtil.getDataLanguage() != null)
						configLanguage = SerenityUtil.getDataLanguage();
				//parse defined language here
				if(parts[2].startsWith("{<") && parts[2].endsWith(">}")){
					//cut start and end of parts[2]
					String mulLang = parts[2].substring(2, parts[2].length() - 2);
					String[] langs = mulLang.split(">,<");
					String trueText = "";
					for(String lang:langs){
						if(lang.startsWith(configLanguage + ": ")){
							trueText = lang.substring(configLanguage.length() + 2, lang.length());
							break;
						}
					}
					result = checkTextInElement(getElements(parts[0], parts[1]), trueText);
				}
				else
					result = checkTextInElement(getElements(parts[0], parts[1]), parts[2]);
				}
			if(result == false)
				return false;
		}
		return result;
	}
	
	public List<WebElementFacade> getElements(String type, String identify){		
		List<WebElementFacade> elements = null;
		try{
		switch(type){					
				case "id":		elements=findAll(By.id(identify)); break;		
				case "css": 	elements=findAll(By.cssSelector(identify)); break;
				case "name":	elements=findAll(By.name(identify)); break;
				default:	elements=findAll(By.xpath(identify));	
		}
		}
		catch(Exception e){
			return null;
		}
		return elements;
	}
	@Override
    public void waitABit(long timeInSecond) {
        super.waitABit(timeInSecond*1000);
    }
}
