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
package uk.co.bssd.monitoring;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileLoggerTest {

	private static final String LOGGER_ID_1 = UUID.randomUUID().toString();
	private static final String LOGGER_ID_2 = UUID.randomUUID().toString();

	private static final Integer REPORTED_VALUE_1 = Integer.valueOf(5);
	private static final Integer REPORTED_VALUE_2 = Integer.valueOf(8);

	private static final String FILENAME_1 = "target/test-classes/test-file1.csv";
	private static final String FILENAME_2 = "target/test-classes/test-file2.csv";

	private static ValueReporter<Integer> logger1;
	private static ValueReporter<Integer> logger2;

	@BeforeClass
	public static void beforeClass() {
		FileUtils.deleteQuietly(file1());
		FileUtils.deleteQuietly(file2());
		assertThat("File should not exist", file1().exists(), is(false));

		logger1 = new CsvFileLogger<Integer>(LOGGER_ID_1, FILENAME_1);
		logger1.report(REPORTED_VALUE_1);

		logger2 = new CsvFileLogger<Integer>(LOGGER_ID_2, FILENAME_2);
	}

	@Test
	public void testToString() {
		assertThat(logger1.toString(), is("CsvFileLogger with id ["
				+ LOGGER_ID_1 + "] logging to file [" + FILENAME_1 + "]"));
	}

	@Test
	public void testFileIsCreatedByLogger() {
		assertThat("Logger should create file", file1().exists(), is(true));
	}

	@Test
	public void testFileIsNotEmpty() {
		assertThat(fileContents(file1()), is(not(empty())));
	}

	@Test
	public void testFileHasOneEntry() {
		assertThat(fileContents(file1()).size(), is(1));
	}

	@Test
	public void testFileEntryIsCommaSeperated() {
		assertThat(firstLine(file1()), containsString(","));
	}

	@Test
	public void testFileEntryOnlyHasTwoCommaSeperatedValues() {
		assertThat(firstLine(file1()).split(","), is(arrayWithSize(2)));
	}

	@Test
	public void testFirstValueIsTimestamp() {
		DateTimeFormatter timestampFormatter = DateTimeFormat
				.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
		DateTime timestamp = timestampFormatter
				.parseDateTime(timestamp(firstLine(file1())));
		assertThat(timestamp, is(notNullValue()));
	}

	@Test
	public void testSecondValueIsValueThatWasLogged() {
		assertThat(value(firstLine(file1())), is(REPORTED_VALUE_1.toString()));
	}

	@Test
	public void testLoggingToSecondReporterWritesToDifferentFile() {
		logger2.report(REPORTED_VALUE_2);
		assertThat(fileContents(file2()), is(not(empty())));
	}

	private String timestamp(String line) {
		return splitLineAndReturnValue(line, 0);
	}

	private String value(String line) {
		return splitLineAndReturnValue(line, 1);
	}

	private String splitLineAndReturnValue(String line, int index) {
		return line.split(",")[index];
	}

	private String firstLine(File file) {
		return fileContents(file).iterator().next();
	}

	private static File file1() {
		return file(FILENAME_1);
	}

	private static File file2() {
		return file(FILENAME_2);
	}

	private List<String> fileContents(File file) {
		try {
			return FileUtils.readLines(file);
		} catch (IOException e) {
			throw new IllegalStateException("Unable to read file", e);
		}
	}

	private static File file(String filename) {
		return new File(filename);
	}
}