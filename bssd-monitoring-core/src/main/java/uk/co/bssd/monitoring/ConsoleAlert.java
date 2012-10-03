package uk.co.bssd.monitoring;

public class ConsoleAlert implements Alert {

	private static final String ALERT_TEMPLATE = "Current value [%s] has broken condition [%s] for threshold [%s]";
	
	@Override
	public <T> void alert(AlertEvent<T> event) {
		String alert = String.format(ALERT_TEMPLATE, event.value(), event.condition(), event.threshold());
		System.out.println(alert);
	}
}