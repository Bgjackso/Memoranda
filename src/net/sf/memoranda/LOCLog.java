package net.sf.memoranda;

import java.util.Vector;

import nu.xom.Document;

public interface LOCLog {

	Vector<LinesofCode> getAllLOC();
    
    LinesofCode getLOC(int p_defect_num);
    
    void addLOC(LinesofCode loc_amount);
    
    void removeLOC(int locAmount);
        
    int getTotalLOCCount();
    
    Document getXMLContent();
}
