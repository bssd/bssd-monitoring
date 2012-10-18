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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import uk.co.bssd.monitoring.CsvFileLogger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:csv-file-reporter-context.xml")
public class CsvFileLoggerBeanDefinitionParserTest {

	@Autowired
	@Qualifier("csvFileReporter1")
	private CsvFileLogger<Long> csvFileLogger1;
	
	@Autowired
	@Qualifier("csvFileReporter2")
	private CsvFileLogger<Long> csvFileLogger2;
	
	@Test
	public void testCsvFileLoggerCreatedWithIdAloneHasCorrectIdAndFileName() {
		assertThat(this.csvFileLogger1.id(), is("csvFileReporter1"));
		assertThat(this.csvFileLogger1.filename(), is("csvFileReporter1.csv"));
	}
	
	@Test
	public void testCsvFileLoggerCreatedWithIdAndFileNameHasCorrectIdAndFileName() {
		assertThat(this.csvFileLogger2.id(), is("csvFileReporter2"));
		assertThat(this.csvFileLogger2.filename(), is("target/differentFileName.csv"));
	}
}