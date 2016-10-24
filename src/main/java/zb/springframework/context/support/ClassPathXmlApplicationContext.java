package zb.springframework.context.support;

import java.io.IOException;

import org.springframework.beans.BeansException;

import zb.springframework.context.ApplicationContext;
import zb.springframework.core.io.Resource;

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

	public ClassPathXmlApplicationContext(String[] configLocations, boolean refresh, ApplicationContext parent)
			throws BeansException {
		super(parent);
		setConfigLocations(configLocations);
		if (refresh) {
			refresh();
		}
	}

}
