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
public class QuantityTO implements Serializable{
    
    private final int quant;

    public QuantityTO(int quant) {
        this.quant = quant;
    }

    public int getQuant() {
        return quant;
    }
    
    
    
    

    
    
}
