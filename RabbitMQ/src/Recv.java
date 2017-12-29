import com.rabbitmq.client.*;

import java.io.IOException;
//import com.rabbitmq.client.MessageProperties;

public class Recv {

  private final static String QUEUE_NAME = "hello";
  private final static String QUEUE_COUNT = "count";
  private final static String QUEUE_WORDS = "word";
  
  public static void main(String[] argv) throws Exception {
	  
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    System.out.println(factory.getHost());
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
//
    boolean durability = true;
    channel.queueDeclare("anotherOne", durability, false ,false, null);
//    channel.queueDeclare(QUEUE_NAME, durability, false, false, null);
//    channel.queueDeclare(QUEUE_WORDS, durability, false, false, null);
    
    
    
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
    channel.basicQos(1);

    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
          throws IOException {
        
    	String message = new String(body, "UTF-8");
        System.out.println(" [x] Received '" + message + "'");
        
        try {
        	doWork(message);
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
        	System.out.println(" [x] Done");
        	channel.basicAck(envelope.getDeliveryTag(), false);
        }
      }
    };
    boolean autoAck=false;
    
    channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    channel.basicConsume(QUEUE_COUNT, autoAck, consumer);
    channel.basicConsume(QUEUE_WORDS, autoAck, consumer);
  }
  
  private static void doWork(String task) throws InterruptedException {
	    for (char ch: task.toCharArray()) {
	        if (ch == '.') Thread.sleep(1000);
	    }
	}  
}