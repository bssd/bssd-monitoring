package uk.co.bssd.monitoring.spring;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

import uk.co.bssd.monitoring.JmxAttributeAdapter;

public class JmxAttributeAdapterBeanDefinitionParser extends
		AbstractSingleBeanDefinitionParser {

	@Override
	protected Class<?> getBeanClass(Element element) {
		return JmxAttributeAdapter.class;
	}

	@Override
	protected void doParse(Element element, BeanDefinitionBuilder bean) {
		bean.addConstructorArgReference(element.getAttribute("managementBeanServerRef"));
		bean.addConstructorArgValue(element.getAttribute("objectName"));
		bean.addConstructorArgValue(element.getAttribute("attributeName"));

		String className = element.getAttribute("type");
		try {
			Class<?> clazz = Class.forName(className);
			bean.addConstructorArgValue(clazz);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Unable to find class '" + className + "' for attribute", e);
		}
	}
}