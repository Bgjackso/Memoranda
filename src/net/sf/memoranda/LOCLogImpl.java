package net.sf.memoranda;

import java.util.Vector;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

public class LOCLogImpl implements LOCLog {

	private Project _project = null;
	private Document _doc = null;
	private Element _root = null; 
	
	public LOCLogImpl(Document doc, Project prj){
		_doc = doc;
		_root = _doc.getRootElement();
		_project = prj;
	}
	
	public LOCLogImpl(Project prj){
		_root = new Element ("loc-list");
		_doc = new Document (_root);
		_project = prj;
	}
	
	@Override
	public Vector<LinesofCode> getAllLOC() {
		Vector v = new Vector();
		Elements rs = _root.getChildElements("loc");
		
		for (int i = 0; i < rs.size(); i++){
			LinesofCode temp_loc = new LinesofCode();
		}
		return null;
	}

	@Override
	public LinesofCode getLOC(int p_loc) {
		Elements rs = _root.getChildElements("loc");
		LinesofCode temp_loc = null;
		
		for (int i =0; i< rs.size(); i++){
			
			if (rs.get(i).getAttribute("loc_amount").getValue().equals(Integer.toString(p_loc))){
				temp_loc = new LinesofCode();
				temp_loc.setLOCIntValue(Integer.parseInt(rs.get(i).getAttribute("loc_amount").getValue()));
				CalendarDate dateFound = new CalendarDate(
	        			Integer.parseInt(rs.get(i).getChildElements("date").get(0).getAttribute("day").getValue()),
	        			Integer.parseInt(rs.get(i).getChildElements("date").get(0).getAttribute("month").getValue()),
	        			Integer.parseInt(rs.get(i).getChildElements("date").get(0).getAttribute("year").getValue())
	        			);
	        	temp_loc.setDateAdded(dateFound);
			}  
		}
		return temp_loc;
	}

	@Override
	public void addLOC(LinesofCode loc_amount) {
		Element loc_element = new Element("loc");
		loc_element.addAttribute(new Attribute("loc_amount", Integer.toString(loc_amount.getLOCIntValue())));
		
		Element loc_date = new Element("date");
		loc_date.addAttribute(new Attribute("day", Integer.toString(loc_amount.getDateCompleted().getDay())));
		loc_date.addAttribute(new Attribute("month", Integer.toString(loc_amount.getDateCompleted().getMonth())));
		loc_date.addAttribute(new Attribute("year", Integer.toString(loc_amount.getDateCompleted().getYear())));
		loc_element.appendChild(loc_element);
		
        _root.appendChild(loc_element);
	}

	@Override
	public void removeLOC(int locAmount) {
		
	}

	@Override
	public int getTotalLOCCount() {

		return 0;
	}

	@Override
	public Document getXMLContent() {
	
		return null;
	}

}
