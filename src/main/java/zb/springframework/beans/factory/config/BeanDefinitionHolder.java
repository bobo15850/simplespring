package zb.springframework.beans.factory.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.util.Assert;

import zb.springframework.beans.BeanMetadataElement;

public class BeanDefinitionHolder implements BeanMetadataElement {
	private final BeanDefinition beanDefinition;
	private final String beanName;
	private final String[] aliases;

	public BeanDefinitionHolder(BeanDefinition beanDefinition, String beanName, String[] aliases) {
		Assert.notNull(beanDefinition, "BeanDefinition must not be null");
		Assert.notNull(beanName, "Bean name must not be null");
		this.beanDefinition = beanDefinition;
		this.beanName = beanName;
		this.aliases = aliases;
	}

	@Override
	public Object getSource() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getBeanName() {
		// TODO Auto-generated method stub
		return null;
	}

}
