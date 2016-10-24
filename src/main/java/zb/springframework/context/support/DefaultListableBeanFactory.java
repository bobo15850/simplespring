package zb.springframework.context.support;

import java.io.Serializable;

import zb.springframework.beans.factory.BeanFactory;
import zb.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import zb.springframework.beans.factory.support.BeanDefinitionRegistry;

public class DefaultListableBeanFactory
		implements ConfigurableListableBeanFactory, BeanDefinitionRegistry, Serializable {
	private static final long serialVersionUID = -163907832733544086L;

	public DefaultListableBeanFactory(BeanFactory parentBeanFactory) {
		// TODO
	}
}
