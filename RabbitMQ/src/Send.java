import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

import java.lang.reflect.Array;
import java.util.concurrent.TimeoutException;

import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;


public class Send {
	
	private final static String QUEUE_NAME = "hello";
	private final static String QUEUE_COUNT = "count";
	private final static String QUEUE_WORDS = "word";
	private String[] wordywords = new String[4];
	
	
	public static void main (String[] argv) throws java.io.IOException, TimeoutException {
		
		
		String wordywords[] = {"BST.", "CAMERA..", "RADAR...", "JAMMER.", "COFFEE"};
		
		for(int i = 0; i<5; i++) {
			
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			//channel.exchangeDeclare("logs", "fanout");
			//String queueName = channel.queueDeclare().getQueue();
			
//			final boolean durability = true;
//			channel.queueDeclare(QUEUE_WORDS, durability, false ,false, null);
//			channel.queueDeclare(QUEUE_COUNT, durability, false ,false, null);
//			channel.queueDeclare(QUEUE_NAME, durability, false, false, null);
			
			String period = "";
			for(int j = 0; j<=i; j++) {
				period += ".";
			}
			String count = "count is "+ i + period;
			String message = getMessage(argv);
			String wordy = wordywords[i];
			
			channel.basicPublish("", QUEUE_WORDS, MessageProperties.PERSISTENT_TEXT_PLAIN, wordy.getBytes());
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			channel.basicPublish("", QUEUE_COUNT, null, count.getBytes());
			
			System.out.println(" [x] Sent '" + i + "  " + message + "'");
			System.out.println(" [x] Sent '" + i + "  " + count + "'");
			System.out.println(" [x] Sent '" + i + "  " + wordy + "'");
			
			//channel.close();
			//connection.close();
		}
		
		
	}
	
	private static String getMessage(String[] strings){
	    if (strings.length < 1)
	        return "Hello World!";
	    return joinStrings(strings, " ");
	}

	private static String joinStrings(String[] strings, String delimiter) {
	    int length = strings.length;
	    if (length == 0) return "";
	    StringBuilder words = new StringBuilder(strings[0]);
	    for (int i = 1; i < length; i++) {
	        words.append(delimiter).append(strings[i]);
	    }
	    return words.toString();
	}

}
