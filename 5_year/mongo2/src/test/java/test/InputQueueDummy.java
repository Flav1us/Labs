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

import rabbit.MongoWorker;
import rabbit.RabbitRecieverMain;

public class InputQueueDummy {

	private static final String RABBIT_HOST_IP = "localhost";
	private static final String OUTPUT_QUEUE = "notes_requests";
	private static final String INPUT_QUEUE = null;
	private static final String ENCODING = "UTF-8";
	
	
	
	private static final Logger logger = Logger.getLogger(InputQueueDummy.class);

	@Test
	public void run() throws UnsupportedEncodingException {
		logger.info("main class started");
		byte[] test_msg = "get/sep/ton1".getBytes(RabbitRecieverMain.CHARSET);
		//byte[] test_msg = "get/sep/ton4".getBytes(RabbitRecieverMain.CHARSET);
		
		try (Connection connection = getConnection(); 
		
			Channel output_channel = connection.createChannel();) {

			output_channel.queueDeclare(OUTPUT_QUEUE, true, false, false, null);
			System.out.println("s: sending messages.");
			
			output_channel.basicPublish("", OUTPUT_QUEUE, null, test_msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private static Connection getConnection() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(RABBIT_HOST_IP);
		Connection connection = factory.newConnection();
		return connection;
	}
}
