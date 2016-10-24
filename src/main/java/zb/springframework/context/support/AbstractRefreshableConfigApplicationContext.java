package zb.springframework.context.support;

import org.springframework.util.Assert;
import org.springframework.util.SystemPropertyUtils;

import zb.springframework.beans.factory.BeanNameAware;
import zb.springframework.beans.factory.InitializingBean;
import zb.springframework.context.ApplicationContext;

public abstract class AbstractRefreshableConfigApplicationContext extends AbstractRefreshableApplicationContext
		implements BeanNameAware, InitializingBean {

	private String[] configLocations;

	public AbstractRefreshableConfigApplicationContext(ApplicationContext parent) {
		super(parent);
	}

	/**
	 * 设置配置文件的地址
	 * 
	 * @param locations
	 */
	public void setConfigLocations(String[] locations) {
		if (locations != null) {
			// 该方法确保数组中没有null
			Assert.noNullElements(locations, "Config locations must not be null");
			this.configLocations = new String[locations.length];
			for (int i = 0; i < locations.length; i++) {
				this.configLocations[i] = resolvePath(locations[i]).trim();
			}
		} else {
			this.configLocations = null;
		}
	}

	protected String[] getConfigLocations() {
		return (this.configLocations != null ? this.configLocations : getDefaultConfigLocations());
	}

	protected String[] getDefaultConfigLocations() {
		return null;
	}

	/**
	 * 用系统定义的值替换占位符，成为真正的路径
	 * 
	 * @param path
	 * @return
	 */
	protected String resolvePath(String path) {
		return SystemPropertyUtils.resolvePlaceholders(path);
	}
}
