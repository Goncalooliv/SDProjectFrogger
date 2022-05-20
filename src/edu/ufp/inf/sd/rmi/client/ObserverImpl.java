package edu.ufp.inf.sd.rmi.client;

import edu.ufp.inf.sd.rmi.frogger.Main;
import edu.ufp.inf.sd.rmi.server.State;
import edu.ufp.inf.sd.rmi.server.SubjectRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ObserverImpl extends UnicastRemoteObject implements ObserverRI {
    private int id;
    private State lastObserverState;
    protected SubjectRI subjectRI;
    private Main main;

    public ObserverImpl(int id) throws RemoteException{
        this.id = id;
    }

    public int getId() throws RemoteException{
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public State getLastObserverState() {
        return lastObserverState;
    }

    @Override
    public void update(State state) throws RemoteException{
        //this.lastObserverState = state;
        this.main.stateHandler(state);
    }

    public void setSubjectRI(SubjectRI subjectRI) throws RemoteException{
        this.subjectRI = subjectRI;
    }

    @Override
    public SubjectRI getSubjectRI() throws RemoteException {
        return subjectRI;
    }

    @Override
    public void setMain(Main main) throws RemoteException {
        this.main = main;
    }

    /*@Override
    public Main getMain() throws RemoteException {
        return main;
    }*/

}
