package uk.co.bssd.monitoring;

import java.io.IOException;
import java.util.UUID;

import org.apache.log4j.FileAppender;
import org.apache.log4j.PatternLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvFileLogger<T> implements ValueReporter<T> {

	private static final String TIMESTAMP_PATTERN_EXCEL = "yyyy-MM-dd HH:mm:ss.SSS";
	
	private static final String APPENDER_NAME = "file";

	private final String loggerId;
	private final Logger logger;

	public CsvFileLogger(String filename) {
		this(UUID.randomUUID().toString(), filename);
	}
	
	public CsvFileLogger(String loggerId, String filename) {
		this.loggerId = loggerId;
		this.logger = LoggerFactory.getLogger(this.loggerId);

		if (log4jLogger().getAppender(APPENDER_NAME) == null) {
			PatternLayout layout = new PatternLayout("%d{"
					+ TIMESTAMP_PATTERN_EXCEL + "},%m%n");

			try {
				FileAppender fileAppender = new FileAppender(layout, filename);
				fileAppender.setName(APPENDER_NAME);
				log4jLogger().addAppender(fileAppender);
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
	}

	private org.apache.log4j.Logger log4jLogger() {
		return org.apache.log4j.Logger.getLogger(this.loggerId);
	}

	public void report(T value) {
		this.logger.info("{}", value);
	}
}