package edu.ufp.inf.sd.rmi.server;

import edu.ufp.inf.sd.rmi.client.ObserverRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class SubjectImpl extends UnicastRemoteObject implements SubjectRI {
    private State subjectState;
    public ArrayList<ObserverRI> observers = new ArrayList<>();


    protected SubjectImpl() throws RemoteException {
        this.subjectState = new State(0, "");
        //this.observers = new ArrayList<>();
    }

    /**
     * Retorna o array de observers
     * @return
     * @throws RemoteException
     */
    public ArrayList<ObserverRI> getObservers() throws RemoteException {
        return observers;
    }

    protected SubjectImpl(State subjectState) throws RemoteException {
        this.subjectState = subjectState;
        this.observers = new ArrayList<ObserverRI>();
    }

    /**
     * Metodo usado para dar attach do observer ao array
     * @param obsRI
     * @throws RemoteException
     */
    @Override
    public void attach(ObserverRI obsRI) throws RemoteException{
        this.observers.add(obsRI);
    }

    /**
     * Metodo usado para dar detach do observer
     * @param obsRI
     * @throws RemoteException
     */
    @Override
    public void detach(ObserverRI obsRI) throws RemoteException{
        this.observers.remove(obsRI);
    }

    @Override
    public State getState() throws RemoteException{
        return this.subjectState;
    }

    /**
     * Metodo usado para as mudanças de estado de um observer, por exemplo, movimentação de um frog
     * @param state
     * @throws RemoteException
     */
    @Override
    public void setState(State state) throws RemoteException{
        this.subjectState = state;
        notifyAllObservers(state);
    }

    /**
     * Notificação de todos os observers da mudança de estado
     * @param state
     * @throws RemoteException
     */
    public void notifyAllObservers(State state) throws RemoteException {
        for (ObserverRI observer : observers) {
            observer.update(state);
        }
    }

}
