package uk.co.bssd.monitoring;

public interface Alert {

	<T> void alert(AlertEvent<T> event);
}