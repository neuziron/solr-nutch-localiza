/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.localiza.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thiago.rodrigues
 */
public class Document implements Serializable {
    
    private final int LENGTH_CONTENT_PER_LINE = 400;
    private final int MAX_LENGHT_URL = 100;
    private final int MAX_LENGTH_TITLE = 90;
    private final String ELLIPSES = " ... ";
    
    private final String INFO_TITLE   = "title";
    private final String INFO_URL     = "url";
    private final String INFO_CONTENT = "content";
    
    private List<Information> informationList;
    
    private String title;
    
    private String url;
    
    private String content;
    
    private String highlightContent;
    
    
    public Document() {
        this.informationList = new ArrayList<Information>();
    }

    public String getReducedUrl() {
        return this.url.length() > MAX_LENGHT_URL ?
                this.url.substring(0, MAX_LENGHT_URL - 3).
                replaceFirst("\\s\\S+$|\\s$", ELLIPSES).concat(ELLIPSES) :
                this.url;
    }
    
    public String getContentResume() {
        return this.content.substring(0, LENGTH_CONTENT_PER_LINE)
                    .replaceFirst("\\s\\S+$|\\s$", ELLIPSES);
    }
    
    public void addInformation(Information info) {
        this.informationList.add(info);
    }

    public void buildInformation() {
        if (this.informationList == null || informationList.isEmpty()) return;
        for (Information info : this.informationList) this.buildInformation(info);
    }
    
    public void buildInformation(Information info) {
        if (info instanceof HighlightInformation) 
            this.highlightContent = info.getValue();
        else if (info.getType().equalsIgnoreCase(INFO_TITLE))
            this.title = info.getValue();
        else if (info.getType().equalsIgnoreCase(INFO_URL))
            this.url = info.getValue();
        else if (info.getType().equalsIgnoreCase(INFO_CONTENT))
            this.content = info.getValue();   
    }
    
    // getters e setters
    
    public List<Information> getInformationList() {
        return informationList;
    }

    public void setInformationList(List<Information> informationList) {
        this.informationList = informationList;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title == null || title.length() == 0 ? 
                content.substring(0, MAX_LENGTH_TITLE).concat(ELLIPSES) : title;
    }

    public String getUrl() {
        return url;
    }

    public void setHighlightContent(String highlightContent) {
        this.highlightContent = highlightContent;
    }

    
    public String getHighlightContent() {
        return highlightContent + ELLIPSES;
    }
}
