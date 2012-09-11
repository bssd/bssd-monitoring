package uk.co.bssd.monitoring.loader;

public class ParameterDescriptor {

	private final String name;
	private final String classname;
	private final boolean generic;
	private final String genericTypeName;
	
	public ParameterDescriptor(String name, String classname) {
		this(name, classname, false, null);
	}
	
	public ParameterDescriptor(String name, String classname, String genericTypeName) {
		this(name, classname, true, genericTypeName);
	}
	
	private ParameterDescriptor(String name, String classname, boolean generic, String genericTypeName) {
		this.name = name;
		this.classname = classname;
		this.generic = generic;
		this.genericTypeName = genericTypeName;
	}
	
	public String name() {
		return this.name;
	}
	
	public String classname() {
		return this.classname;
	}
	
	public boolean isGenericType() {
		return this.generic;
	}
	
	public String genericTypeName() {
		return this.genericTypeName;
	}
}