package net.sf.memoranda;

import java.util.Arrays;
import net.sf.memoranda.date.CalendarDate;

import javax.swing.JTextField;

public class LinesofCode {
	
	
	public int locIntValue;
	public String locStrValue;
	public String str;
	public CalendarDate loc_completed;
	
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
	
	public CalendarDate getDateCompleted(){
		return this.loc_completed;
	}
	
	public void setLOCIntValue(int locInt){
		this.locIntValue = locInt;
	}
	
	public void setLOCStrValue(String locStr){
		this.locStrValue = locStr;
	}
	
	public void setDateAdded(CalendarDate loc_date){
		this.loc_completed = loc_date;
	}
	
}
