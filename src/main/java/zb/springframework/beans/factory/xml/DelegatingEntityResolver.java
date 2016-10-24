package zb.springframework.beans.factory.xml;

import java.io.IOException;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 使用代理模式将DTD和XSD格式的验证文件加载进行统一接口提供
 * 
 * @author yizhi.zb
 *
 */
public class DelegatingEntityResolver implements EntityResolver {

	public static final String DTD_SUFFIX = ".dtd";
	/**
	 * 两种不同的验证文件的后缀
	 */
	public static final String XSD_SUFFIX = ".xsd";

	private EntityResolver dtdResolver;

	private EntityResolver schemaResolver;

	public DelegatingEntityResolver(ClassLoader classLoader) {
		dtdResolver = new BeansDtdResolver();
		schemaResolver = new PluggableSchemaResolver(classLoader);
	}

	@Override
	public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
		if (systemId != null) {
			if (systemId.endsWith(DTD_SUFFIX)) {
				return this.dtdResolver.resolveEntity(publicId, systemId);
			} else if (systemId.endsWith(XSD_SUFFIX)) {
				return this.schemaResolver.resolveEntity(publicId, systemId);
			}
		}
		return null;
	}

}
