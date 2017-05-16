package provider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.provider.controller.MQProducer;
import com.provider.model.Mail;

@ContextConfiguration(locations = { "classpath:spring-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestProducer {

	@Autowired
	private MQProducer mqProducer;

	@Test
	public void send() {
		Mail mail = new Mail();
		mail.setTo("550556232@qq.com");
		mail.setSubject("小猪儿");
		mail.setContent("小伙子，吃饭");

		this.mqProducer.sendMessage(mail);
		System.out.println("发送成功.....");

	}

}
