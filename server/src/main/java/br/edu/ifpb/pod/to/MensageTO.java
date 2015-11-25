/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pod.to;

import java.io.Serializable;

/**
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class MensageTO implements Serializable{
    
    private static final long serialVersionUID=1L;
    private String msn;

    public MensageTO() {
    }

    public MensageTO(String msn) {
        this.msn = msn;
    }
    

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }
    
    
    
}
