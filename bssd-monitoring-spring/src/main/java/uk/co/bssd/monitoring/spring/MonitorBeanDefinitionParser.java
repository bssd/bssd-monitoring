package uk.co.bssd.monitoring.spring;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

import uk.co.bssd.monitoring.Alert;
import uk.co.bssd.monitoring.ConsoleAlert;
import uk.co.bssd.monitoring.LessThan;
import uk.co.bssd.monitoring.Monitor;
import uk.co.bssd.monitoring.MonitoredValueAdapter;
import uk.co.bssd.monitoring.ThresholdBreaksImmediately;
import uk.co.bssd.monitoring.loader.MonitorDefinition;

public class MonitorBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

	@Override
	protected Class<?> getBeanClass(Element element) {
		return MonitorDefinition.class;
	}
	
	@Override
	protected void doParse(Element element, BeanDefinitionBuilder bean) {
		MonitoredValueAdapter<Long> valueAdapter = new MonitoredValueAdapter<Long>() {
			@Override
			public Long currentValue() {
				return Long.valueOf(1);
			}
		};
		Monitor<Long> monitor = new Monitor<Long>(valueAdapter, new Alert<Long>(new LessThan<Long>(0L), new ThresholdBreaksImmediately(), new ConsoleAlert()));
		long intervalMs = Long.parseLong(element.getAttribute("monitorIntervalMs"));
		bean.addConstructorArgValue(monitor);
		bean.addConstructorArgValue(intervalMs);
	}
}