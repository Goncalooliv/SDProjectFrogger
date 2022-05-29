package edu.ufp.inf.sd.rabbitmq.chatgui;

import com.rabbitmq.client.BuiltinExchangeType;
import util.RabbitUtils;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerGuiClient extends JFrame {

    private Server server;

    /**
     * Creates new form ChatClientFrame
     *
     * @param args
     */
    public ServerGuiClient(String args[]) throws IOException, TimeoutException {
        //1. Init the GUI components
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, " After initComponents()...");

        RabbitUtils.printArgs(args);

        //Read args passed via shell command
        String host=args[0];
        int port=Integer.parseInt(args[1]);
        String exchangeName=args[2];

        //2. Create the _05_observer object that manages send/receive of messages to/from rabbitmq
        this.server = new Server();
        //this.observer= new Observer(this, host, port, "guest", "guest", exchangeName, BuiltinExchangeType.FANOUT, "UTF-8", user);
        this.server.setServer(this, host, port, "guest", "guest", exchangeName, BuiltinExchangeType.FANOUT, "UTF-8");
    }

    //================================================ BEGIN TO CHANGE ================================================

    /**
     * This method will be called by the _05_observer, to notify/update the GUI,
     * whenever a new msg is received/consumed from the broker.
     */
    public void updateUser() {

    }

    /**
     * Sends msg through the _05_observer to the exchange where all observers are binded
     *
     * @param msgToSend
     */
    private void sendState(String user, String msgToSend) {
        try {
            msgToSend = "["+user+"]: " + msgToSend;
            this.server.sendMessage(msgToSend);
        } catch (IOException ex) {
            Logger.getLogger(ServerGuiClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException, TimeoutException {
        new ServerGuiClient(args);
    }


}