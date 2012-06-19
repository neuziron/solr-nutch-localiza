/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.localiza.web.beans;

import com.br.localiza.model.Document;
import com.br.localiza.web.util.LocalizaMediator;
import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.lucene.queryParser.ParseException;
import org.primefaces.model.LazyDataModel;

/**
 * Managed bean responsible for the communication with the Solr.
 * The execution of queries happens through this communication.
 * 
 * @author thiago.rodrigues
 * 
 * @since 09-03-2012
 */
@ManagedBean
@ViewScoped
public class LocalizaBean implements Serializable {
    
    /*
     * Input data attributes
     */
    private String inputQuery;
    
    /*
     * Output data attributes
     */
    private LocalizaMediator searcherMediator;
    private LazyDataModel<Document> docLazyModel;
    private int numResultsFound;
    
    /*
     * State page parameters
     */
    private boolean resultDataTableRender;
    private boolean msgResultSetRender;
    private boolean isSearching;
    
    
    public LocalizaBean() throws IOException {
        this.searcherMediator = new LocalizaMediator();
        reset();
    }
    
    private void reset() {
        this.docLazyModel = null;
        this.resultDataTableRender = this.msgResultSetRender = this.isSearching = false;
    }
    
    public String search() {
        
        this.resultDataTableRender = true;

        if (this.inputQuery == null || this.inputQuery.length() == 0) {
            this.docLazyModel = null;
            this.msgResultSetRender = false;
        }
        else {
            try {
                this.docLazyModel = new LazyDocumentDataModel(this.inputQuery, this);
                displayResultMessage(((LazyDocumentDataModel) this.docLazyModel).loadCurrentPage());
            } 
            catch (ParseException e) {
                e.printStackTrace();
                displayParserErrorMessage();
                reset();
            }
            catch (Exception e) {
                e.printStackTrace();
                displayGeneralFatalMessage();
                reset();
            }
        }
        
        return null;
    }

    private void displayResultMessage(Integer numResults) {
        this.numResultsFound = numResults == null ? 0 : numResults;
        if (this.numResultsFound > 0) {
            this.msgResultSetRender = true;
        }
    }
    
    public String getInputQuery() {
        return inputQuery;
    }

    public void setInputQuery(String inputQuery) {
        this.inputQuery = inputQuery;
    }

    public LazyDataModel<Document> getDocLazyModel() {
        return docLazyModel;
    }

    public void setDocLazyModel(LazyDataModel<Document> docLazyModel) {
        this.docLazyModel = docLazyModel;
    }

    public int getNumResultsFound() {
        return numResultsFound;
    }

    public void setNumResultsFound(int numResultsFound) {
        this.numResultsFound = numResultsFound;
    }

    public boolean isResultDataTableRender() {
        return resultDataTableRender;
    }

    public void setResultDataTableRender(boolean resultDataTableRender) {
        this.resultDataTableRender = resultDataTableRender;
    }

    public boolean isMsgResultSetRender() {
        return msgResultSetRender;
    }

    public void setMsgResultSetRender(boolean msgResultSetRender) {
        this.msgResultSetRender = msgResultSetRender;
    }

    public boolean isIsSearching() {
        return isSearching;
    }

    public void setIsSearching(boolean isSearching) {
        this.isSearching = isSearching;
    }

    public LocalizaMediator getSearcherMediator() {
        return searcherMediator;
    }

    protected void displayGeneralFatalMessage() {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
        FacesMessage exceptionMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, 
                        bundle.getString("fatal_message"), null);
        context.addMessage(null, exceptionMessage);
    }
    
    private void displayParserErrorMessage() {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
        FacesMessage exceptionMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, 
                        bundle.getString("parser_message"), null);
        context.addMessage(null, exceptionMessage);
    }
}
