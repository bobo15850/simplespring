package zb.springframework.context.support;

import org.springframework.beans.BeansException;

import zb.springframework.beans.factory.DisposableBean;
import zb.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import zb.springframework.context.ConfigurableApplicationContext;
import zb.springframework.core.io.DefaultResourceLoader;

public abstract class AbstractApplicationContext extends DefaultResourceLoader
		implements ConfigurableApplicationContext, DisposableBean {

	public AbstractApplicationContext() {

	}

	public Object getBean(String name) throws BeansException {
		return null;
	}

	public abstract ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

}
