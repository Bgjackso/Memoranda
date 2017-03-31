package net.sf.memoranda;

import java.util.Vector;

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
	public LinesofCode getLOC(String p_defect_num) {
		
		return null;
	}

	@Override
	public void addLOC(LinesofCode locAmount) {
		Element loc_element = new Element("loc");
		loc_element.addAttribute(new Attribute("loc_amount", Integer.toString(locAmount.getLOCIntValue())));
		
	}

	@Override
	public void removeLOC(String locAmount) {
		
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
