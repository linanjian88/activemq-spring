package test.mq.pb;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

public class publish {

	private ConnectionFactory factory;
	private static Connection connection;
	private static Session session;
	private MessageProducer Producer;

	publish() {
		ConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
				ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://localhost:61616");
		// 开启一个connection
		try {
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			Producer = session.createProducer(null);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendMessage() throws JMSException {
		Destination destination = session.createTopic("topic1");
		TextMessage textMessage = session.createTextMessage("我是内容");
		Producer.send(destination, textMessage);
	}

	public static void main(String[] args) throws Exception {

		publish publish = new publish();
		publish.sendMessage();

	}

}