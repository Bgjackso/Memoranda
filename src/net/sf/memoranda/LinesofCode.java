package net.sf.memoranda;

import java.util.Arrays;

import javax.swing.JTextField;

public class LinesofCode {
	
	
	public int locIntValue;
	public String locStrValue;
	public String str;
	//valid values for JTextField
	public String[] locValidValues = {"0","1","2","3","4","5","6","7","8","9"};
	
	public LinesofCode(){
		this.locIntValue = 0;
		this.locStrValue = "0";
	}
	
	public int getLOCIntValue(){
		return this.locIntValue;
	}
	
	public String getLOCStrValue(){
		return this.locStrValue;
	}
	
	
	public void setLOCStrValue(String locStr){
		this.locStrValue = locStr;
	}
	
	
	//checking to see if the text field is valid to be logged and returns value if valid.
	public void locValue(JTextField jtext){
			str = jtext.getText();
		
			if (!str.matches(".*[1234567890].*")){
				String text = "Invalid Value(s)";
				jtext.setText(text);
			} else{
				setLOCStrValue(str);
				jtext.setText(null);
			}
	}
	
	
	
	
	public static boolean stringContainsItemFromList(String inputStr, String[] items) {
	    return Arrays.stream(items).noneMatch(inputStr::contains);
	}
}
