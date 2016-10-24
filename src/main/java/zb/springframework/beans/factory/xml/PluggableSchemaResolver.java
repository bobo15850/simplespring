package zb.springframework.beans.factory.xml;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class PluggableSchemaResolver implements EntityResolver {
	// 保存网络上的schema地址和本地文件schema的映射文件
	public static final String DEFAULT_SCHEMA_MAPPINGS_LOCATION = "META-INF/spring.schemas";

	private static final Log logger = LogFactory.getLog(PluggableSchemaResolver.class);

	private final ClassLoader classLoader;

	private final String schemaMappingsLocation;

	private volatile Map<String, String> schemaMappings;// 懒加载模式

	public PluggableSchemaResolver(ClassLoader classLoader) {
		this.classLoader = classLoader;
		schemaMappingsLocation = DEFAULT_SCHEMA_MAPPINGS_LOCATION;
	}

	@Override
	public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
		// TODO 具体的加载过程
		return null;
	}

	
}
