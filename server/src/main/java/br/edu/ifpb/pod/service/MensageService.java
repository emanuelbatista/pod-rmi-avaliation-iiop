/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pod.service;

import br.edu.ifpb.pod.to.AcessTO;
import br.edu.ifpb.pod.to.MensageTO;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class MensageService {
    
    private final Map<String,List<MensageTO>> userMensages;
    private final List<MensageTO> mensageAll;

    public MensageService() {
        this.userMensages=new HashMap<>();
        this.mensageAll=new LinkedList<>();
    }
    
    

    public void send(AcessTO acessTO, MensageTO mensageTO) {
      userMensages.forEach((key,value)->{
          value.add(mensageTO);
      });
      mensageAll.add(mensageTO);
    }
    
    public boolean containsMensages(AcessTO acess){
        boolean containsUserMensages=userMensages.containsKey(acess.getToken());
        boolean containsMensages=mensageAll.size()>0;
        return containsMensages || containsUserMensages;
    }
    public List<MensageTO> receiveAll(AcessTO acess){
        if(!userMensages.containsKey(acess.getToken())){
            userMensages.put(acess.getToken(), new LinkedList<>());
            return mensageAll;
        }
        List<MensageTO> msns=userMensages.get(acess.getToken());
        userMensages.replace(acess.getToken(), new LinkedList<>());
        return msns;
    }

    public Map<String, List<MensageTO>> getUserMensages() {
        return userMensages;
    }

    public List<MensageTO> getMensageAll() {
        return mensageAll;
    }
    
    

}
