package net.sf.memoranda;

import java.util.Vector;

import nu.xom.Document;

public interface LOCList {

	Vector<LinesofCode> getAllLOC();
    
    LinesofCode getLOC(int p_loc);
    
    void addLOC(LinesofCode loc_amount);
    
    void removeLOC(int locAmount);
    
    void modifyLoc(int p_loc_num, String p_attrib, String p_value);
        
    int getTotalLOCCount();
    
    Document getXMLContent_();
}
