package uk.co.bssd.monitoring;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.co.bssd.jmx.ManagementBeanServer;

public class JmxAttributeAdapterTest {

	public interface MonitoredAttributeMBean {
		int getMonitoredValue();
	}
	
	public static class MonitoredAttribute implements MonitoredAttributeMBean {
		private int value;
		
		public void setMonitoredValue(int newValue) {
			this.value = newValue;
		}
		
		@Override
		public int getMonitoredValue() {
			return this.value;
		}
	}
	
	private static final String MBEAN_NAME = "uk.co.bssd:type=monitoredAttribute";
	private static final String ATTRIBUTE_NAME = "MonitoredValue";

	private MonitoredAttribute monitoredAttribute;
	private ManagementBeanServer managementBeanServer;

	private MonitoredValueAdapter<Integer> adapter;
	
	@Before
	public void before() {
		this.monitoredAttribute = new MonitoredAttribute();
		
		this.managementBeanServer = new ManagementBeanServer();
		this.managementBeanServer.registerManagementBean(MBEAN_NAME, this.monitoredAttribute);
		
		this.adapter = new JmxAttributeAdapter<Integer>(MBEAN_NAME, ATTRIBUTE_NAME, Integer.class);
	}
	
	@Test
	public void testAdapterExtractsValueFromJmxCorrectly() {
		int value = 49984;
		this.monitoredAttribute.setMonitoredValue(value);
		assertThat(this.adapter.currentValue(), is(value));
	}
}