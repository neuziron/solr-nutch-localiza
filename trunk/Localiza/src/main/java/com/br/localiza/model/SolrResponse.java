/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.localiza.model;

/**
 *
 * @author thiago.rodrigues
 */
public class SolrResponse {
    
    private Result result;
    
    private HighlightResult highlight;
    
    public SolrResponse() {
        this.result = new Result();
    }
    
    public void buildResults() {
        this.result.mergeHighlightInformation(this.highlight.getInfoList());
    }
    
    // getters e setters
    
    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public void setHighlight(HighlightResult highlight) {
        this.highlight = highlight;
    }
    
    public HighlightResult getHighlight() {
        return highlight;
    }
    
}
