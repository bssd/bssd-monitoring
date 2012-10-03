package uk.co.bssd.monitoring;

import uk.co.bssd.jmx.ManagementBeanServer;
import uk.co.bssd.jmx.ProcessManagementBean;

public class CpuUsageAdapter implements MonitoredValueAdapter<Double> {

	private final ProcessManagementBean processBean;
	
	public CpuUsageAdapter(ManagementBeanServer beanServer) {
		this.processBean = beanServer.processManagementBean();
	}
	
	@Override
	public Double currentValue() {
		return this.processBean.cpuUsage();
	}
}