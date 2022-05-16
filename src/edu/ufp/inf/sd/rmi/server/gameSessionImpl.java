package edu.ufp.inf.sd.rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class gameSessionImpl extends UnicastRemoteObject implements gameSessionRI {

    gameFactoryImpl gameFactoryImpl;

    public gameSessionImpl(gameFactoryImpl gameFactoryImpl) throws RemoteException{
        super();
        this.gameFactoryImpl = gameFactoryImpl;
    }
}
