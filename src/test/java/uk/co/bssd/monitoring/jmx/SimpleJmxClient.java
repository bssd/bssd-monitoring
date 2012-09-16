package uk.co.bssd.monitoring.jmx;

import org.junit.Ignore;

import uk.co.bssd.jmx.ManagementAttribute;
import uk.co.bssd.jmx.ManagementBean;
import uk.co.bssd.jmx.ManagementBeanServer;
import uk.co.bssd.jmx.RemoteManagementBeanServerFactory;

@Ignore("Don't run this as a test, simple jmx client")
public class SimpleJmxClient {

	public static void main(String[] args) {
		ManagementBeanServer beanServer = RemoteManagementBeanServerFactory.managementBeanServer("localhost", 1234);
		ManagementBean managementBean = beanServer.findManagementBean("uk.co.bssd:type=simpleMBean");
		ManagementAttribute<Integer> managementAttribute = managementBean.findAttribute("IntValue", Integer.class);
		System.out.println(managementAttribute.value());
	}
}