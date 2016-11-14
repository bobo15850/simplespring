package zb.springframework.context.support;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.core.io.Resource;

import zb.springframework.context.ApplicationContext;

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

	public ClassPathXmlApplicationContext(String[] configLocations, boolean refresh, ApplicationContext parent)
			throws BeansException {
		super(parent);
		setConfigLocations(configLocations);
		if (refresh) {
			refresh();
		}
	}

	@Override
	public Resource[] getResources(String locationPattern) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
