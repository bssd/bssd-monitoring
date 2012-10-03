package uk.co.bssd.monitoring;

public class FixedValueAdapter<T> implements MonitoredValueAdapter<T>{

	private final T fixedValue;
	
	public FixedValueAdapter(T value) {
		this.fixedValue = value;
	}
	
	@Override
	public T currentValue() {
		return this.fixedValue;
	}
}