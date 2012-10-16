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