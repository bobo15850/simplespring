package zb.springframework.core.io.support;

import java.io.IOException;

import org.springframework.util.Assert;

import zb.springframework.core.io.Resource;
import zb.springframework.core.io.ResourceLoader;

public class PathMatchingResourcePatternResolver implements ResourcePatternResolver {

	private final ResourceLoader resourceLoader;

	public PathMatchingResourcePatternResolver(ResourceLoader resourceLoader) {
		Assert.notNull(resourceLoader, "ResourceLoader must not be null");
		this.resourceLoader = resourceLoader;
	}

	public ResourceLoader getResourceLoader() {
		return this.resourceLoader;
	}

	@Override
	public Resource getResource(String location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClassLoader getClassLoader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource[] getResources(String locationPattern) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
}
