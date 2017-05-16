package test.mq.pb;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Comsumer3 {

	private ConnectionFactory factory;
	private static Connection connection;
	private static Session session;
	private MessageProducer Producer;
	private static MessageConsumer messageConsumer;

	Comsumer3() {
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

	public void receive() throws Exception {
		Destination destination = session.createTopic("topic1");
		messageConsumer = session.createConsumer(destination);
		messageConsumer.setMessageListener(new Listerner());
	}

	public static void main(String[] args) throws JMSException {

		Comsumer3 comsumer3 = new Comsumer3();
		try {
			comsumer3.receive();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// close以后会自动关闭下级节点

	}

	class Listerner implements MessageListener {

		@Override
		public void onMessage(Message message) {
			try {
				System.out.println("订阅者03接收到消息：" + ((TextMessage) message).getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
}
