package edu.ufp.inf.sd.rmi.client;

import edu.ufp.inf.sd.rmi.server.State;
import edu.ufp.inf.sd.rmi.server.SubjectRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ObserverImpl extends UnicastRemoteObject implements ObserverRI {
    //private String id;
    private State lastObserverState;
    protected SubjectRI subjectRI;

    protected ObserverImpl(SubjectRI subjectRI) throws RemoteException {
        super();
        this.subjectRI = subjectRI;
        this.subjectRI.attach(this);
    }

    public State getLastObserverState() {
        return lastObserverState;
    }

    @Override
    public void update() throws RemoteException{
        this.lastObserverState = subjectRI.getState();
    }
}
