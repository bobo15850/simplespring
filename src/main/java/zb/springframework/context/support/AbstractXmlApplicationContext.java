package zb.springframework.context.support;

import java.io.IOException;

import org.springframework.beans.BeansException;

import zb.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import zb.springframework.context.ApplicationContext;
import zb.springframework.core.io.Resource;

public abstract class AbstractXmlApplicationContext extends AbstractRefreshableConfigApplicationContext {

	public AbstractXmlApplicationContext(ApplicationContext parent) {
		super(parent);
	}

	protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException, IOException {
		XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
		beanDefinitionReader.setResourceLoader(this);
		// TODO 一系列辅助措施

	}

	/**
	 * 通过给定的xmlBeanDefinitionReader来加载bean定义信息
	 * 
	 * @param reader
	 * @throws BeansException
	 * @throws IOException
	 */
	protected void loadBeanDefinitions(XmlBeanDefinitionReader reader) throws BeansException, IOException {
		Resource[] configResources = getConfigResources();
		if (configResources != null) {
			reader.loadBeanDefinitions(configResources);
		}

		String[] configLocations = getConfigLocations();
		if (configLocations != null) {
			reader.loadBeanDefinitions(configLocations);
		}
	}

	protected Resource[] getConfigResources() {
		return null;
	}

}
