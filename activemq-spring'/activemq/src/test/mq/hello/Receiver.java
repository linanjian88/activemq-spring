package test.mq.hello;

import java.text.DecimalFormat;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

public class Receiver {

	public static void main(String[] args) throws Exception {
		// 建立连接
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
				ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://localhost:61616");
		// 开启一个connection
		Connection connection = connectionFactory.createConnection();
		// 默认关闭要开启
		connection.start();
		// 创建session,是否启动事务,签收模式(手动签收)
		// 必须调用手动签收的方法才会进行消息确认
		// 一般用手动的
		Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);

		// session创建Destination目的对象(队列Queue)
		// 创建而定目的地叫什么
		// 生产者和消费者指定一样的名字才能拿到东西
		Destination destination = session.createQueue("queue1");
		// 生产者
		MessageConsumer messageConsumer = session.createConsumer(destination);
		
		
		while (true) {
			TextMessage msg = (TextMessage) messageConsumer.receive();
			// 手工签收，另起一个线程（tcp）告知已经接收到消息
			msg.acknowledge();
			if (msg == null)
				break;
			System.out.println("Message=" + msg.getText());
		}
		// close以后会自动关闭下级节点
		if (connection != null)
			connection.close();

	}
	
}
