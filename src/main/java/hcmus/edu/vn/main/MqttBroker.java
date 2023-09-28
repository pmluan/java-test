package hcmus.edu.vn.main;

import hcmus.edu.vn.utils.Jackson;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class MqttBroker {

    private static final Random rnd = new Random();

    public static void main(String[] args) throws MqttException, InterruptedException {
        String publisherId = UUID.randomUUID().toString();
        IMqttClient client = new MqttClient("tcp://192.168.1.44:1883",publisherId);

        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        client.connect(options);
    
        if ( !client.isConnected()) {
            LOGGER.info("[I31] Client not connected.");
            return;
        }

        MqttTopic mqttTopic = client.getTopic("test");
        LOGGER.info("topic: {}", Jackson.toJsonString(mqttTopic));

        CountDownLatch receivedSignal = new CountDownLatch(1);
        client.subscribe("test", (topic, msg) -> {
            byte[] payload = msg.getPayload();
            LOGGER.info("[I46] Message received: topic={}, payload={}", topic, new String(payload));
            receivedSignal.countDown();
        });



       // receivedSignal.await(10, TimeUnit.SECONDS);
        LOGGER.info("[I56] Success !");

//        MqttMessage msg = readEngineTemp();
//        msg.setQos(0);
//        msg.setRetained(true);
//        client.publish("test",msg);
    }

    private static MqttMessage readEngineTemp() {
        double temp =  80 + rnd.nextDouble() * 20.0;
        byte[] payload = String.format("T:%04.2f",temp).getBytes();
        MqttMessage msg = new MqttMessage(payload);
        return msg;
    }
}
