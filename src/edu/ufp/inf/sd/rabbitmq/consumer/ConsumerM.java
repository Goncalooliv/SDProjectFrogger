package edu.ufp.inf.sd.rabbitmq.consumer;

/**
 * Consumer will keep running to listen for messages from queue and print them out.
 * <p>
 * DefaultConsumer is a class implementing the Consumer interface, used to buffer
 * the messages pushed to us by the server.
 * <p>
 * Compile with RabbitMQ java client on the classpath:
 * javac -cp amqp-client-4.0.2.jar RPCServer.java RPCClient.java
 * <p>
 * Run with need rabbitmq-client.jar and its dependencies on the classpath.
 * java -cp .:amqp-client-4.0.2.jar:slf4j-api-1.7.21.jar:slf4j-simple-1.7.22.jar Recv
 * java -cp .:amqp-client-4.0.2.jar:slf4j-api-1.7.21.jar:slf4j-simple-1.7.22.jar Producer
 * <p>
 * OR
 * export CP=.:amqp-client-4.0.2.jar:slf4j-api-1.7.21.jar:slf4j-simple-1.7.22.jar
 * java -cp $CP Producer
 * java -cp %CP% Producer
 * <p>
 * The client will print the message it gets from the publisher via RabbitMQ.
 * The client will keep running, waiting for messages (Use Ctrl-C to stop it).
 * Try running the publisher from another terminal.
 * <p>
 * Check RabbitMQ Broker runtime info (credentials: guest/guest4rabbitmq):
 * http://localhost:15672/
 *
 * @author rui
 */

import com.rabbitmq.client.*;
import util.RabbitUtils;

import java.nio.charset.StandardCharsets;


public class ConsumerM {
    /**
     * recebe pela fila o ficheiro JSSP para correr o algoritmo Genetic Alg.
     * @param argv
     * @throws Exception
     */
    public static void main(String[] argv) throws Exception {
        if (argv.length < 2) {
            System.err.println("Usage: ReceiveLogsTopic [HOST] [PORT] [EXCHANGE]");
            System.exit(1);
        }
        String host = argv[0];
        int port = Integer.parseInt(argv[1]);
        String exchangeName = "xd_exchange";
        String queue = "FODEROHBORRO";
        try {

            Connection connection = RabbitUtils.newConnection2Server(host, port, "guest", "guest");
            Channel channel = RabbitUtils.createChannel2Server(connection);

            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT);
            String queueName = channel.queueDeclare().getQueue();
            System.out.println(queueName);

            System.out.println("main(): argv.length=" + argv.length);

            //Bind to each routing key (received from args[3] upward)
            String routingKey = "";
            channel.queueBind(queueName, exchangeName, routingKey);

            System.out.println(" [*] Waiting for messages... to exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(message);
                channel.basicPublish("", queue, null, message.getBytes(StandardCharsets.UTF_8));
            };

            CancelCallback cancelCallback = (consumerTag) -> {
                System.out.println(" [x] Consumer Tag [" + consumerTag + "] - Cancel Callback invoked!");
            };

            channel.basicConsume(queueName, true, deliverCallback, cancelCallback);

            DeliverCallback deliverCallbackResults = (consumerTagResults, deliveryResults) -> {
                System.out.println("arroz");
            };
            CancelCallback cancelCallbackResults=(consumerTagResults) -> {
                System.out.println(" [x] Consumer Tag [" + consumerTagResults + "] - Cancel Callback invoked!");
            };
            channel.queueDeclare(queue +  "_results", false, false, false, null);
            channel.basicConsume(queue + "_results",true,deliverCallbackResults,cancelCallbackResults);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


