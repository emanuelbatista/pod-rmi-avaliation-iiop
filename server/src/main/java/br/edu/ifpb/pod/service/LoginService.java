/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pod.service;

import br.edu.ifpb.pod.to.AcessTO;
import br.edu.ifpb.pod.to.CredentialTO;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class LoginService {

    private long generateToken;
    private final Map<String, AcessTO> users;

    public LoginService() {
        this.generateToken = 0L;
        this.users = new HashMap<>();
    }

    public AcessTO login(CredentialTO credential) {
        if (credential.getUserName() != null && !credential.getUserName().isEmpty()) {
            AcessTO acessTO = new AcessTO();
            acessTO.setToken((++generateToken) + credential.getUserName());
            users.put(acessTO.getToken(), acessTO);
            return acessTO;
        } else {
            return null;
        }
    }
    
    public void logout(AcessTO to){
        this.users.remove(to.getToken());
    }
    
    public boolean isLoger(AcessTO to){
        return this.users.containsKey(to.getToken());
    }

    public Map<String, AcessTO> getUsers() {
        return users;
    }
    
    
    
    
}
