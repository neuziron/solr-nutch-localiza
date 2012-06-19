/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.trees.tresearch.web.util;

import com.br.localiza.web.util.LocalizaMediator;
import com.br.localiza.model.Document;
import com.br.localiza.model.SolrResponse;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author thiago.rodrigues
 */
public class SearcherMediatorTest {
    
    private final String URL_GOOGLE = "www.google.com";
    private final String URL_UOL    = "www.uol.com";
    
    private LocalizaMediator mediator;
    
    public SearcherMediatorTest() {
        this.mediator = new LocalizaMediator();
    }
    
   @Test
    public void xmlToBeanWithoutHighlight() {
        StringReader xmlReader = new StringReader(
          "<?xml version='1.0' ?><response><lst>header</lst><result>"
                + "<doc><str name='title'>Google Search</str><str name='url'>www.google.com</str></doc>"
                + "<doc><str name='title'>UOL Portal</str><str name='url'>www.uol.com</str></doc>" 
                + "</result></response>");
        try {
            SolrResponse solrResponse = this.mediator.xmlToBean(xmlReader);
            solrResponse.buildResults();
            Assert.assertEquals(2, solrResponse.getResult().getDocumentList().size());
            
            // It extracts urls from list of documents.
            List<String> urlList = new ArrayList<String>();
            for (Object objDoc : solrResponse.getResult().getDocumentList().toArray()) 
                urlList.add(((Document) objDoc).getUrl());
                
            Assert.assertTrue(urlList.contains("www.uol.com"));
            Assert.assertTrue(urlList.contains("www.google.com"));
        } 
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        } 
    }
    
    
    @Test
    public void xmlToBeanWithHighlight() {
        
        String googleFragment = "Site do google hightlighted";
        
        StringReader xmlReader = new StringReader(
          "<?xml version='1.0' ?><response><lst>header</lst><result>"
                + "<doc><str name='title'>Google Search</str><str name='url'>www.google.com</str></doc>"
                + "<doc><str name='title'>UOL Portal</str><str name='url'>www.uol.com</str></doc>"
                + "</result>"
                + "<lst name='highlighting'><lst name='www.google.com'><arr name='content'><str>"
                + googleFragment
                + "</str></arr></lst></lst></response>");
        try {
            SolrResponse solrResponse = this.mediator.xmlToBean(xmlReader);
            solrResponse.buildResults();
            Assert.assertEquals(2, solrResponse.getResult().getDocumentList().size());
            
            Map<String, Document> docMap = solrResponse.getResult().getDocumentMap();
            Assert.assertEquals(URL_GOOGLE, docMap.get(URL_GOOGLE).getUrl());
            Assert.assertEquals(URL_UOL, docMap.get(URL_UOL).getUrl());
            Assert.assertEquals(googleFragment + " ... ", docMap.get(URL_GOOGLE).getHighlightContent());
        } 
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        } 
    }

}
