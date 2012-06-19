/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.localiza.model;

import java.io.Serializable;

/**
 *
 * @author thiago.rodrigues
 */
public class Information implements Serializable {
    
    private String type;
    
    private String value;

    // getters e setters
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
}
