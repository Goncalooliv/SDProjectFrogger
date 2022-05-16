package edu.ufp.inf.sd.rmi.client;

import edu.ufp.inf.sd.rmi.server.gameFactoryRI;
import util.rmisetup.SetupContextRMI;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class froggerClient {

    private SetupContextRMI contextRMI;

    private gameFactoryRI gameFactoryRI;

    private String email;
    private String password;

    public static void main(String[] args) {
        try {
            loginMenu();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        /*if (args != null && args.length < 2) {
            System.err.println("usage: java [options] edu.ufp.sd.inf.rmi._01_helloworld.server.HelloWorldClient <rmi_registry_ip> <rmi_registry_port> <service_name>");
            System.exit(-1);
        } else {
            //1. ============ Setup client RMI context ============
            //froggerClient hwc= new froggerClient(args);
            //2. ============ Lookup service ============
            //hwc.lookupService();
            //3. ============ Play with service ============
            //hwc.playService();
        }*/
    }

    public froggerClient(String args[]){
        try {
            //List ans set args
            SetupContextRMI.printArgs(this.getClass().getName(), args);
            String registryIP = args[0];
            String registryPort = args[1];
            String serviceName = args[2];
            //Create a context for RMI setup
            contextRMI = new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serviceName});
        } catch (RemoteException e) {
            Logger.getLogger(froggerClient.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private Remote lookupService() {
        try {
            //Get proxy MAIL_TO_ADDR rmiregistry
            Registry registry = contextRMI.getRegistry();
            //Lookup service on rmiregistry and wait for calls
            if (registry != null) {
                //Get service url (including servicename)
                String serviceUrl = contextRMI.getServicesUrl(0);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going MAIL_TO_ADDR lookup service @ {0}", serviceUrl);

                //============ Get proxy MAIL_TO_ADDR HelloWorld service ============
                gameFactoryRI = (gameFactoryRI) registry.lookup(serviceUrl);
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "registry not bound (check IPs). :(");
                //registry = LocateRegistry.createRegistry(1099);
            }
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return gameFactoryRI;
    }

    public static void loginMenu() throws RemoteException{
        System.out.println("1 - Register \n2- Login \nOption: ");
        Scanner option = new Scanner(System.in);
        Scanner email = new Scanner(System.in);
        Scanner password = new Scanner(System.in);
        Integer userChoice = option.nextInt();
        System.out.println("===========================================");
        System.out.println(userChoice);
        
    }
}
