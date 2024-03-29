package edu.ufp.inf.sd.rmi.client;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import edu.ufp.inf.sd.rmi.frogger.Main;
import edu.ufp.inf.sd.rmi.server.*;
import util.rmisetup.SetupContextRMI;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FroggerClient {

    private SetupContextRMI contextRMI;

    private static GameFactoryRI gameFactoryRI;

    private static String email;
    private static String password;

    public static void main(String[] args) {
        /*try {
            autenticationMenu();
        } catch (RemoteException e) {
            e.printStackTrace();
        }*/
        if (args != null && args.length < 2) {
            System.err.println("usage: java [options] edu.ufp.sd.inf.rmi._01_helloworld.server.HelloWorldClient <rmi_registry_ip> <rmi_registry_port> <service_name>");
            System.exit(-1);
        } else {
            //1. ============ Setup client RMI context ============
            FroggerClient hwc= new FroggerClient(args);
            //2. ============ Lookup service ============//
            hwc.lookupService();
            //3. ============ Play with service ============
            hwc.playService();

            System.out.println("PUTA QUE PARIU ESTE LIXO :)");
        }
    }

    public FroggerClient(String args[]) {
        try {
            //List ans set args
            SetupContextRMI.printArgs(this.getClass().getName(), args);
            String registryIP = args[0];
            String registryPort = args[1];
            String serviceName = args[2];
            //Create a context for RMI setup
            contextRMI = new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serviceName});
        } catch (RemoteException e) {
            System.out.println("REMOTE EXCEPTION");
            Logger.getLogger(FroggerClient.class.getName()).log(Level.SEVERE, null, e);
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
                gameFactoryRI = (GameFactoryRI) registry.lookup(serviceUrl);
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "registry not bound (check IPs). :(");
                //registry = LocateRegistry.createRegistry(1099);
            }
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return gameFactoryRI;
    }

    private void playService() {
        try {
            GameSessionRI gameSessionRI = autenticationMenu();
            gameOptions(gameSessionRI);

            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going MAIL_TO_ADDR finish, bye. ;)");
        } catch (RemoteException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo para efetuar o registo / login
     * @return
     * @throws RemoteException
     */
    public GameSessionRI autenticationMenu() throws RemoteException {
        System.out.println("Register) \nLogin) ");
        System.out.print("Input: ");
        Scanner userInput = new Scanner(System.in);
        String userChoice = userInput.next();
        System.out.println("===========================================");
        System.out.println(userChoice);
        switch (userChoice) {
            case "Register" -> {
                System.out.println("=====Register=====");
                System.out.println("Email: ");
                Scanner emailInput = new Scanner(System.in);
                email = emailInput.next();
                System.out.println(email);
                System.out.println("Password: ");
                Scanner passwordInput = new Scanner(System.in);
                password = passwordInput.next();
                System.out.println(password);
                gameFactoryRI.register(email, password);
                return autenticationMenu();
            }
            case "Login" -> {
                System.out.println("=====Login=====");
                System.out.println("Email: ");
                Scanner emailInput = new Scanner(System.in);
                email = emailInput.next();
                System.out.println(email);
                System.out.println("Password: ");
                Scanner passwordInput = new Scanner(System.in);
                password = passwordInput.next();
                System.out.println("Login: " + email);
                return gameFactoryRI.login(email,password);

            }
            default -> {
                System.out.println("Choose Something pelise");
                return autenticationMenu();
            }
        }
    }

    /**
     * Menu de jogo com as diferentes opções (Criar jogo, Juntar, Listar, Exit)
     * @param gameSessionRI
     * @throws RemoteException
     */
    public void gameOptions(GameSessionRI gameSessionRI) throws RemoteException{
        System.out.println("Create Game) \nJoin Game) \nList Games) \nExit)");
        System.out.print("Input: ");
        Scanner userInput = new Scanner(System.in);
        String userChoice = userInput.next();

        switch(userChoice){
            case "Create" -> {
                gameCreation(gameSessionRI);
                break;
            }
            case "Join" -> {
                joinGame(gameSessionRI);
                break;
            }
            case "List" -> {
                ArrayList<Jogo> jogos = gameSessionRI.printFroggerGameList();
                for(Jogo jogo : jogos){
                    System.out.println("jogo: " + jogo.id);
                }
                gameOptions(gameSessionRI);

            }
            case "Exit" -> {
                System.out.println("Are you sure you want to leave (Y|N)?");
                Scanner confirmation = new Scanner(System.in);
                String confirmationString = confirmation.next();
                switch(confirmationString.toUpperCase(Locale.ROOT)){
                    case "Y" -> {
                        System.out.println("Thank you for playing!!!!!");
                        System.exit(0);
                    }
                    case "N" -> gameOptions(gameSessionRI);

                }
                if(confirmation.next().equalsIgnoreCase("Y")){
                    System.out.println("Thank you for playing!!!!!");
                    break;
                }else if(confirmation.next().equalsIgnoreCase("N")){
                    gameOptions(gameSessionRI);
                }else if(!confirmation.next().equalsIgnoreCase("Y") || !confirmation.next().equalsIgnoreCase("N")){
                    System.out.println("Only Y for Yes or N for No are possible to choose");

                }
            }
            default -> {
                System.out.println("Choose Something pelise");
                gameOptions(gameSessionRI);
            }


        }
    }

    /**
     * Menu usado para a criação do jogo (introduzir a dificuldade, numero de players, etc)
     * @param gameSessionRI
     * @return
     * @throws RemoteException
     */
    private static Jogo gameCreation(GameSessionRI gameSessionRI) throws RemoteException{
        Scanner dificuldade = new Scanner(System.in);
        System.out.print("Chose the difficulty(Ez,Normal,Hardcore) : ");
        String difficulty = dificuldade.next();
        System.out.println("Dificuldade Do Jogo : " + difficulty);
        Scanner numero = new Scanner(System.in);
        System.out.print("Number of Players : ");
        int number = numero.nextInt();
        System.out.println("numero de players: " + number);

        ObserverRI observerRI = new ObserverImpl(0);

        Jogo jogo = gameSessionRI.createJogo(number,difficulty,observerRI);
        observerRI.setSubjectRI(jogo.getSubjectRI());

        while(observerRI.getSubjectRI().getObservers().size() < 2){
        }

        Main main = new Main(jogo,observerRI);
        main.run();

        return jogo;

    }

    /**
     * Metodo usado quando a opção de "Join Game" é escolhida
     * Lista os jogos disponiveis, cria um observer e dá attach do player ao jogo
     * @param gameSessionRI
     * @return
     * @throws RemoteException
     */
    public static Jogo joinGame(GameSessionRI gameSessionRI) throws RemoteException{
        System.out.println("Available Game Lobbies: ");
        ArrayList<Jogo> jogos = gameSessionRI.printFroggerGameList();
        for(Jogo jogo : jogos){
            System.out.println("jogo: " + jogo.id);
        }

        System.out.println("Choose a game primo: ");
        Scanner userChoice = new Scanner(System.in);
        int choice = userChoice.nextInt();

        ObserverRI observerRI = new ObserverImpl(1);

        Jogo jogo = gameSessionRI.joinJogo(choice, observerRI);

        System.out.println("It's Showtime :)");

        Main main2 = new Main(jogo,observerRI);
        main2.run();

        return jogo;
    }

}
