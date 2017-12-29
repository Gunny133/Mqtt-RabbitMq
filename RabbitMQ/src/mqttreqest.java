
import org.eclipse.paho.client.mqttv3.*;
//import json;
//import de.dcsquare.paho.client.util.Utils;
//import java.util.*;

public class mqttreqest {
	
	public MqttClient publish;
	public MqttClient subscribe;
	public MqttClient subscribe2;
	public final String brokerUrl = "tcp://localhost:1883";
	
	public mqttreqest() {
		//String publishID = Utils.getMacAddress() + "pub";
		String publishID = getClass().getSimpleName() + ((int) (10000*Math.random()));
		String subscibeID = getClass().getSimpleName() + ((int) (10000*Math.random()));
		String subscibeID2 = getClass().getSimpleName() + ((int) (10000*Math.random()));
		
		try {
			publish = new MqttClient(brokerUrl, publishID);		
			subscribe = new MqttClient(brokerUrl, subscibeID);
			subscribe2 = new MqttClient(brokerUrl, subscibeID2);
			publish.connect();
			subscribe.connect();
			subscribe2.connect();
			
		}catch (MqttException e){
			e.printStackTrace();
			System.exit(1);
			
		}
	}
	
	public void createTopic(String topic1, String topic2) throws MqttException {
		MqttTopic top1 = new MqttTopic(topic1, null);
		MqttTopic top2 = new MqttTopic(topic2, null);
		MqttMessage bst = new MqttMessage("bst".getBytes());
		MqttMessage xyz = new MqttMessage("xyz".getBytes());

		
		publish.publish(top1.getName(), bst);
		publish.publish(top2.getName(), xyz);
		
		subscribe.subscribe(top1.getName());
		subscribe2.subscribe(top2.getName());
		
		
	}
	
//    String clientId2 = clientId + "-2";
  //  MqttClient client2 = new MqttClient(brokerUrl, clientId2);
	
	public static void main (String[] args) throws MqttException {
		mqttreqest mq = new mqttreqest();
		mq.createTopic("testTopic", "testTopic2");
	}
	
}
