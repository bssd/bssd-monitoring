package uk.co.bssd.monitoring;

import uk.co.bssd.jmx.ManagementAttribute;
import uk.co.bssd.jmx.ManagementBean;
import uk.co.bssd.jmx.ManagementBeanServer;

public class JmxAttributeAdapter<T> implements MonitoredValueAdapter<T> {

	private final ManagementAttribute<T> attribute;
	
	public JmxAttributeAdapter(ManagementBeanServer mbeanServer, String objectName, String attributeName, Class<T> attributeClazz) {
		ManagementBean mbean = mbeanServer.findManagementBean(objectName);
		this.attribute = mbean.findAttribute(attributeName, attributeClazz);
	}

	@Override
	public T currentValue() {
		return this.attribute.value();
	}
}