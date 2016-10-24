package zb.springframework.core.io.support;

import java.io.IOException;

import zb.springframework.core.io.Resource;
import zb.springframework.core.io.ResourceLoader;

public interface ResourcePatternResolver extends ResourceLoader {
	String CLASSPATH_ALL_URL_PREFIX = "classpath*:";

	Resource[] getResources(String locationPattern) throws IOException;
}
