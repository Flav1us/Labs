package test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import rabbit.RabbitRecieverMain;

public class OutputQueueDummy {
	private static final String RABBIT_HOST_IP = "localhost";
	private static final String INPUT_QUEUE = "notes_responses";
	private static final String ENCODING = "UTF-8";
	
	
	
	private static final Logger logger = Logger.getLogger(InputQueueDummy.class);

	
	public static void main(String[] args) throws IOException, TimeoutException {
		logger.info("main class started");
		//byte[] test_msg = "delete/sep/2aaa5223727c4a2f".getBytes(RabbitRecieverMain.CHARSET);
		
		Connection connection = getConnection(); 
		
			Channel input_channel = connection.createChannel();

			input_channel.queueDeclare(INPUT_QUEUE, true, false, false, null);
			System.out.println("r: recieving messages.");
			
			DeliverCallback deliverCallback = (consumerTag, delivery) -> {
				String message = new String(delivery.getBody(), "UTF-8");
				logger.info("Received message '" + message + "'");
				try {
					System.out.println(new String(delivery.getBody(), ENCODING));
				} catch (Exception e) {
					logger.error("exception recieving message", e);
				} finally {
					System.out.println("done test");
				}
			};
			input_channel.basicConsume(INPUT_QUEUE, true, deliverCallback, consumerTag -> {
			});
			
		
	}
	
	private static Connection getConnection() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(RABBIT_HOST_IP);
		Connection connection = factory.newConnection();
		return connection;
	}
}
