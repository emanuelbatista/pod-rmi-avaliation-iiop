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
public class AcessTO implements Serializable{
    
    private String token;
    private boolean containsMensage;

    public AcessTO() {
    }

    
    public AcessTO(String token) {
        this.token = token;
        this.containsMensage=false;
    }

    public AcessTO(String token, boolean containsMensage) {
        this.token = token;
        this.containsMensage = containsMensage;
    }
    
    

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isContainsMensage() {
        return containsMensage;
    }

    public void setContainsMensage(boolean containsMensage) {
        this.containsMensage = containsMensage;
    }
    
    
}
