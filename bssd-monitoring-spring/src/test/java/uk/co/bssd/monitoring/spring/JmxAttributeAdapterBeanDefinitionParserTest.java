package uk.co.bssd.monitoring.spring;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uk.co.bssd.monitoring.JmxAttributeAdapter;

public class JmxAttributeAdapterBeanDefinitionParserTest {

	private ApplicationContext context;
	
	@Before
	public void before() {
		this.context = new ClassPathXmlApplicationContext("classpath:jmx-attribute-adapter-context.xml");
	}
	
	@Test
	public void testJmxAttributeAdapterBeanIsParsedCorrectly() {
		assertThat(this.context.getBean(JmxAttributeAdapter.class), is(notNullValue()));
	}
}