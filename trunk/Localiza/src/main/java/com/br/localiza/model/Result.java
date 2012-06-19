/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.localiza.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author thiago.rodrigues
 */
public class Result implements Serializable {

    private Map<String, Document> documentMap;

    private Integer numResultsFound;
            
    public Result() {
        this.documentMap = new HashMap<String, Document>();
    }
    
    public List<Document> getDocumentList() {
        List<Document> docList = new ArrayList<Document>();
        for (Document doc : documentMap.values()) docList.add(doc);
        return docList;
    }

    public void setDocumentList(List<Document> documentList) {
        for (Document doc : documentList) 
            this.documentMap.put(doc.getUrl(), doc);
    }

    public void addDocument(Document doc) {
        doc.buildInformation();
        this.documentMap.put(doc.getUrl(), doc);
    }
    
    public void mergeHighlightInformation(List<HighlightInformation> highlightList) {
        for (HighlightInformation info : highlightList) 
            this.documentMap.get(info.getType()).buildInformation(info);
    }
    
    public Map<String, Document> getDocumentMap() {
        return documentMap;
    }
    
    public Integer getNumResultsFound() {
        return numResultsFound;
    }

    public void setNumResultsFound(Integer numResultsFound) {
        this.numResultsFound = numResultsFound;
    }
                
}
