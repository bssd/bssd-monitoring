package uk.co.bssd.monitoring.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class MonitoringNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("monitor", new MonitorBeanDefinitionParser());
		registerBeanDefinitionParser("csv-file-reporter", new CsvFileLoggerBeanDefinitionParser());
	}
}