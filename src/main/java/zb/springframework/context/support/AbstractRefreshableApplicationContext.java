package zb.springframework.context.support;

import zb.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

	public final ConfigurableListableBeanFactory getBeanFactory() {
		return null;
	}

}
