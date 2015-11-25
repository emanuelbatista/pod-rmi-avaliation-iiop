/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pod.interfaces;

import br.edu.ifpb.pod.to.AcessTO;
import br.edu.ifpb.pod.to.CredentialTO;
import br.edu.ifpb.pod.to.MensageTO;
import br.edu.ifpb.pod.to.QuantityTO;
import br.edu.ifpb.pod.exception.LoginException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
*
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public interface RMIService extends Remote{
    
    AcessTO login(CredentialTO cto) throws RemoteException,LoginException;
    
    void send(AcessTO acess,MensageTO msn) throws RemoteException,LoginException;
    
    List<MensageTO> receiveAll(AcessTO acess) throws RemoteException,LoginException;
    
    QuantityTO howMany(AcessTO acess) throws RemoteException,LoginException;
    
    void logout(AcessTO acess) throws RemoteException;
    
}
