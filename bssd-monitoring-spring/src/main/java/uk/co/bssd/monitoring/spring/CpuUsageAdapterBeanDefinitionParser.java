package uk.co.bssd.monitoring.spring;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

import uk.co.bssd.monitoring.CpuUsageAdapter;

public class CpuUsageAdapterBeanDefinitionParser extends
		AbstractSingleBeanDefinitionParser {

	@Override
	protected Class<?> getBeanClass(Element element) {
		return CpuUsageAdapter.class;
	}

	@Override
	protected void doParse(Element element, BeanDefinitionBuilder bean) {
		String managementBeanServerName = element.getAttribute("managementBeanServerRef");
		bean.addConstructorArgReference(managementBeanServerName);
	}
}