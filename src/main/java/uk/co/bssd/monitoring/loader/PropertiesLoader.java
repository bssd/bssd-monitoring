package uk.co.bssd.monitoring.loader;

import java.util.Properties;

public interface PropertiesLoader<T> {

	boolean canLoad(String propertyName);
	
	T load(Properties properties, String propertyName);
}
