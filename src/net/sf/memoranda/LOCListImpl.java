package net.sf.memoranda;

import java.util.Vector;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

public class LOCListImpl implements LOCList {

	private Project _project = null;
	private Document _doc = null;
	private Element _root = null; 
	
	public LOCListImpl(Document doc, Project prj){
		_doc = doc;
		_root = _doc.getRootElement();
		_project = prj;
	}
	
	public LOCListImpl(Project prj){
		_root = new Element ("loc-list");
		_doc = new Document (_root);
		_project = prj;
	}
	
	public Vector getAllLOC() {
		Vector v = new Vector();
		Elements rs = _root.getChildElements("loc");
		
		for (int i = 0; i < rs.size(); i++){
			LinesofCode temp_loc = new LinesofCode();
			temp_loc.setLocNumber(Integer.parseInt(rs.get(i).getAttribute("loc_number").getValue()));
        	CalendarDate dateCompleted = new CalendarDate(
        			Integer.parseInt(rs.get(i).getChildElements("date").get(0).getAttribute("day").getValue()),
        			Integer.parseInt(rs.get(i).getChildElements("date").get(0).getAttribute("month").getValue()),
        			Integer.parseInt(rs.get(i).getChildElements("date").get(0).getAttribute("year").getValue())
        			);
        	temp_loc.setDateCompleted(dateCompleted);
        	temp_loc.setLOCIntValue(Integer.parseInt(rs.get(i).getAttribute("lines_of_code").getValue()));
        	temp_loc.setDescription(rs.get(i).getAttribute("description").getValue());
        	
        	v.add(temp_loc);
		}
		return v;
	}

	public LinesofCode getLOC(int p_loc) {
		Elements rs = _root.getChildElements("loc");
		LinesofCode temp_loc = null;
		
		for (int i =0; i< rs.size(); i++){
			
			if (rs.get(i).getAttribute("loc_number").getValue().equals(Integer.toString(p_loc))){
				temp_loc = new LinesofCode();
				temp_loc.setLocNumber(Integer.parseInt(rs.get(i).getAttribute("loc_number").getValue()));
				CalendarDate dateCompleted = new CalendarDate(
	        			Integer.parseInt(rs.get(i).getChildElements("date").get(0).getAttribute("day").getValue()),
	        			Integer.parseInt(rs.get(i).getChildElements("date").get(0).getAttribute("month").getValue()),
	        			Integer.parseInt(rs.get(i).getChildElements("date").get(0).getAttribute("year").getValue())
	        			);
	        	temp_loc.setDateCompleted(dateCompleted);
	        	temp_loc.setLOCIntValue(Integer.parseInt(rs.get(i).getAttribute("lines_of_code").getValue()));
	        	temp_loc.setDescription(rs.get(i).getAttribute("description").getValue());
			}  
		}
		return temp_loc;
	}

	public void addLOC(LinesofCode p_loc) {
		Element loc_element = new Element("loc");
		loc_element.addAttribute(new Attribute("loc_number", Integer.toString(p_loc.getLocNumber())));
		
		Element loc_date = new Element("date");
		loc_date.addAttribute(new Attribute("day", Integer.toString(p_loc.getDateCompleted().getDay())));
		loc_date.addAttribute(new Attribute("month", Integer.toString(p_loc.getDateCompleted().getMonth())));
		loc_date.addAttribute(new Attribute("year", Integer.toString(p_loc.getDateCompleted().getYear())));
		loc_element.appendChild(loc_date);
		
		loc_element.addAttribute(new Attribute("lines_of_code",Integer.toString(p_loc.getLOCIntValue())));
		loc_element.addAttribute(new Attribute("description", p_loc.getDescription()));
        _root.appendChild(loc_element);
	}

	public void removeLOC(int locAmount) {
		Elements rs = _root.getChildElements("loc");
        for (int i = 0; i < rs.size(); i++)
        {
        	if (rs.get(i).getAttribute("loc_number").getValue().equals(Integer.toString(locAmount)))
        	{
        		_root.removeChild(rs.get(i));
        	}
        }
	}
	
	public void modifyLoc(int p_loc_num, String p_attrib, String p_value)
	{
		Elements rs = _root.getChildElements("loc");
        for (int i = 0; i < rs.size(); i++)
        {
        	if (rs.get(i).getAttribute("loc_number").getValue().equals(Integer.toString(p_loc_num)))
        	{
        		if (rs.get(i).getAttribute(p_attrib) != null)
        		{
        			rs.get(i).getAttribute(p_attrib).setValue(p_value);
        		}
        	}
        }
	}

	public int getTotalLOCCount() {
		return _root.getChildElements("loc").size();
	}

	public Document getXMLContent_() {
		return _doc;
	}

}
