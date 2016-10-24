package zb.springframework.beans.factory.xml;

import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;

public interface DocumentLoader {

	/**
	 * 从文件源中加载一个Document
	 * 
	 * @param inputSource
	 * @param entityResolver
	 * @param errorHandler
	 * @param validationMode
	 * @param namespaceAware
	 * @return
	 * @throws Exception
	 */
	Document loadDocument(InputSource inputSource, EntityResolver entityResolver, ErrorHandler errorHandler,
			int validationMode, boolean namespaceAware) throws Exception;
}
