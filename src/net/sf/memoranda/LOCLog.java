package net.sf.memoranda;

import java.util.Vector;

import nu.xom.Document;

public interface LOCLog {

	Vector<LinesofCode> getAllLOC();
    
    LinesofCode getLOC(String p_defect_num);
    
    void addLOC(LinesofCode locAmount);
    
    void removeLOC(String locAmount);
        
    int getTotalLOCCount();
    
    Document getXMLContent();
}
