package zb.springframework.beans.factory.xml;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class BeansDtdResolver implements EntityResolver {
	private static final String DTD_EXTENSION = ".dtd";

	private static final String[] DTD_NAMES = { "spring-beans-2.0", "spring-beans" };

	private static final Log logger = LogFactory.getLog(BeansDtdResolver.class);

	@Override
	public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
		if (logger.isTraceEnabled()) {
			logger.trace(
					"Trying to resolve XML entity with public ID [" + publicId + "] and system ID [" + systemId + "]");
		}
		if (systemId != null && systemId.endsWith(DTD_EXTENSION)) {
			int lastPathSeparator = systemId.lastIndexOf("/");// 最后一个路径分隔符
			for (String DTD_NAME : DTD_NAMES) {
				int dtdNameStart = systemId.indexOf(DTD_NAME);
				if (dtdNameStart > lastPathSeparator) {
					String dtdFile = systemId.substring(dtdNameStart);
					if (logger.isTraceEnabled()) {
						logger.trace("Trying to locate [" + dtdFile + "] in Spring jar");
					}
					// TODO 获取InputSource
				}
			}

		}
		return null;
	}

}
