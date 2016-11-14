package zb.springframework.beans.factory.support;

import org.springframework.beans.factory.config.SingletonBeanRegistry;

import zb.springframework.core.SimpleAliasRegistry;

public class DefaultSingletonBeanRegistry extends SimpleAliasRegistry implements SingletonBeanRegistry {

	@Override
	public void registerSingleton(String beanName, Object singletonObject) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getSingleton(String beanName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsSingleton(String beanName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String[] getSingletonNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSingletonCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
