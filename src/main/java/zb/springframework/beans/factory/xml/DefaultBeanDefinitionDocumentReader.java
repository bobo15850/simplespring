package zb.springframework.beans.factory.xml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DefaultBeanDefinitionDocumentReader implements BeanDefinitionDocumentReader {
	protected final Log logger = LogFactory.getLog(getClass());

	private XmlReaderContext readerContext;

	@Override
	public void registerBeanDefinitions(Document doc, XmlReaderContext readerContext)
			throws BeanDefinitionStoreException {
		this.readerContext = readerContext;
		logger.debug("Loading bean definitions");
		Element root = doc.getDocumentElement();
		BeanDefinitionParserDelegate delegate = createHelper(readerContext, root);

		preProcessXml(root);
		parseBeanDefinitions(root, delegate);
		postProcessXml(root);
	}

	protected BeanDefinitionParserDelegate createHelper(XmlReaderContext readerContext, Element root) {
		// TODO 初始化代理解析器
		return null;
	}

	protected void parseBeanDefinitions(Element root, BeanDefinitionParserDelegate delegate) {
		// TODO 处理过程
	}

	/**
	 * 这两个方法允许使用者使用自定义的xml标签
	 * 
	 * @param root
	 */
	protected void preProcessXml(Element root) {
	}

	protected void postProcessXml(Element root) {
	}

}
