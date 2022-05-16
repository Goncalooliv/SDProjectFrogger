package edu.ufp.inf.sd.rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class gameFactoryImpl extends UnicastRemoteObject implements gameFactoryRI {

    public gameFactoryImpl () throws RemoteException {
        super();
    }

    @Override
    public void register(String email, String password) throws RemoteException {

    }

    @Override
    public gameSessionRI login(String email, String password) throws RemoteException {
        return null;
    }
}
