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

import java.util.UUID;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import uk.co.bssd.monitoring.Monitor;
import uk.co.bssd.monitoring.loader.MonitorDefinition;

public class MonitorBeanDefinitionParser extends
		AbstractSingleBeanDefinitionParser {

	@Override
	protected Class<?> getBeanClass(Element element) {
		return MonitorDefinition.class;
	}

	@Override
	protected void doParse(Element element, ParserContext parserContext,
			BeanDefinitionBuilder bean) {

		Element adapterElement = getChildElement(element.getChildNodes(),
				"adapter");
		String adapterRef = adapterElement.getAttribute("ref");

		Element reporterElement = getChildElement(element.getChildNodes(),
				"reporter");
		String reporterRef = reporterElement.getAttribute("ref");

		BeanDefinitionBuilder monitorBeanDefinitionBuilder = BeanDefinitionBuilder
				.genericBeanDefinition(Monitor.class);
		monitorBeanDefinitionBuilder.addConstructorArgReference(adapterRef);
		monitorBeanDefinitionBuilder.addConstructorArgReference(reporterRef);

		String monitorBeanName = UUID.randomUUID().toString();
		BeanDefinitionHolder monitorBeanDefinitionHolder = new BeanDefinitionHolder(
				monitorBeanDefinitionBuilder.getBeanDefinition(),
				monitorBeanName);
		
		registerBeanDefinition(monitorBeanDefinitionHolder,
				parserContext.getRegistry());
		
		BeanComponentDefinition monitorBeanComponentDefinition = new BeanComponentDefinition(
				monitorBeanDefinitionHolder);
		parserContext.registerComponent(monitorBeanComponentDefinition);

		long intervalMs = Long.parseLong(element
				.getAttribute("monitorIntervalMs"));
		bean.addConstructorArgReference(monitorBeanName);
		bean.addConstructorArgValue(intervalMs);
	}

	private Element getChildElement(NodeList childNodes, String elementName) {
		for (int i = 0, max = childNodes.getLength(); i < max; i++) {
			Node childNode = childNodes.item(i);

			if (childNode instanceof Element) {
				Element childElement = (Element) childNode;
				if (childElement.getLocalName().equals(elementName)) {
					return childElement;
				}
			}
		}
		return null;
	}
}