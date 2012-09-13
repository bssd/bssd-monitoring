package uk.co.bssd.monitoring.loader;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uk.co.bssd.monitoring.Monitors;

public class SpringMonitorsLoader implements MonitorsLoader {

	private final ApplicationContext context;
	
	public SpringMonitorsLoader(String configLocation) {
		this.context = new ClassPathXmlApplicationContext(configLocation);
	}
	
	@Override
	public void load(Monitors monitors) {
		Map<String, MonitorDefinition> monitorDefinitions = this.context.getBeansOfType(MonitorDefinition.class);
		
		for (MonitorDefinition definition : monitorDefinitions.values()) {
			monitors.register(definition.monitor(), definition.monitorPeriodMs());
		}
	}
}