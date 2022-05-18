package edu.ufp.inf.sd.rmi.client;

import edu.ufp.inf.sd.rmi.frogger.Main;
import edu.ufp.inf.sd.rmi.server.SubjectRI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ObserverRI extends Remote {
    void update() throws RemoteException;
    void setSubjectRI(SubjectRI subjectRI) throws RemoteException;
    SubjectRI getSubjectRI() throws RemoteException;
    //void setMain(Main main) throws RemoteException;
    //Main getMain() throws RemoteException;

}
