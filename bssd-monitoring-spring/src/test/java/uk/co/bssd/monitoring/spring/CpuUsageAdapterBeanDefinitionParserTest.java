package uk.co.bssd.monitoring.spring;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uk.co.bssd.monitoring.CpuUsageAdapter;

public class CpuUsageAdapterBeanDefinitionParserTest {

	private ApplicationContext context;
	
	@Before
	public void before() {
		this.context = new ClassPathXmlApplicationContext("classpath:cpu-usage-adapter-context.xml");
	}
	
	@Test
	public void testCpuUsageAdapterBeanIsParsedCorrectly() {
		assertThat(this.context.getBean(CpuUsageAdapter.class), is(notNullValue()));
	}
	
	@Test
	public void testCpuUsageAdapterBeanCanBeFoundById() {
		assertThat(this.context.getBean("cpuUsage"), is(notNullValue()));
	}
}