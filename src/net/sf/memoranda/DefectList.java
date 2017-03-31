package net.sf.memoranda;

import java.util.Vector;

import nu.xom.Document;

public interface DefectList {
	Vector<Defect> getAllDefect();
    
    Defect getDefect(int p_defect_num);
    
    void addDefect(Defect p_defect);
    
    void removeDefect(int p_defect_num);
    
    void modifyDefect(int p_defect_num, String p_attrib, String p_value);
        
    int getAllDefectsCount();
    
    Document getXMLContent();
}
