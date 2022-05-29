package edu.ufp.inf.sd.rabbitmq.producer;
import com.rabbitmq.client.*;
import util.RabbitUtils;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQ speaks multiple protocols. This tutorial uses AMQP 0-9-1, which is
 * an open, general-purpose protocol for messaging. There are a number of
 * clients for RabbitMQ in many different languages. We'll use the Java client
 * provided by RabbitMQ.
 * <p>
 * Download client library (amqp-client-4.0.2.jar) and its dependencies (SLF4J
 * API and SLF4J Simple) and copy them into lib directory.
 * <p>
 * Jargon terms:
 * RabbitMQ is a message broker, i.e., a server that accepts and forwards messages.
 * Producer is a program that sends messages (Producing means sending).
 * Queue is a post box which lives inside a RabbitMQ broker (large message buffer).
 * Consumer is a program that waits to receive messages (Consuming means receiving).
 * The server, client and broker do not have to reside on the same host
 *
 * @author rui
 */
public class ProducerM {
    /**
     * recebe por argumento o ID do worker (que vai ser nome da fila) e ficheiro JSSP para enviar ao worker
     * @param argv
     * @throws IOException
     */
    public static void main(String[] argv) throws IOException {

        if(argv.length < 2) {
            System.err.println("Usage: ReceiveLogsTopic [HOST] [PORT] [EXCHANGE]");
            System.exit(0);
        }

        String host = argv[0];
        int port = Integer.parseInt(argv[1]);
        String exchangeName = "xd_exchange";

        try (Connection connection=RabbitUtils.newConnection2Server(host, port, "guest", "guest");
             Channel channel = RabbitUtils.createChannel2Server(connection)) {
            String queueName = "xd";
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT);
            String message = "boas Primo";
            String routingKey = "";
            System.out.println(" [x] Sent '" + message + "'");
            for(int i= 0; i < 100; i++){
                channel.basicPublish(exchangeName,routingKey,null,"hello".getBytes(StandardCharsets.UTF_8));
            }

            while(true){
                channel.basicPublish(exchangeName,routingKey,null,"hello".getBytes(StandardCharsets.UTF_8));
            }




        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

}
