/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.localiza.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 *
 * @author thiago.rodrigues
 * 
 * @since  09-03-2012
 */
public class SolrQuery implements Serializable {

    public static final String CHARSET = "UTF-8";
    
    /*
     * Query's key parameters
     */
    private final String GET_PARAM_SEPARATOR = "&";
    private final String SOLR_PREFIX = "select?";
    private final String SOLR_DEFTYPE_KEY = "q=";
    private final String SOLR_LOGIC_OPERATOR = "q.op=AND";
    private final String START_RANGE_KEY = "start=";
    private final String SIZE_RANGE_KEY = "rows=";
    private final String HIGHLIGHT_ENABLE_KEY = "hl=";
    private final String HIGHLIGHT_FRAGSIZE_KEY = "hl.fragsize=";
    
    /*
     * Query's default values
     */
    private final int START_RANGE_VALUE = 0;
    private final int SIZE_RANGE_VALUE = 10;
    private final boolean HIGHLIGHT_ENABLE_VALUE = true;
    private final int HIGHLIGHT_FRAGSIZE_VALUE = 400;
    
    
    private String inputQuery;
    
    private int startRow;
    
    private int pageSize;
    
    private boolean highLightEnable;
    
    private int highLightFragsize;
    
    public SolrQuery() {
        this.initDefaultValues();
    }
    
    public SolrQuery(String stringQuery) {
        this();
        this.inputQuery = stringQuery;
    }
    
    private void initDefaultValues() {
        this.startRow = START_RANGE_VALUE;
        this.pageSize = SIZE_RANGE_VALUE;
        this.highLightEnable = HIGHLIGHT_ENABLE_VALUE;
        this.highLightFragsize = HIGHLIGHT_FRAGSIZE_VALUE;
    }

    /**
     * Generate the Solr query prefix to HTTP-GET request.
     * 
     * @return query solr prefix
     */
    public String getPrefixQuery() {
        return SOLR_PREFIX.
                concat(HIGHLIGHT_ENABLE_KEY).
                concat(String.valueOf(this.highLightEnable)).concat(GET_PARAM_SEPARATOR).
                concat(HIGHLIGHT_FRAGSIZE_KEY).
                concat(String.valueOf(this.highLightFragsize)).concat(GET_PARAM_SEPARATOR).
                concat(START_RANGE_KEY).
                concat(String.valueOf(this.startRow)).concat(GET_PARAM_SEPARATOR).
                concat(SIZE_RANGE_KEY).
                concat(String.valueOf(this.pageSize)).concat(GET_PARAM_SEPARATOR).
                concat(SOLR_LOGIC_OPERATOR).concat(GET_PARAM_SEPARATOR).
                concat(SOLR_DEFTYPE_KEY);
    }
    
    /**
     * Perform the transformation from user inputed query to
     * solr query. 
     */
    public String build() throws UnsupportedEncodingException {
        return this.getPrefixQuery().concat(URLEncoder.encode(this.inputQuery, CHARSET));
    }

    /*
     * Getters/Setters
     */
    
    public boolean isHighLightEnable() {
        return highLightEnable;
    }

    public void setHighLightEnable(boolean highLightEnable) {
        this.highLightEnable = highLightEnable;
    }

    public int getHighLightFragsize() {
        return highLightFragsize;
    }

    public void setHighLightFragsize(int highLightFragsize) {
        this.highLightFragsize = highLightFragsize;
    }

    public String getInputQuery() {
        return inputQuery;
    }

    public void setInputQuery(String inputQuery) {
        this.inputQuery = inputQuery;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }
    
}
