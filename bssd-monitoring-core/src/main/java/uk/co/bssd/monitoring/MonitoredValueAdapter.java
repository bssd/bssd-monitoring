package uk.co.bssd.monitoring;

public interface MonitoredValueAdapter<T> {

	T currentValue();
}