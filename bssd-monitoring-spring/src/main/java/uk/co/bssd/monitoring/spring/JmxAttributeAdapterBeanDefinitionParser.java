/*
 * Copyright 2002-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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