/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.localiza.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thiago.rodrigues
 */
public class HighlightResult {

    private List<HighlightInformation> infoList;

    public HighlightResult() {
        this.infoList = new ArrayList<HighlightInformation>();
    }
    
    public void addInformation(HighlightInformation info) {
        this.infoList.add(info);
    }

    // getters e setters
    
    public List<HighlightInformation> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<HighlightInformation> infoList) {
        this.infoList = infoList;
    }
    
 
}
