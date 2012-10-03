package uk.co.bssd.monitoring;

public class FixedValueWithPaddedConstructorAdapter<T> extends FixedValueAdapter<T>{

	public FixedValueWithPaddedConstructorAdapter(String leadingPaddingParam, T value, String trailingPaddingParam) {
		super(value);
	}
}