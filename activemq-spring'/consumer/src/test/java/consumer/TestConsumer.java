package consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestConsumer {

	// 因为此处 内部有监听器，所以直接加载配置文件，自动执行监听器
	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					new String[] { "spring-context.xml" });
			context.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
