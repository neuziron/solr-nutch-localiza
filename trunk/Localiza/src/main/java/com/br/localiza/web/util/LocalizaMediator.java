/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.localiza.web.util;

import com.br.localiza.model.Document;
import com.br.localiza.model.HighlightInformation;
import com.br.localiza.model.HighlightResult;
import com.br.localiza.model.Information;
import com.br.localiza.model.Result;
import com.br.localiza.model.SolrQuery;
import com.br.localiza.model.SolrResponse;
import java.beans.IntrospectionException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;                      
import java.io.Reader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import org.apache.commons.digester3.Digester;
import org.apache.solr.core.SolrConfig;
import org.apache.solr.core.SolrResourceLoader;
import org.apache.solr.schema.IndexSchema;
import org.apache.solr.search.SolrQueryParser;
import org.xml.sax.SAXException;

/**
 *
 * @author thiago.rodrigues
 */
public class LocalizaMediator implements Serializable {
    
    private static final String SOLR_URL = "http://toronto.tre-es.gov.br:8989/solr/";
    private static final String SOLR_INSTANCE_DIR = "/opt/apache-solr-3.5.0/example/solr/";
    
    private SolrQueryParser solrQParser;
    
    public SolrResponse executeQuery(SolrQuery query) throws Exception {

        this.getSolrQParser().parse(query.getInputQuery());
        URL solrURL = new URL(SOLR_URL + query.build());
        URLConnection solrConnection = solrURL.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader
                (solrConnection.getInputStream(), Charset.forName(SolrQuery.CHARSET)));
        
        SolrResponse solrResponse = (SolrResponse) this.xmlToBean(in);
        in.close();

        return solrResponse;
    }
    
    
    /**
     * It turn into XML answer from Solr to SolrResponse object.
     * 
     * @param inputReader XML response from Solr
     * @return SolrResponse instance with informations from Solr response
     * @throws IntrospectionException
     * @throws IOException
     * @throws SAXException 
     */
    public SolrResponse xmlToBean(Reader inputReader) throws IntrospectionException, 
            IOException, SAXException {
        
        Digester digester = new Digester();
        digester.setValidating(false);
        
        digester.addObjectCreate("response", SolrResponse.class);
        digester.addObjectCreate("response/result", Result.class);
        digester.addObjectCreate("response/result/doc", Document.class);
        digester.addObjectCreate("response/result/doc/str", Information.class);
        digester.addObjectCreate("response/lst", HighlightResult.class);
        digester.addObjectCreate("response/lst/lst", HighlightInformation.class);
        
        digester.addSetProperties("response/result", "numFound", "numResultsFound");
        digester.addBeanPropertySetter("response/result/doc/str", "value");
        digester.addSetProperties("response/result/doc/str", "name", "type");
        digester.addSetNext("response/result/doc/str", "addInformation");
        digester.addSetNext("response/result/doc", "addDocument");
        digester.addSetNext("response/result", "setResult");
       
        digester.addBeanPropertySetter("response/lst/lst/arr/str", "value");
        digester.addSetProperties("response/lst/lst", "name", "type");
        digester.addSetNext("response/lst/lst", "addInformation");
        digester.addSetNext("response/lst", "setHighlight");
        
        SolrResponse solrResponse = (SolrResponse) digester.parse(inputReader);
        
        return solrResponse;
    }
    

    public SolrQueryParser getSolrQParser() throws Exception {
        
        if (this.solrQParser == null) {
            SolrConfig solrConfig = new SolrConfig(SOLR_INSTANCE_DIR, "solrconfig.xml", null);
            SolrResourceLoader solrLoader = solrConfig.getResourceLoader();
            IndexSchema schema = new IndexSchema(solrConfig, "schema.xml", null);
            this.solrQParser = new SolrQueryParser(schema, null);
        }
        
        return this.solrQParser;
    }
    
    
}
