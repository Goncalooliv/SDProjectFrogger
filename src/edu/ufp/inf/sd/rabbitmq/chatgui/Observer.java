package edu.ufp.inf.sd.rabbitmq.chatgui;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.*;
import edu.ufp.inf.sd.rabbitmq.frogger.Main;
import util.RabbitUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author rui
 */
public class Observer {

    //Reference for gui
    private ObserverGuiClient gui;

    //Preferences for exchange...
    private Channel channelToRabbitMq;
    private String exchangeName;
    private BuiltinExchangeType exchangeType;
    //private final String[] exchangeBindingKeys;
    private String messageFormat;

    //Store received message to be get by gui
    private String receivedMessage;
    private int playerID;
    private Main game;
    public int count = 0;
    public ArrayList<Integer> players = new ArrayList<>();
    public int getPlayerNumber(){
        return players.size();
    }

    public ArrayList<Integer> getPlayersTotal(){
        return this.players;
    }

    public int getPlayerID(){
        return playerID;
    }

    public Observer(){

    }

    /**
     * @param gui
     */
    public void setObserver(ObserverGuiClient gui, String host, int port, String user, String pass, String exchangeName, BuiltinExchangeType exchangeType, String messageFormat, int playerID, Main game) throws IOException, TimeoutException{
        this.gui = gui;
        Connection connection = RabbitUtils.newConnection2Server(host,port,user,pass);
        this.channelToRabbitMq = RabbitUtils.createChannel2Server(connection);
        this.exchangeName = exchangeName;
        this.exchangeType = exchangeType;
        this.messageFormat = messageFormat;
        this.game = game;
        this.playerID = playerID;
        players.add(playerID);
        bindExchangeToChannelRabbitMQ();
        attachConsumerToChannelExchangeWithKey();
        this.sendMessage("Connected! Player:" + playerID);
    }

    public void setPlayerID(int playerID){
        this.playerID = playerID;
    }

    /**
     * Binds the channel to given exchange name and type.
     */
    private void bindExchangeToChannelRabbitMQ() throws IOException {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Declaring Exchange '" + this.exchangeName + "' with type " + this.exchangeType);
        channelToRabbitMq.exchangeDeclare(exchangeName+"observer", BuiltinExchangeType.FANOUT);
    }

    /**
     * Creates a Consumer associated with an unnamed queue.
     */
    public void attachConsumerToChannelExchangeWithKey() {
        try {

            String queueName = channelToRabbitMq.queueDeclare().getQueue();

            String routingKey = "";
            channelToRabbitMq.queueBind(queueName, exchangeName+"observer", routingKey);
            channelToRabbitMq.queuePurge(queueName);

            Logger.getLogger(this.getClass().getName()).log(Level.INFO, " Created consumerChannel bound to Exchange " + this.exchangeName + "...");

            /* Use a DeliverCallback lambda function instead of DefaultConsumer to receive messages from queue;
               DeliverCallback is an interface which provides a single method:
                void handle(String tag, Delivery delivery) throws IOException; */
            DeliverCallback deliverCallback=(consumerTag, delivery) -> {
                String message=new String(delivery.getBody(), messageFormat);

                //Store the received message
                setReceivedMessage(message);
                System.out.println(" [x] Consumer Tag [" + consumerTag + "] - Received '" + message + "'");


            };
            CancelCallback cancelCallback=consumerTag -> {
                System.out.println(" [x] Consumer Tag [" + consumerTag + "] - Cancel Callback invoked!");
            };
            channelToRabbitMq.basicConsume(queueName,true,deliverCallback,cancelCallback);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.toString());
        }
    }

    /**
     * Publish messages to existing exchange instead of the nameless one.
     * - The routingKey is empty ("") since the fanout exchange ignores it.
     * - Messages will be lost if no queue is bound to the exchange yet.
     * - Basic properties can be: MessageProperties.PERSISTENT_TEXT_PLAIN, etc.
     */
    public void sendMessage(String msgToSend) throws IOException {
        //RoutingKey will be ignored by FANOUT exchange
        String routingKey="";
        BasicProperties prop = MessageProperties.PERSISTENT_TEXT_PLAIN;

        channelToRabbitMq.basicPublish(exchangeName+"server", routingKey, prop, msgToSend.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * @return the most recent message received from the broker
     */
    public String getReceivedMessage() {
        return receivedMessage;
    }

    /**
     * Dá-mos split à mensagem para assim conseguirmos saber o id do jogador que quer, por exemplo, efetuar movimentos com o seu sapo
     * @param receivedMessage the received message to set
     */
    public void setReceivedMessage(String receivedMessage) throws RemoteException {
        /*if(!receivedMessage.contains("connected")){
            String[] data = receivedMessage.split(":");
            this.receivedMessage = data[1];
            System.out.println(Arrays.toString(data));
            this.game.stateHandler(Integer.parseInt(data[0]),data[1]);
        }else{
            this.receivedMessage = receivedMessage;
        }*/
        String[] text = receivedMessage.split(":");
        if(receivedMessage.contains("Down") || receivedMessage.contains("Up") || receivedMessage.contains("Left") || receivedMessage.contains("Right")){
            this.game.stateHandler(Integer.parseInt(text[0]),text[1]);
        }
        if(receivedMessage.contains("count")){
            this.count = Integer.parseInt(text[1]);
        }
        this.receivedMessage = receivedMessage;
    }

    public int getCount() {
        return count;
    }
}
