package rabbit;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.IllegalFormatException;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

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
	private static final String INPUT_QUEUE = "test";
	private static final String OUTPUT_QUEUE = "test";
	public static final String CHARSET = "UTF-8";

	private static MongoWorker mw = new MongoWorker();

	private static final Logger logger = Logger.getLogger(RabbitRecieverMain.class);

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
		
		System.out.println("process start");
		byte[] body = delivery.getBody();
		String msg = new String(body, CHARSET);
		String[] args = msg.split(":");
		if (args.length != 4) {
			System.out.println("throw");
			throw new IllegalArgumentException(
					"cant parse " + msg + " : expected <user_name>:<user_token>:<add|delete>:<note>");
		}
		
		String user = args[0];
		String token = args[1];
		String command = args[2];
		String content = args[3];
		
		String response = null;
		
		if (isValidToken(token)) {
			switch (command) {
			case "add":
				System.out.println("ading");
				mw.add(user, content);
				break;
			case "delete":
				System.out.println("deleting");
				long num_deleted = mw.delete(user, content);
				response = "deleted " + num_deleted + " entries";
				break;
			case "default":
				response = "no such command: " + command;
				logger.error(response);
				break;
			}
		} else {
			System.out.println("not valid token: " + token);
		}
		System.out.println("process end");
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
