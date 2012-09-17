package uk.co.bssd.monitoring;

public interface ValueReporter<T> {

	void report(T value);
}