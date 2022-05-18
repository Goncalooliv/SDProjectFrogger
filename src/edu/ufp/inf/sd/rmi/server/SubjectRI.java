package edu.ufp.inf.sd.rmi.server;

import edu.ufp.inf.sd.rmi.client.ObserverRI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface SubjectRI  extends Remote {
    void attach(ObserverRI obsRI) throws RemoteException;
    void detach(ObserverRI obsRI) throws RemoteException;
    State getState() throws RemoteException;
    void setState(State state) throws RemoteException;
    ArrayList<ObserverRI> getObservers() throws RemoteException;
}
