package uk.co.bssd.monitoring.loader;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import uk.co.bssd.monitoring.MonitoredValueAdapter;
import uk.co.bssd.reflection.ClassWrapper;
import uk.co.bssd.reflection.ConstructorWrapper;
import uk.co.bssd.reflection.ParameterWrapper;
import uk.co.bssd.reflection.ReflectionException;

public class AdapterPropertiesLoader<T> implements
		PropertiesLoader<MonitoredValueAdapter<T>> {

	private static final String DOT = ".";
	private static final String PREFIX_ADAPTER = "uk.co.bssd.monitoring.adapter.";
	private static final int LAST_DOT_IN_PREFIX = PREFIX_ADAPTER
			.lastIndexOf(DOT);

	@Override
	public boolean canLoad(String propertyName) {
		return propertyName.startsWith(PREFIX_ADAPTER)
				&& lastIndexOfDot(propertyName) == LAST_DOT_IN_PREFIX;
	}

	@SuppressWarnings("unchecked")
	@Override
	public MonitoredValueAdapter<T> load(Properties properties,
			String propertyName) {
		ClassWrapper clazz = getClassWrapper(properties, propertyName);
		ConstructorWrapper constructor = constructor(clazz);
		List<Object> arguments = arguments(properties, propertyName, clazz,
				constructor);
		return (MonitoredValueAdapter<T>)constructor.instantiate(arguments);
	}

	private List<Object> arguments(Properties properties, String propertyName,
			ClassWrapper clazz, ConstructorWrapper constructor) {
		List<ParameterWrapper> parameters = constructor.parameters();

		List<Object> args = new ArrayList<Object>();
		for (ParameterWrapper parameter : parameters) {
			String key = propertyName + "." + parameter.name();

			if (!properties.containsKey(key)) {
				throw new IllegalStateException("Expected to find property '"
						+ key + "' required for type '" + clazz.className() + "'");
			}
			Object unwrappedValue = properties.get(key);
			Object parameterValue = convertParameterToCorrectType(properties,
					propertyName, parameter, unwrappedValue);
			args.add(parameterValue);
		}
		return args;
	}

	private Object convertParameterToCorrectType(Properties properties,
			String propertyName, ParameterWrapper parameter,
			Object unwrappedValue) {
		String classname;

		if (parameter.isGenericType()) {
			classname = (String) properties.get(propertyName + ".type."
					+ parameter.genericTypeName());
		} else {
			classname = parameter.classname();
		}
		Constructor<?> parameterConstructor = parameterConstructor(classname);
		try {
			return parameterConstructor.newInstance(unwrappedValue);
		} catch (Exception e) {
			throw new IllegalStateException(
					"Problem converting parameter to correct type", e);
		}
	}

	private Constructor<?> parameterConstructor(String classname) {
		try {
			return Class.forName(classname).getConstructor(String.class);
		} catch (Exception e) {
			throw new IllegalStateException(
					"Problem getting parameter constructor", e);
		}
	}

	private ConstructorWrapper constructor(ClassWrapper classWrapper) {
		List<ConstructorWrapper> constructors = classWrapper.constructors();
		if (constructors.size() != 1) {
			throw new ReflectionException(
					"Expect only 1 constructor to avoid ambiguity");
		}
		return constructors.get(0);
	}

	private ClassWrapper getClassWrapper(Properties properties,
			String propertyName) {
		String className = properties.getProperty(propertyName);
		return ClassWrapper.forName(className);
	}

	private static int lastIndexOfDot(String string) {
		return string.lastIndexOf(DOT);
	}
}