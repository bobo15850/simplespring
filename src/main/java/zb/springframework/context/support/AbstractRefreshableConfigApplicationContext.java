package zb.springframework.context.support;

import zb.springframework.beans.factory.BeanNameAware;
import zb.springframework.beans.factory.InitializingBean;

public abstract class AbstractRefreshableConfigApplicationContext extends AbstractRefreshableApplicationContext
		implements BeanNameAware, InitializingBean {
	public AbstractRefreshableConfigApplicationContext() {

	}
}
