/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.localiza.web.beans;

import com.br.localiza.model.Document;
import com.br.localiza.model.SolrQuery;
import com.br.localiza.model.SolrResponse;
import com.br.localiza.web.util.LocalizaMediator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author thiago.rodrigues
 */
public class LazyDocumentDataModel extends LazyDataModel<Document> {

    private LocalizaBean localizaBean;
    
    private LocalizaMediator searcher;
    
    private SolrQuery solrQuery;
    
    private SolrResponse firstSolrResponse;
    
    
    public LazyDocumentDataModel(String inputQuery, LocalizaBean bean) {
        this.localizaBean = bean;
        this.searcher = bean.getSearcherMediator();
        this.solrQuery = new SolrQuery(inputQuery);
    }
    
    
    public Integer loadCurrentPage() throws Exception {
        this.firstSolrResponse = this.searcher.executeQuery(this.solrQuery);
        this.firstSolrResponse.buildResults();
        return this.firstSolrResponse.getResult().getNumResultsFound();
    }
    
    
    @Override
    public  List<Document> load(int offSet, int pageSize, String sortField, 
        SortOrder sortOrder, Map<String, String> filter) {
        
        List<Document> documentList = new ArrayList<Document>();
        
        // Verfying execution of loadCurrentPage method
        if (this.firstSolrResponse != null) {
            documentList = this.firstSolrResponse.getResult().getDocumentList();
            this.setRowCount(this.firstSolrResponse.getResult().getNumResultsFound());
            this.firstSolrResponse = null;
            return documentList;
        }
        
        this.solrQuery.setStartRow(offSet);
        this.solrQuery.setPageSize(pageSize);
        
        try {
            SolrResponse solrResponse = this.searcher.executeQuery(this.solrQuery);
            solrResponse.buildResults();
            documentList = solrResponse.getResult().getDocumentList();
            this.setRowCount(this.firstSolrResponse.getResult().getNumResultsFound());
        }
        catch (Exception e) {
            e.printStackTrace();
            this.localizaBean.displayGeneralFatalMessage();
        }
        
        return documentList;
    }
}
