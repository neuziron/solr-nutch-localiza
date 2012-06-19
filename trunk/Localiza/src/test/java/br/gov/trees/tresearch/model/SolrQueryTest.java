/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.trees.tresearch.model;

import com.br.localiza.model.SolrQuery;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author thiago.rodrigues
 */
public class SolrQueryTest {
    
    private String prefixSolrQuery;
    
    public SolrQueryTest() {
        this.prefixSolrQuery = (new SolrQuery()).getPrefixQuery();
    }
    
    private void compareQuery(String inputquery, String outputquery) {
        
        try {
            Assert.assertEquals(this.prefixSolrQuery + outputquery, 
                    new SolrQuery(inputquery).build());
        } 
        catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
    
    
    //@Test
    public void buildOneQuery() {
        String inputquery = "forum";
        String outputquery = "FORUM"; 
        compareQuery(inputquery, outputquery);
    }
  
    //@Test
    public void buildSimpleQueryWithSpaces() {
        String inputquery = "forum     forty";
        String outputquery = "FORUM+AND+FORTY";
        compareQuery(inputquery, outputquery);
    }
    
    //@Test
    public void buildSimpleQueryWithEdgeSpaces() {
        String inputquery = "    forum     forty    ";
        String outputquery = "FORUM+AND+FORTY";
        compareQuery(inputquery, outputquery);
    }
    
    //@Test
    public void buildSimpleQuery() {
        String inputquery = "forum forty";
        String outputquery = "FORUM+AND+FORTY";
        compareQuery(inputquery, outputquery);
    }
    
    //@Test
    public void buildSimpleORQuery() {
        String inputquery = "forum OR forty";
        String outputquery = "FORUM+OR+FORTY";
        compareQuery(inputquery, outputquery);
    }
    
    //@Test
    public void buildSimpleANDQuery() {
        String inputquery = "forum and forty";
        String outputquery = "FORUM+AND+FORTY";
        compareQuery(inputquery, outputquery);
    }
    
    //@Test
    public void buildRightEdgeANDOperator() {
        String inputquery = "forum forty and";
        String outputquery = "FORUM+AND+FORTY";
        compareQuery(inputquery, outputquery);
    }
    
    //@Test
    public void buildRightEdgeOROperator() {
        String inputquery = "forum forty or";
        String outputquery = "FORUM+AND+FORTY";
        compareQuery(inputquery, outputquery);
    }
    
    //@Test
    public void buildLeftEdgeANDOperator() {
        String inputquery = "and forum forty";
        String outputquery = "FORUM+AND+FORTY";
        compareQuery(inputquery, outputquery);
    }
    
    //@Test
    public void buildLeftEdgeOROperator() {
        String inputquery = "or forum forty";
        String outputquery = "FORUM+AND+FORTY";
        compareQuery(inputquery, outputquery);
    }
    
    //@Test
    public void buildBothEdgeOperators() {
        String inputquery = "or forum forty and";
        String outputquery = "FORUM+AND+FORTY";
        compareQuery(inputquery, outputquery);
    }
    
    //@Test
    public void buildMultiplyOperators() {
        String inputquery = "or forum and or and and or or forty and";
        String outputquery = "FORUM+OR+FORTY";
        compareQuery(inputquery, outputquery);
    }
    
    //@Test
    public void buildMultiplyEdgeOperators() {
        String inputquery = "or and or and forum and or and and or or forty and and and or";
        String outputquery = "FORUM+OR+FORTY";
        compareQuery(inputquery, outputquery);
    }
    
    //@Test
    public void buildMultiplyANDOperators() {
        String inputquery = "and and and and forum and and and and and forty and and and and";
        String outputquery = "FORUM+AND+FORTY";
        compareQuery(inputquery, outputquery);
    }
    
    //@Test
    public void buildMultiplyOROperators() {
        String inputquery = "or or or or forum or or or or or forty or or or or";
        String outputquery = "FORUM+OR+FORTY";
        compareQuery(inputquery, outputquery);
    }
    
    //@Test
    public void buildAlternateORANDOperators() {
        String inputquery = "or and or and forum or and or and or forty and or and or";
        String outputquery = "FORUM+OR+FORTY";
        compareQuery(inputquery, outputquery);
    }
    
    //@Test
    public void buildOneUTF8Character() {
        String inputquery = "eleição";
        String outputquery = "ELEI%C3%87%C3%83O";
        compareQuery(inputquery, outputquery);
    }
    
    //@Test
    public void buildUTF8MultiplyCharacters() {
        String inputquery = "fórum eleições";
        String outputquery = "F%C3%93RUM+AND+ELEI%C3%87%C3%95ES";
        compareQuery(inputquery, outputquery);
    }
    
    //@Test
    public void buildTwoTimes() {
        String inputquery = "forum 2012";
        String outputquery = "FORUM+AND+2012";
        SolrQuery solrQ = new SolrQuery(inputquery);
        try {
            Assert.assertEquals(this.prefixSolrQuery + outputquery, solrQ.build());
            Assert.assertEquals(this.prefixSolrQuery + outputquery, solrQ.build());
        } 
        catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
    
    @Test
    public void nothing() {
       
    }
    
}
