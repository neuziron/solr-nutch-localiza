/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.localiza.model;

/**
 *
 * @author thiago.rodrigues
 */
public class HighlightInformation extends Information {
    
    @Override
    public void setValue(String value) {
        super.setValue(value.replaceAll("em>", "b>"));
    }
}
