package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;

public class LinesofCode {
	
	public int locNumber;
	public int locIntValue;
	public String locStrValue;
	public CalendarDate loc_completed;
	public String description;
	
	public LinesofCode(){
		this.locNumber = 0;
		//this.locStrValue = "0";
		this.loc_completed = CalendarDate.today();
	}
	
	public int getLocNumber (){
		return this.locNumber;
	}
	
	public String getLOCStrValue(){
		return this.locStrValue;
	}
	
	public int getLOCIntValue(){
		return this.locIntValue;
	}
	
	public CalendarDate getDateCompleted(){
		return this.loc_completed;
	}
	
	public String getDescription() { 
		return this.description; 
		}
	
	public void setLocNumber (int locInt){
		this.locIntValue = locInt;
	}
	
	public void setLOCStrValue(String locStr){
		this.locStrValue = locStr;
	}
	
	public void setLOCIntValue(int locInt){
		this.locIntValue = locInt;
	}
	
	public void setDateCompleted(CalendarDate loc_date){
		this.loc_completed = loc_date;
	}
	
	public void setDescription (String description)
	{
		this.description = description;
	}
	
}
