package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;

/*
 * Class holds general information regarding defects in a project
 */
public class Defect {
	private int m_defect_num;
	private int m_fix_time;
	private String m_corrected;
	private String m_description;
	private CalendarDate m_date_found;
	private String m_type;
	private String m_injected;
	private boolean m_opened;
	
	public Defect()
	{
		this.m_defect_num = 0;
		this.m_fix_time = 0;
		this.m_corrected = "";
		this.m_description = "";
		this.m_injected = "";
		this.m_type = "";
		this.m_date_found = CalendarDate.today();
		this.m_opened = true;
	}
	
	public int GetDefectNumber() 		{ return this.m_defect_num; }
	public int GetFixTime() 			{ return this.m_fix_time; }
	public String GetCorrected() 		{ return this.m_corrected; }
	public String GetDescription()		{ return this.m_description; }
	public CalendarDate GetDateFound()  { return this.m_date_found; }
	public String GetType() 			{ return this.m_type; }
	public String GetInjected() 		{ return this.m_injected; }
	public boolean GetOpened()  		{ return this.m_opened; }
	
	public void SetDefectNumber (int p_dnum)
	{
		this.m_defect_num = p_dnum;
	}
	public void SetFixTime (int p_time)
	{
		this.m_fix_time = p_time;
	}
	public void SetDateFound(CalendarDate p_date)
	{
		this.m_date_found = p_date;
	}
	public void SetCorrected (String p_corrected)
	{
		this.m_corrected = p_corrected;
	}
	public void SetDescription (String p_description)
	{
		this.m_description = p_description;
	}
	public void SetType (String p_type)
	{
		this.m_type = p_type;
	}
	public void SetInjected (String p_injected)
	{
		this.m_injected = p_injected;
	}
	public void CloseDefect()
	{
		this.m_opened = false;
	}
	public void OpenDefect()
	{
		this.m_opened = true;
	}
}