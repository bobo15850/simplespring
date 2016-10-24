package zb.springframework.core.io.support;

import org.springframework.util.Assert;

import zb.springframework.core.io.Resource;

/**
 * 指定了编码格式的资源文件
 * 
 * @author yizhi.zb
 *
 */
public class EncodedResource {
	private final Resource resource;

	private final String encoding;

	public EncodedResource(Resource resource) {
		this(resource, null);
	}

	public EncodedResource(Resource resource, String encoding) {
		Assert.notNull(resource, "Resource must not be null");
		this.resource = resource;
		this.encoding = encoding;
	}

	public final Resource getResource() {
		return resource;
	}

	public final String getEncoding() {
		return encoding;
	}
}
