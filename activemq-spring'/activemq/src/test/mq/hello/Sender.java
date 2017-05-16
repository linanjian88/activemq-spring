package test.mq.hello;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession.DeliveryListener;
import org.apache.activemq.command.ActiveMQTextMessage;

public class Sender {

	public static void main(String[] args) throws Exception {
		// 建立连接
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
				ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://localhost:61616");
		// 开启一个connection
		Connection connection = connectionFactory.createConnection();
		// 默认关闭要开启
		connection.start();
		// 创建session,是否启动事务,签收模式
		// Session session = connection.createSession(Boolean.TRUE,
		// Session.AUTO_ACKNOWLEDGE);
		// 使用代码的方式签收
		Session session = connection.createSession(Boolean.TRUE, Session.CLIENT_ACKNOWLEDGE);

		// session创建Destination目的对象(队列Queue)
		// 创建而定目的地叫什么
		// 生产者和消费者指定一样的名字才能拿到东西
		Destination destination = session.createQueue("queue1");
		// 生产者
		MessageProducer messageProducer = session.createProducer(destination);
		// 设置持久化和非持久化的特性,持久化可以放在数据库之类的
		// 此处为非持久化

		messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		// 常见信息内容

		for (int a = 0; a < 5; a++) {
			ActiveMQTextMessage textMessage = (ActiveMQTextMessage) session.createTextMessage("现在的a=" + a);
			messageProducer.send(textMessage);
		}
		session.commit();
		// close以后会自动关闭下级节点
		if (connection != null)
			connection.close();
	}
}
