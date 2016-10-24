package zb.springframework.context.support;

import java.io.IOException;

import org.springframework.beans.BeansException;

import zb.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import zb.springframework.context.ApplicationContext;

public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

	private DefaultListableBeanFactory beanFactory;

	private final Object beanFactoryMonitor = new Object();

	public AbstractRefreshableApplicationContext(ApplicationContext parent) {
		super(parent);
	}

	protected final void refreshBeanFactory() throws BeansException {
		if (hasBeanFactory()) {
			// 先把原来的bean和beanFactory全部销毁再重新load
			destroyBeans();
			closeBeanFactory();
		}
		try {
			DefaultListableBeanFactory beanFactory = createBeanFactory();
			// TODO 设置serializationId
			customizeBeanFactory(beanFactory);// 加入个性化设置
			loadBeanDefinitions(beanFactory);
			synchronized (this.beanFactoryMonitor) {
				this.beanFactory = beanFactory;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	protected final void closeBeanFactory() {
		synchronized (this.beanFactoryMonitor) {
			// TODO serializationId未置空
			this.beanFactory = null;
		}
	}

	/**
	 * 判断是否已经存在beanFactory
	 * 
	 * @return
	 */
	protected final boolean hasBeanFactory() {
		synchronized (this.beanFactoryMonitor) {
			return (this.beanFactory != null);
		}
	}

	public final ConfigurableListableBeanFactory getBeanFactory() {
		synchronized (this.beanFactoryMonitor) {
			if (this.beanFactory == null) {
				throw new IllegalStateException("BeanFactory not initialized or already closed - "
						+ "call 'refresh' before accessing beans via the ApplicationContext");
			}
			return this.beanFactory;
		}
	}

	protected DefaultListableBeanFactory createBeanFactory() {
		return new DefaultListableBeanFactory(getInternalParentBeanFactory());
	}

	protected void customizeBeanFactory(DefaultListableBeanFactory beanFactory) {
		/*
		 * TODO 加入定制化的属性 <br> 是否允许同名定义的bean覆盖 <br> 是否允许bean之间循环依赖
		 * 
		 * 是否支持Qualifier和autowired注解
		 */
	}

	/**
	 * 将bean定义解析进传入的 bean factory
	 * 
	 * @param beanFactory
	 * @throws BeansException
	 * @throws IOException
	 */
	protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory)
			throws BeansException, IOException;
}
