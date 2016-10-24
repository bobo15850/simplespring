package zb.springframework.beans.factory.support;

import java.io.IOException;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.util.Assert;

import zb.springframework.core.io.Resource;
import zb.springframework.core.io.ResourceLoader;
import zb.springframework.core.io.support.ResourcePatternResolver;

public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

	protected final Log logger = LogFactory.getLog(getClass());

	private final BeanDefinitionRegistry registry;// bean注册器

	private ResourceLoader resourceLoader;

	private ClassLoader beanClassLoader;

	private BeanNameGenerator beanNameGenerator = new DefaultBeanNameGenerator();

	protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
		Assert.notNull(registry, "BeanDefinitionRegistry must not be null");
		this.registry = registry;

		// TODO 初始化resourceLoader
	}

	public final BeanDefinitionRegistry getBeanFactory() {
		return this.registry;
	}

	public final BeanDefinitionRegistry getRegistry() {
		return this.registry;
	}

	public ResourceLoader getResourceLoader() {
		return resourceLoader;
	}

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public ClassLoader getBeanClassLoader() {
		return beanClassLoader;
	}

	public void setBeanClassLoader(ClassLoader beanClassLoader) {
		this.beanClassLoader = beanClassLoader;
	}

	public BeanNameGenerator getBeanNameGenerator() {
		return beanNameGenerator;
	}

	public void setBeanNameGenerator(BeanNameGenerator beanNameGenerator) {
		this.beanNameGenerator = (beanNameGenerator != null ? beanNameGenerator : new DefaultBeanNameGenerator());
	}

	/**
	 * 所有的loadBeanDefinitions的方法都会收敛到 子类中的 loadBeanDefinitions(Resource
	 * resource)中,然后封装resource得到EncodedResource
	 */

	public int loadBeanDefinitions(Resource... resources) throws BeanDefinitionStoreException {
		Assert.notNull(resources, "Resource array must not be null");
		int counter = 0;
		for (Resource resource : resources) {
			counter += loadBeanDefinitions(resource);// 该方法由子类实现
		}
		return counter;
	}

	public int loadBeanDefinitions(String location) throws BeanDefinitionStoreException {
		return loadBeanDefinitions(location, null);
	}

	/**
	 * 
	 * @param location
	 * @param actualResources
	 *            加载过程中已经处理过的资源
	 * @return
	 * @throws BeanDefinitionStoreException
	 */
	public int loadBeanDefinitions(String location, Set<Resource> actualResources) throws BeanDefinitionStoreException {
		ResourceLoader resourceLoader = getResourceLoader();
		if (resourceLoader == null) {
			throw new BeanDefinitionStoreException(
					"Cannot import bean definitions from location [" + location + "]: no ResourceLoader available");
		}

		if (resourceLoader instanceof ResourcePatternResolver) {
			try {
				Resource[] resources = ((ResourcePatternResolver) resourceLoader).getResources(location);// 获取所有资源文件
				int loadCount = loadBeanDefinitions(resources);
				if (actualResources != null) {
					for (Resource resource : resources) {
						actualResources.add(resource);
					}
				}
				if (logger.isDebugEnabled()) {
					logger.debug("Loaded " + loadCount + " bean definitions from location pattern [" + location + "]");
				}
				return loadCount;
			} catch (IOException ex) {
				throw new BeanDefinitionStoreException(
						"Could not resolve bean definition resource pattern [" + location + "]", ex);
			}
		} else {
			// 固定的url中加载单个资源
			Resource resource = resourceLoader.getResource(location);
			int loadCount = loadBeanDefinitions(resource);
			if (actualResources != null) {
				actualResources.add(resource);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Loaded " + loadCount + " bean definitions from location [" + location + "]");
			}
			return loadCount;
		}
	}

	public int loadBeanDefinitions(String... locations) throws BeanDefinitionStoreException {
		Assert.notNull(locations, "Location array must not be null");
		int counter = 0;
		for (String location : locations) {
			counter += loadBeanDefinitions(location);
		}
		return counter;
	}

}
