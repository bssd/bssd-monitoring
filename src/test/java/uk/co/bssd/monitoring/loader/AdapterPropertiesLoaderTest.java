package uk.co.bssd.monitoring.loader;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import uk.co.bssd.monitoring.FixedLongValueAdapter;
import uk.co.bssd.monitoring.FixedStringValueAdapter;
import uk.co.bssd.monitoring.FixedValueAdapter;
import uk.co.bssd.monitoring.FixedValueWithPaddedConstructorAdapter;
import uk.co.bssd.monitoring.MonitoredValueAdapter;

public class AdapterPropertiesLoaderTest {

	private static final String PROPERTIES_FILENAME_FIXED_STRING_ADAPTER = "/test-fixed-string-value-adapter.properties";
	private static final String PROPERTIES_FILENAME_FIXED_LONG_ADAPTER = "/test-fixed-long-value-adapter.properties";
	private static final String PROPERTIES_FILENAME_FIXED_GENERIC_ADAPTER = "/test-fixed-generic-value-adapter.properties";
	private static final String PROPERTIES_FILENAME_FIXED_GENERIC_WITH_PADDED_CONSTRUCTOR_ADAPTER = "/test-fixed-generic-value-with-padded-constructor-adapter.properties";
	private static final String PROPERTY_NAME_ADAPTER = "uk.co.bssd.monitoring.adapter.adapter1";

	private PropertiesLoader<MonitoredValueAdapter<Object>> loader;

	@Before
	public void before() throws Exception {
		this.loader = new AdapterPropertiesLoader<Object>();
	}

	@Test
	public void testCanLoadPropertyWhichIdentifiesNameAndType() {
		assertThat(this.loader.canLoad("uk.co.bssd.monitoring.adapter.aName"),
				is(true));
	}

	@Test
	public void testCanNotLoadPropertyWhichIsNotAnAdapter() {
		assertThat(
				this.loader
						.canLoad("uk.co.bssd.monitoring.threshold.immediately"),
				is(false));
	}

	@Test
	public void testCanNotLoadPropertyWhichIsAParameterToAnAdapter() {
		assertThat(
				this.loader
						.canLoad("uk.co.bssd.monitoring.adapter.aName.fixedValue"),
				is(false));
	}

	@Test
	public void testLoadFixedStringAdapterReturnsNonNullAdapter() {
		assertThat(loadStringAdapter(), is(notNullValue()));
	}

	@Test
	public void testLoadFixedStringAdapterIsCorrectType() {
		assertThat(loadStringAdapter(),
				is(instanceOf(FixedStringValueAdapter.class)));
	}

	@Test
	public void testLoadFixedStringAdapterHasCorrectValue() {
		assertThat(loadStringAdapter().currentValue(),
				is(equalTo("helloWorld")));
	}

	@Test
	public void testLoadFixedLongAdapterReturnsNonNullAdapter() {
		assertThat(loadLongAdapter(), is(notNullValue()));
	}

	@Test
	public void testLoadFixedLongAdapterIsCorrectType() {
		assertThat(loadLongAdapter(),
				is(instanceOf(FixedLongValueAdapter.class)));
	}

	@Test
	public void testLoadFixedLongAdapterHasCorrectValue() {
		assertThat(loadLongAdapter().currentValue(), is(equalTo(2L)));
	}

	@Test
	public void testLoadFixedGenericAdapterReturnsNonNullAdapter() {
		assertThat(loadGenericAdapter(), is(notNullValue()));
	}

	@Test
	public void testLoadFixedGenericAdapterIsCorrectType() {
		assertThat(loadGenericAdapter(), is(instanceOf(FixedValueAdapter.class)));
	}

	@Test
	public void testLoadFixedGenericAdapterHasCorrectValue() {
		assertThat(loadGenericAdapter().currentValue(), is(equalTo(3)));
	}

	@Test
	public void testLoadFixedGenericAdapterWithPaddedConstructorReturnsNonNullAdapter() {
		assertThat(loadGenericAdapterWithPaddedConstructor(), is(notNullValue()));
	}

	@Test
	public void testLoadFixedGenericAdapterWithPaddedConstructorIsCorrectType() {
		assertThat(loadGenericAdapterWithPaddedConstructor(), is(instanceOf(FixedValueWithPaddedConstructorAdapter.class)));
	}
	
	@Test
	public void testLoadFixedGenericAdapterWithPaddedConstructorHasCorrectValue() {
		assertThat(loadGenericAdapterWithPaddedConstructor().currentValue(),
				is(equalTo(4)));
	}

	private MonitoredValueAdapter<String> loadStringAdapter() {
		return loadAdapter(PROPERTIES_FILENAME_FIXED_STRING_ADAPTER,
				String.class);
	}

	private MonitoredValueAdapter<Long> loadLongAdapter() {
		return loadAdapter(PROPERTIES_FILENAME_FIXED_LONG_ADAPTER, Long.class);
	}

	private MonitoredValueAdapter<Integer> loadGenericAdapter() {
		return loadAdapter(PROPERTIES_FILENAME_FIXED_GENERIC_ADAPTER,
				Integer.class);
	}

	private MonitoredValueAdapter<Integer> loadGenericAdapterWithPaddedConstructor() {
		return loadAdapter(
				PROPERTIES_FILENAME_FIXED_GENERIC_WITH_PADDED_CONSTRUCTOR_ADAPTER,
				Integer.class);
	}

	@SuppressWarnings("unchecked")
	private <T> MonitoredValueAdapter<T> loadAdapter(String filename,
			Class<T> clazz) {
		return (MonitoredValueAdapter<T>) this.loader.load(
				properties(filename), PROPERTY_NAME_ADAPTER);
	}

	private Properties properties(String filename) {
		Properties properties = new Properties();
		try {
			properties.load(getClass().getResourceAsStream(filename));
		} catch (IOException e) {
			throw new IllegalStateException("Unable to load properties file", e);
		}
		return properties;
	}

}