package zb.springframework.context.support;

import org.springframework.beans.BeansException;

import zb.springframework.context.ApplicationContext;

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

	public ClassPathXmlApplicationContext(String[] configLocations, boolean refresh, ApplicationContext parent)
			throws BeansException {
		super(parent);
	}
}
