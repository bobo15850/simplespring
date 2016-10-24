package zb.springframework.context;

import org.springframework.beans.BeansException;

import zb.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public interface ConfigurableApplicationContext extends ApplicationContext, Lifecycle {
	void setId(String id);

	void setParent(ApplicationContext parent);

	/**
	 * 加载或者刷新持久化配置文件
	 * 
	 * @throws BeansException
	 * @throws IllegalStateException
	 */
	void refresh() throws BeansException, IllegalStateException;

	ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

}
