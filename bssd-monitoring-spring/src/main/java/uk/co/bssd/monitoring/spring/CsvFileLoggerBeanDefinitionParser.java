package uk.co.bssd.monitoring.spring;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import uk.co.bssd.monitoring.CsvFileLogger;

public class CsvFileLoggerBeanDefinitionParser extends
		AbstractSingleBeanDefinitionParser {

	@Override
	protected Class<?> getBeanClass(Element element) {
		return CsvFileLogger.class;
	}

	@Override
	protected void doParse(Element element, BeanDefinitionBuilder bean) {
		String id = element.getAttribute("id");
		bean.addConstructorArgValue(id);
		
		String filename = element.getAttribute("filename");
		
		if (!StringUtils.hasText(filename)) {
			filename = id + ".csv";
		}
		
		bean.addConstructorArgValue(filename);
	}
}