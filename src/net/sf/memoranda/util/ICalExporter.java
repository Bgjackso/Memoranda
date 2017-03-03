package net.sf.memoranda.util;
import net.sf.memoranda.Event;
import net.sf.memoranda.EventsManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ListIterator;
import java.util.TimeZone;
import java.util.Vector;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Calendar;

/**
 * @author Nichole Erickson
 * Class: ICalExporter
 * 
 * Description: This class handles exporting the current calendar to an ICS file that can be integrated
 * with apps such as Google Calendar.
 */
public class ICalExporter {
	private static String convertEvent(Event e){
		SimpleDateFormat calF = new SimpleDateFormat("yyyyMMdd'T'HHmm'00'");
		calF.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date startDate = e.getTime();
		Date stampDate = e.getTime();
		String endDateString = "";
		try{
			Date endDate = e.getEndDate().getDate();
			endDateString += ";UNTIL=" + calF.format(endDate) + "Z";
		}		
		catch(Exception ex){}

		String repeatType = "";
		if(e.isRepeatable()){
			switch(e.getRepeat()){
			case 1: repeatType = "DAILY"; break;
			case 2: repeatType = "WEEKLY"; break;
			case 3: repeatType = "MONTHLY"; break;
			case 4: repeatType = "YEARLY"; break;
			}
		}
		String repeat = (e.isRepeatable())? String.format("RRULE:FREQ=%s;INTERVAL=%d%s\n",
				repeatType, 
				e.getPeriod(),
				endDateString)
				:"";
		return String.format("BEGIN:VEVENT\n"
				+ "SUMMARY:%s\n"
				+ "UID:%s\n"
				+ "DTSTART:%s\n"
				+ "%s"
				+ "DTSTAMP:%s\n"
				+ "END:VEVENT\n",
				e.getText(),
				e.getId(),
				calF.format(startDate),
				repeat,
				calF.format(stampDate));
	}
	private static String convertToCalendar(){
		String ret = "BEGIN:VCALENDAR\n"
				+ "VERSION:2.0\n"
				+ "CALSCALE:GREGORIAN\n"
				+ "BEGIN:VTIMEZONE\n"
				+ "TZID:" + TimeZone.getTimeZone("GMT").getID() + "\n"
				+ "END:VTIMEZONE\n";
		Vector events = EventsManager.getAllEvents();
		ListIterator it = events.listIterator();
		while(it.hasNext()){
			ret += convertEvent((Event)it.next());
		}

		return ret + "END:VCALENDAR";
	}

	public static void exportCalendar(File f){
		try{
			if (!f.exists()) f.createNewFile();
			FileWriter wr = new FileWriter(f);
			wr.write(convertToCalendar());
			wr.flush();
			wr.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	private static String[] icalKeywords = {"BEGIN:VCALENDAR","END:VCALENDAR","VERSION:","PRODID:","CALSCALE:","METHOD:","X-WR-CALNAME:","X-WR-TIMEZONE:",
			"BEGIN:VTIMEZONE","END:VTIMEZONE","TZID:","X-LIC-LOCATION:","BEGIN:STANDARD","END:STANDARD","TZOFFSETFROM:","TZOFFSETTO:","TZNAME:",
			"BEGIN:VEVENT","END:VEVENT","DTSTART:","DTSTART;","DTEND:","DTEND;","DTSTAMP:","RRULE:","UID:","SUMMARY:","CREATED:","DESCRIPTION:","SEQUENCE:","LOCATION:","LAST-MODIFIED:","STATUS:","TRANSP:"};
	private static String[] rruleKeywords = {"FREQ=", "INTERVAL=","UNTIL=","BYDAY="};
	/**
	 * @param body: the string that is searched
	 * @param start: string at beginning of chunk
	 * @param end: string at end of chunk
	 * @param refArray: string array used in case of "" as end argument
	 * @return res: the string result from searching the body
	 */
	private static String getChunk(String body, String start, String end,String[] refArray){
		if(body.indexOf(start) == -1) return "";
		if(!end.equals("") && body.indexOf(end) != -1){
			String res = body.substring(body.indexOf(start),body.indexOf(end)+end.length());
			return res;
		}
		int iStart = body.indexOf(start) + start.length();
		int indOfKey = body.length();
		int lenOfKey = 0;
		for(String s: refArray){
			if(!s.equals(start) && body.indexOf(s) >= iStart && body.indexOf(s) < indOfKey){
				indOfKey = body.indexOf(s);
				lenOfKey = s.length();
			}
		}

		String res = body.substring(body.indexOf(start), indOfKey);
		return res;
	}
	
	private static Calendar convertTimeZone(int year, int month, int day, int hour, int minute, TimeZone tz1, TimeZone tz2){
		Calendar adjustDate = new GregorianCalendar(tz1);
		adjustDate.set(Calendar.YEAR,year);
		adjustDate.set(Calendar.MONTH,month);
		adjustDate.set(Calendar.DAY_OF_MONTH,day);
		adjustDate.set(Calendar.HOUR_OF_DAY,hour);
		adjustDate.set(Calendar.MINUTE,minute);
		Calendar adj2 = new GregorianCalendar(tz2);
		adj2.setTimeInMillis(adjustDate.getTimeInMillis());
		return adj2;	
	}
}
