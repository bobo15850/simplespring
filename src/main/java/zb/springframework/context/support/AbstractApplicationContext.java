package zb.springframework.context.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.ObjectUtils;

import zb.springframework.beans.factory.BeanFactory;
import zb.springframework.beans.factory.DisposableBean;
import zb.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import zb.springframework.context.ApplicationContext;
import zb.springframework.context.ConfigurableApplicationContext;

public abstract class AbstractApplicationContext extends DefaultResourceLoader
		implements ConfigurableApplicationContext, DisposableBean {

	protected final Log logger = LogFactory.getLog(getClass());

	private String id = ObjectUtils.identityToString(this);

	private ApplicationContext parent;

	private long startupDate;

	/** 指示该context是否激活状态 */
	private boolean active = false;

	/** 指示该context是否关闭 */
	private boolean closed = false;

	/** active的锁 */
	private final Object activeMonitor = new Object();

	/** refresh和destroy的锁 */
	private final Object startupShutdownMonitor = new Object();

	private ResourcePatternResolver resourcePatternResolver;

	public AbstractApplicationContext(ApplicationContext parent) {
		this.parent = parent;
		this.resourcePatternResolver = getResourcePatternResolver();
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public ApplicationContext getParent() {
		return parent;
	}

	public long getStartupDate() {
		return this.startupDate;
	}

	protected ResourcePatternResolver getResourcePatternResolver() {
		return new PathMatchingResourcePatternResolver(this);
	}

	public void setParent(ApplicationContext parent) {
		this.parent = parent;
	}

	/**
	 * 该方法是一个模板方法，定义了整个加载过程的流程
	 */
	public void refresh() throws BeansException, IllegalStateException {
		synchronized (this.startupShutdownMonitor) {
			prepareRefresh();

			ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
		}
	}

	/**
	 * 准备工作，设置开始时间，加锁防止并发，设置激活flag
	 */
	protected void prepareRefresh() {
		this.startupDate = System.currentTimeMillis();

		synchronized (this.activeMonitor) {
			this.active = true;
		}

		if (logger.isInfoEnabled()) {
			logger.info("Refreshing " + this);
		}
	}

	protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
		refreshBeanFactory();
		return null;
	}

	/**
	 * 模板方法，销毁此context管理的所有bean
	 */
	protected void destroyBeans() {
		// TODO
	}

	public Object getBean(String name) throws BeansException {
		return null;
	}

	protected BeanFactory getInternalParentBeanFactory() {
		return (getParent() instanceof ConfigurableApplicationContext)
				? ((ConfigurableApplicationContext) getParent()).getBeanFactory() : getParent();
	}

	/**
	 * 该方法由子类实现实现初始化
	 * 
	 * @throws BeansException
	 * @throws IllegalStateException
	 *             重复refresh
	 */
	protected abstract void refreshBeanFactory() throws BeansException, IllegalStateException;

	public abstract ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

}
