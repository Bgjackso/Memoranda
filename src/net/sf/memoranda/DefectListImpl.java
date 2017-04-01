package net.sf.memoranda;

import java.util.Vector;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

public class DefectListImpl implements DefectList {
	private Project _project = null;
    private Document _doc = null;
    private Element _root = null;
	
    public DefectListImpl(Document doc, Project prj) {
        _doc = doc;
        _root = _doc.getRootElement();
        _project = prj;
    }

    public DefectListImpl(Project prj) {
            _root = new Element("defects-list");
            _doc = new Document(_root);
            _project = prj;
    }
    
	public Vector getAllDefect() {
		Vector v = new Vector();
        Elements rs = _root.getChildElements("defect");
        for (int i = 0; i < rs.size(); i++)
        {
        	Defect temp_defect = new Defect();
        	temp_defect.SetDefectNumber(Integer.parseInt(rs.get(i).getAttribute("defect_number").getValue()));
        	CalendarDate dateFound = new CalendarDate(
        			Integer.parseInt(rs.get(i).getChildElements("date").get(0).getAttribute("day").getValue()),
        			Integer.parseInt(rs.get(i).getChildElements("date").get(0).getAttribute("month").getValue()),
        			Integer.parseInt(rs.get(i).getChildElements("date").get(0).getAttribute("year").getValue())
        			);
        	temp_defect.SetDateFound(dateFound);
        	temp_defect.SetDescription(rs.get(i).getAttribute("description").getValue());
        	temp_defect.SetCorrected(rs.get(i).getAttribute("corrected").getValue());
        	temp_defect.SetInjected(rs.get(i).getAttribute("injected").getValue());
        	temp_defect.SetType(rs.get(i).getAttribute("type").getValue());
        	temp_defect.SetFixTime(Integer.parseInt(rs.get(i).getAttribute("fix_time").getValue()));
        
        	v.add(temp_defect);
        }
        return v;
	}

	public Defect getDefect(int p_defect_num) {
		Elements rs = _root.getChildElements("defect");
		Defect temp_defect = null;
        for (int i = 0; i < rs.size(); i++)
        {
        	if (rs.get(i).getAttribute("defect_number").getValue().equals(Integer.toString(p_defect_num)))
        	{
        		temp_defect = new Defect();
            	temp_defect.SetDefectNumber(Integer.parseInt(rs.get(i).getAttribute("defect_number").getValue()));
            	CalendarDate dateFound = new CalendarDate(
            			Integer.parseInt(rs.get(i).getChildElements("date").get(0).getAttribute("day").getValue()),
            			Integer.parseInt(rs.get(i).getChildElements("date").get(0).getAttribute("month").getValue()),
            			Integer.parseInt(rs.get(i).getChildElements("date").get(0).getAttribute("year").getValue())
            			);
            	temp_defect.SetDateFound(dateFound);
            	temp_defect.SetDescription(rs.get(i).getAttribute("description").getValue());
            	temp_defect.SetCorrected(rs.get(i).getAttribute("corrected").getValue());
            	temp_defect.SetInjected(rs.get(i).getAttribute("injected").getValue());
            	temp_defect.SetType(rs.get(i).getAttribute("type").getValue());
            	temp_defect.SetFixTime(Integer.parseInt(rs.get(i).getAttribute("fix_time").getValue()));
        	}
        }
        return temp_defect;
	}

	public void addDefect(Defect p_defect) {
		Element defect_element = new Element("defect");
		defect_element.addAttribute(new Attribute("defect_number", Integer.toString(p_defect.GetDefectNumber())));
		
		Element defect_date = new Element("date");
		defect_date.addAttribute(new Attribute("day", Integer.toString(p_defect.GetDateFound().getDay())));
		defect_date.addAttribute(new Attribute("month", Integer.toString(p_defect.GetDateFound().getMonth())));
		defect_date.addAttribute(new Attribute("year", Integer.toString(p_defect.GetDateFound().getYear())));
		defect_element.appendChild(defect_date);
		
		defect_element.addAttribute(new Attribute("description", p_defect.GetDescription()));
		defect_element.addAttribute(new Attribute("corrected", p_defect.GetCorrected()));
		defect_element.addAttribute(new Attribute("injected", p_defect.GetInjected()));
		defect_element.addAttribute(new Attribute("type", p_defect.GetType()));
		
		defect_element.addAttribute(new Attribute("fix_time", Integer.toString(p_defect.GetFixTime())));
		
        _root.appendChild(defect_element);
	}

	public void removeDefect(int p_defect_num) {
		Elements rs = _root.getChildElements("defect");
        for (int i = 0; i < rs.size(); i++)
        {
        	if (rs.get(i).getAttribute("defect_number").getValue().equals(Integer.toString(p_defect_num)))
        	{
        		_root.removeChild(rs.get(i));
        	}
        }
	}
	
	public void modifyDefect(int p_defect_num, String p_attrib, String p_value)
	{
		Elements rs = _root.getChildElements("defect");
        for (int i = 0; i < rs.size(); i++)
        {
        	if (rs.get(i).getAttribute("defect_number").getValue().equals(Integer.toString(p_defect_num)))
        	{
        		if (rs.get(i).getAttribute(p_attrib) != null)
        		{
        			rs.get(i).getAttribute(p_attrib).setValue(p_value);
        		}
        	}
        }
	}

	@Override
	public int getAllDefectsCount() {
		return _root.getChildElements("defect").size();
	}

	@Override
	public Document getXMLContent() {
		return _doc;
	}

}
