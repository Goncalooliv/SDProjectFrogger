package edu.ufp.inf.sd.rabbitmq.chatgui;

import com.rabbitmq.client.*;
import util.RabbitUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private ServerGuiClient gui;

    //Preferences for exchange...
    private Channel channelToRabbitMq;
    private String exchangeName;
    private BuiltinExchangeType exchangeType;
    //private final String[] exchangeBindingKeys;
    private String messageFormat;

    //Store received message to be get by gui
    private String receivedMessage;

    public static ArrayList<Integer> playersArray = new ArrayList<>();
    public static int count = 0;

    public void setServer(ServerGuiClient gui, String host, int port, String user, String pass, String exchangeName, BuiltinExchangeType exchangeType, String messageFormat) throws IOException, TimeoutException{
        this.gui = gui;
        Connection connection = RabbitUtils.newConnection2Server(host,port,user,pass);
        this.channelToRabbitMq = RabbitUtils.createChannel2Server(connection);
        this.exchangeName = exchangeName;
        this.exchangeType = exchangeType;
        this.messageFormat = messageFormat;
        bindExchangeToChannelRabbitMQ();
        attachConsumerToChannelExchangeWithKey();

    }

    public void bindExchangeToChannelRabbitMQ() throws IOException{
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Declaring Exchange '" + this.exchangeName + "' with type " + this.exchangeType);
        channelToRabbitMq.exchangeDeclare(exchangeName+"server", BuiltinExchangeType.FANOUT);
    }

    public void attachConsumerToChannelExchangeWithKey(){
        try{

            String queueName = channelToRabbitMq.queueDeclare().getQueue();

            String routingKey="";
            channelToRabbitMq.queueBind(queueName,exchangeName+"server",routingKey);
            channelToRabbitMq.queuePurge(queueName);

            Logger.getLogger(this.getClass().getName()).log(Level.INFO, " Created consumerChannel bound to Exchange " + this.exchangeName + "...");

            DeliverCallback deliverCallback=(consumerTag, delivery) -> {
                String message=new String(delivery.getBody(), messageFormat);

                //Store the received message
                setReceivedMessage(message);
                //System.out.println(" [x] Consumer Tag [" + consumerTag + "] - Received '" + message + "'");


            };
            CancelCallback cancelCallback= consumerTag -> {
                System.out.println(" [x] Consumer Tag [" + consumerTag + "] - Cancel Callback invoked!");
            };
            channelToRabbitMq.basicConsume(queueName, true, deliverCallback, cancelCallback);

        }catch (Exception e){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.toString());

        }
    }

    public void sendMessage(String msgToSend) throws IOException{
        String routingKey="";
        AMQP.BasicProperties prop = MessageProperties.PERSISTENT_TEXT_PLAIN;

        channelToRabbitMq.basicPublish(exchangeName+"observer", routingKey, prop, msgToSend.getBytes("UTF-8"));
    }

    public String getReceivedMessage(){
        return receivedMessage;
    }

    public void setReceivedMessage(String message) throws IOException{
        String[] text = message.split(":");
        if(message.contains("Connected!")){
            count++;
            this.sendMessage("count:" + count);
        }

        System.out.println(Arrays.toString(text));
        if(message.contains("Down") || message.contains("Up") || message.contains("Left") || message.contains("Right")){
            this.sendMessage(message);
        }
        /*if(!receivedMessage.contains("Connected")){
            String[] text = receivedMessage.split(":");
            this.receivedMessage = text[1];
            System.out.println(Arrays.toString(text));
            this.playersArray.add(Integer.parseInt(text[1]));
        }else{
            this.receivedMessage = message;
        }*/
    }


}
