package uk.co.bssd.monitoring;

public class DoNothingValueReporter<T> implements ValueReporter<T> {

	@Override
	public void report(T value) {
		// do nothing
	}
}