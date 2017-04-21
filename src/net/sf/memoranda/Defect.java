package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;

/*
 * Class holds general information regarding defects in a project
 */
public class Defect {
	private int mDefectnum;
	private int mFixtime;
	private String mCorrected;
	private String mDescription;
	private CalendarDate mDatefound;
	private String mType;
	private String mInjected;
	private boolean mOpened;
	
	public Defect()
	{
		this.mDefectnum = 0;
		this.mFixtime = 0;
		this.mCorrected = "";
		this.mDescription = "";
		this.mInjected = "";
		this.mType = "";
		this.mDatefound = CalendarDate.today();
		this.mOpened = true;
	}
	
	public int GetDefectNumber() 		{ return this.mDefectnum; }
	public int GetFixTime() 			{ return this.mFixtime; }
	public String GetCorrected() 		{ return this.mCorrected; }
	public String GetDescription()		{ return this.mDescription; }
	public CalendarDate GetDateFound()  { return this.mDatefound; }
	public String GetType() 			{ return this.mType; }
	public String GetInjected() 		{ return this.mInjected; }
	public boolean GetOpened()  		{ return this.mOpened; }
	
	public void SetDefectNumber (int p_dnum)
	{
		this.mDefectnum = p_dnum;
	}
	public void SetFixTime (int p_time)
	{
		this.mFixtime = p_time;
	}
	public void SetDateFound(CalendarDate p_date)
	{
		this.mDatefound = p_date;
	}
	public void SetCorrected (String p_corrected)
	{
		this.mCorrected = p_corrected;
	}
	public void SetDescription (String p_description)
	{
		this.mDescription = p_description;
	}
	public void SetType (String p_type)
	{
		this.mType = p_type;
	}
	public void SetInjected (String p_injected)
	{
		this.mInjected = p_injected;
	}
	public void CloseDefect()
	{
		this.mOpened = false;
	}
	public void OpenDefect()
	{
		this.mOpened = true;
	}
}