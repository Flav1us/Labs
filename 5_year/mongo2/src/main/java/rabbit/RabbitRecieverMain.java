package rabbit;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.IllegalFormatException;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.bson.Document;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import rabbit.MongoWorker;
import test.LogTest;

public class RabbitRecieverMain {

	private static final String RABBIT_USER = "guest";
	private static final String RABBIT_PASSWORD = "guest";
	private static final String RABBIT_HOST_IP = "localhost";
	private static final String INPUT_QUEUE = "notes_requests";
	private static final String OUTPUT_QUEUE = "notes_responses";
	public static final String CHARSET = "UTF-8";

	private static MongoWorker mw = new MongoWorker();

	private static final Logger logger = Logger.getLogger(RabbitRecieverMain.class);
	public static final String SEP = "/sep/";

	public static void main(String[] argv) throws Exception {
		logger.info("main class started");

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(RABBIT_HOST_IP);
		// factory.setUsername(RABBIT_USER);
		// factory.setPassword(RABBIT_PASSWORD);
		Connection connection = factory.newConnection();
		Channel input_channel = connection.createChannel();
		Channel output_channel = connection.createChannel();

		input_channel.queueDeclare(INPUT_QUEUE, false, false, false, null);
		output_channel.queueDeclare(OUTPUT_QUEUE, false, false, false, null);
		
		System.out.println("r: Waiting for messages.");
		
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			logger.info("Received message '" + message + "'");
			try {
				String response = processDelivery(delivery);
				if (response != null) {
					output_channel.basicPublish("", OUTPUT_QUEUE, null, response.getBytes(CHARSET));
					logger.info("response " + response + " sent");
				}
			} catch (Exception e) {
				logger.error("exception during processing delivery", e);
			} finally {
				System.out.println("done");
			}
		};
		input_channel.basicConsume(INPUT_QUEUE, true, deliverCallback, consumerTag -> {
		});

	}

	private static String processDelivery(Delivery delivery) throws UnsupportedEncodingException {
		//{command}/sep/{username}/{note_title}/sep/{note_body}
		
		byte[] body = delivery.getBody();
		String msg = new String(body, CHARSET);
		System.out.println("process start " + msg);
		String[] args = msg.split(SEP);
		
		String command = args[0];
		 
		//may be null
		String response = null;
		
		//if (isValidToken(token)) {
			switch (command) {
			case "create":
				System.out.println("creating");
				mw.create(args[1], args[2], args[3]);
				response = "true";
				break;
			case "delete":
				System.out.println("deleting");
				if(mw.delete(args[1])) {	
				response = "true";
				} else {
					response = "false";
				}
				break;
			case "get_by_id":
				response = mw.get_by_id(args[1], args[2]);
				break;
			case "get":
				response = mw.get(args[1]);
				break;
			case "default":
				logger.error("no such command: " + command);
				break;
			}
		/*} else {
			logger.warn("not valid token: " + token);
		}*/
		logger.info("returning" + response);
		return response;
	}

	private static boolean isValidToken(String token) {
		// TODO Auto-generated method stub
		System.out.println("mock valid");
		return true;
	}

	private static Connection getConnection() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(RABBIT_HOST_IP);
		// factory.setUsername(RABBIT_USER);
		// factory.setPassword(RABBIT_PASSWORD);
		Connection connection = factory.newConnection();
		return connection;
	}
}
