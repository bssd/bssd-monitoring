package uk.co.bssd.monitoring.jmx;

import org.junit.Ignore;

import uk.co.bssd.jmx.LocalManagementBeanServer;
import uk.co.bssd.jmx.SimpleReportingService;

@Ignore("Don't run this as a test, simple jmx server")
public class SimpleJmxServer {

	private static final String MBEAN_NAME = "uk.co.bssd:type=simpleMBean";
	
	public static void main(String[] args) {
		LocalManagementBeanServer beanServer = new LocalManagementBeanServer();
		beanServer.registerManagementBean(MBEAN_NAME, new SimpleReportingService());

		System.out.println("Management bean registered, SimpleJmxServer started...");
		while (true) {
			try {
				Thread.sleep(100000);
			} catch (InterruptedException e) {
			}
		}
	}
}