package zb.springframework.context.support;

import java.io.Serializable;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;

import zb.springframework.beans.factory.BeanFactory;
import zb.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import zb.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import zb.springframework.beans.factory.support.BeanDefinitionRegistry;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
		implements ConfigurableListableBeanFactory, BeanDefinitionRegistry, Serializable {
	private static final long serialVersionUID = -163907832733544086L;

	public DefaultListableBeanFactory(BeanFactory parentBeanFactory) {
		// TODO
	}

	@Override
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition)
			throws BeanDefinitionStoreException {
		// TODO Auto-generated method stub

	}
}
