package edu.ufp.inf.sd.rmi.server;

import edu.ufp.inf.sd.rmi.client.ObserverRI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface GameSessionRI extends Remote {

    void logout() throws RemoteException;

    Jogo createJogo(String dificuldade, ObserverRI observerRI) throws RemoteException;
    Jogo joinJogo(int id,ObserverRI observerRI) throws RemoteException;
    //ArrayList<Jogo> printFroggerGameList();
}
