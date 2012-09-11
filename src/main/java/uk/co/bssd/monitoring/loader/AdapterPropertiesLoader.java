package uk.co.bssd.monitoring.loader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;

import uk.co.bssd.monitoring.MonitoredValueAdapter;

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

	@Override
	public MonitoredValueAdapter<T> load(Properties properties,
			String propertyName) {
		Class<?> clazz = getClass(properties, propertyName);
		Constructor<?> constructor = constructor(clazz);
		List<Object> arguments = arguments(properties, propertyName, clazz,
				constructor);
		return instantiate(constructor, arguments);
	}

	@SuppressWarnings("unchecked")
	private MonitoredValueAdapter<T> instantiate(Constructor<?> constructor,
			List<Object> arguments) {
		try {
			return (MonitoredValueAdapter<T>) constructor.newInstance(arguments
					.toArray());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to instantiate class", e);
		}
	}

	private List<Object> arguments(Properties properties, String propertyName,
			Class<?> clazz, Constructor<?> constructor) {
		List<ParameterDescriptor> parameters = parameters(clazz, constructor);

		List<Object> args = new ArrayList<Object>();
		for (ParameterDescriptor parameter : parameters) {
			String key = propertyName + "." + parameter.name();

			if (!properties.containsKey(key)) {
				throw new IllegalStateException("Expected to find property '"
						+ key + "' required for type '" + clazz + "'");
			}
			Object unwrappedValue = properties.get(key);
			Object parameterValue = convertParameterToCorrectType(properties,
					propertyName, parameter, unwrappedValue);
			args.add(parameterValue);
		}
		return args;
	}

	private Object convertParameterToCorrectType(Properties properties,
			String propertyName, ParameterDescriptor parameter,
			Object unwrappedValue) {
		String classname;

		if (parameter.isGenericType()) {
			classname = (String)properties.get(propertyName + ".type." + parameter.genericTypeName()); 
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

	private Constructor<?> constructor(Class<?> clazz) {
		Constructor<?>[] constructors = clazz.getConstructors();
		if (constructors.length != 1) {
			throw new IllegalStateException(
					"Expect only 1 constructor to avoid ambiguity");
		}
		return constructors[0];
	}

	private Class<?> getClass(Properties properties, String propertyName) {
		String className = properties.getProperty(propertyName);
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(
					"Unable to load specified class as it could not be found",
					e);
		}
	}

	private List<ParameterDescriptor> parameters(Class<?> clazz,
			Constructor<?> constructor) {
		InputStream classFileInputStream = classInputStream(clazz);
		ClassNode classNode = classNode(classFileInputStream);
		return parameters(classNode, constructor);
	}

	private InputStream classInputStream(Class<?> clazz) {
		ClassLoader declaringClassLoader = clazz.getClassLoader();
		Type declaringType = Type.getType(clazz);
		String url = declaringType.getInternalName() + ".class";

		InputStream classFileInputStream = declaringClassLoader
				.getResourceAsStream(url);
		if (classFileInputStream == null) {
			throw new IllegalArgumentException(
					"The constructor's class loader cannot find the bytecode that defined the constructor's class (URL: "
							+ url + ")");
		}
		return classFileInputStream;
	}

	@SuppressWarnings("unchecked")
	private List<ParameterDescriptor> parameters(ClassNode classNode,
			Constructor<?> constructor) {
		MethodNode method = constructorMethodNode(classNode, constructor);
		Type[] argumentTypes = Type.getArgumentTypes(method.desc);
		List<ParameterDescriptor> parameterNames = new ArrayList<ParameterDescriptor>(
				argumentTypes.length);

		java.lang.reflect.Type[] genericParameterTypes = constructor
				.getGenericParameterTypes();

		List<LocalVariableNode> localVariables = method.localVariables;
		// The first local variable actually represents "this" object
		for (int i = 0; i < argumentTypes.length; i++) {
			String name = localVariables.get(i + 1).name;
			Type type = argumentTypes[0];
			String parameterClass = type.getClassName();

			ParameterDescriptor parameterDescriptor;

			java.lang.reflect.Type genericParameterType = genericParameterTypes[i];
			if (genericParameterType instanceof TypeVariable) {
				TypeVariable<?> typeVariable = (TypeVariable<?>) genericParameterType;
				parameterDescriptor = new ParameterDescriptor(name,
						parameterClass, typeVariable.getName());
			} else {
				parameterDescriptor = new ParameterDescriptor(name,
						parameterClass);
			}

			parameterNames.add(parameterDescriptor);
		}

		return parameterNames;
	}

	@SuppressWarnings("unchecked")
	private MethodNode constructorMethodNode(ClassNode classNode,
			Constructor<?> constructor) {
		List<MethodNode> methods = classNode.methods;
		String constructorDescriptor = Type
				.getConstructorDescriptor(constructor);
		for (MethodNode method : methods) {
			if (method.name.equals("<init>")
					&& method.desc.equals(constructorDescriptor)) {
				return method;
			}
		}
		throw new IllegalStateException(
				"Unable to find method node for constructor");
	}

	private ClassNode classNode(InputStream classFileInputStream) {
		try {
			ClassNode classNode = new ClassNode();
			ClassReader classReader = new ClassReader(classFileInputStream);
			classReader.accept(classNode, 0);
			return classNode;
		} catch (IOException e) {
			throw new IllegalStateException(
					"Unable to determine parameter names for constructor", e);
		} finally {
			try {
				classFileInputStream.close();
			} catch (IOException e) {
			}
		}
	}

	private static int lastIndexOfDot(String string) {
		return string.lastIndexOf(DOT);
	}
}