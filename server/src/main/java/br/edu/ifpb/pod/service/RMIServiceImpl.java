/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pod.service;

import br.edu.ifpb.pod.interfaces.RMIService;
import br.edu.ifpb.pod.to.AcessTO;
import br.edu.ifpb.pod.to.CredentialTO;
import br.edu.ifpb.pod.to.MensageTO;
import br.edu.ifpb.pod.to.QuantityTO;
import br.edu.ifpb.pod.exception.LoginException;
import java.rmi.RemoteException;
import java.util.List;
import javax.rmi.PortableRemoteObject;

/**
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class RMIServiceImpl extends PortableRemoteObject implements RMIService {

    private MensageService mensageService;
    private LoginService loginService;

    public RMIServiceImpl() throws RemoteException {
        super();
        this.loginService = new LoginService();
        this.mensageService = new MensageService();
    }

    @Override
    public AcessTO login(CredentialTO cto) throws RemoteException, LoginException {
        AcessTO acess=loginService.login(cto);
        if(mensageService.containsMensages(acess)){
            acess.setContainsMensage(true);
        }
        return acess;
    }

    @Override
    public void send(AcessTO acess, MensageTO msn) throws RemoteException, LoginException {
        if (loginService.isLoger(acess)) {
            mensageService.send(acess, msn);
        } else {
            throw new LoginException();
        }
    }

    @Override
    public List<MensageTO> receiveAll(AcessTO acess) throws RemoteException, LoginException {
        if (loginService.isLoger(acess)) {
            return mensageService.receiveAll(acess);
        } else {
            throw new LoginException();
        }
    }

    @Override
    public QuantityTO howMany(AcessTO acess) throws RemoteException, LoginException {
        if (loginService.isLoger(acess)) {
            return new QuantityTO(loginService.getUsers().size());
        }else{
            throw new LoginException();
        }
    }

    @Override
    public void logout(AcessTO acess) throws RemoteException {
        loginService.logout(acess);
    }

}
