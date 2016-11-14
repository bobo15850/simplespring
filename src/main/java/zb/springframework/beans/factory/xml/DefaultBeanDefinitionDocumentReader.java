package zb.springframework.beans.factory.xml;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import zb.springframework.beans.factory.config.BeanDefinitionHolder;
import zb.springframework.beans.factory.support.BeanDefinitionReaderUtils;

public class DefaultBeanDefinitionDocumentReader implements BeanDefinitionDocumentReader {
	public static final String BEAN_ELEMENT = BeanDefinitionParserDelegate.BEAN_ELEMENT;

	public static final String ALIAS_ELEMENT = "alias";

	public static final String NAME_ATTRIBUTE = "name";

	public static final String ALIAS_ATTRIBUTE = "alias";

	public static final String IMPORT_ELEMENT = "import";

	public static final String RESOURCE_ATTRIBUTE = "resource";

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

	/**
	 * 创建代理类来读取xml配置，该代理类是真正的解析类
	 * 
	 * @param readerContext
	 * @param root
	 * @return
	 */
	protected BeanDefinitionParserDelegate createHelper(XmlReaderContext readerContext, Element root) {
		BeanDefinitionParserDelegate delegate = new BeanDefinitionParserDelegate(readerContext);
		delegate.initDefaults(root);
		return delegate;
	}

	protected final XmlReaderContext getReaderContext() {
		return this.readerContext;
	}

	protected Object extractSource(Element ele) {
		return this.readerContext.extractSource(ele);
	}

	/**
	 * 该方法是代理类的客户端方法
	 * 
	 * @param root
	 * @param delegate
	 */
	protected void parseBeanDefinitions(Element root, BeanDefinitionParserDelegate delegate) {
		// TODO 处理过程
		if (delegate.isDefaultNamespace(root)) {
			NodeList nl = root.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++) {// 遍历处理每一个标签
				Node node = nl.item(i);
				if (node instanceof Element) {
					Element ele = (Element) node;
					if (delegate.isDefaultNamespace(ele)) {
						parseDefaultElement(ele, delegate);
					} else {
						delegate.parseCustomerElement(ele);
					}
				}
			}
		} else {
			delegate.parseCustomerElement(root);
		}
	}

	/**
	 * 处理默认的import ，alias，bean三种标签
	 * 
	 * @param ele
	 * @param delegate
	 */
	private void parseDefaultElement(Element ele, BeanDefinitionParserDelegate delegate) {
		if (delegate.nodeNameEquals(ele, IMPORT_ELEMENT)) {
			importBeanDefinitionResource(ele);
		} else if (delegate.nodeNameEquals(ele, ALIAS_ELEMENT)) {
			processAliasRegistration(ele);
		} else if (delegate.nodeNameEquals(ele, BEAN_ELEMENT)) {
			processBeanDefinition(ele, delegate);
		}
	}

	/*
	 * 具体处理时因为import和alias处理相对简单（依赖各种上文中封装的组件）所以直接在本类中处理了，bean是主要的用代理类来处理
	 */

	/**
	 * 处理import标签 根据location来判断是绝对路径还是相对路径，然后调用上下文处理器进行处理
	 * 这是一个递归的过程，不能有循环import，在
	 * {@link XmlBeanDefinitionReader#loadBeanDefinitions(zb.springframework.core.io.support.EncodedResource)}
	 * 中已经进行了处理
	 * 
	 * @param ele
	 */
	protected void importBeanDefinitionResource(Element ele) {
		String location = ele.getAttribute(RESOURCE_ATTRIBUTE);
		if (!StringUtils.hasText(location)) {
			getReaderContext().error("Resource location must not be empty", ele);
			return;
		}

		location = SystemPropertyUtils.resolvePlaceholders(location);

		Set<Resource> actualResources = new LinkedHashSet<>(4);
		boolean absoluteLocation = false;// 是否是绝对路径
		try {
			absoluteLocation = ResourcePatternUtils.isUrl(location) || ResourceUtils.toURI(location).isAbsolute();
		} catch (URISyntaxException e) {

		}
		if (absoluteLocation) {
			try {
				int importCount = getReaderContext().getReader().loadBeanDefinitions(location, actualResources);
				if (logger.isDebugEnabled()) {
					logger.debug("Imported " + importCount + " bean definitions from URL location [" + location + "]");
				}
			} catch (BeanDefinitionStoreException ex) {
				getReaderContext().error("Failed to import bean definitions from URL location [" + location + "]", ele,
						ex);
			}
		} else {
			try {
				int importCount;
				Resource relativeResource = getReaderContext().getResource().createRelative(location);
				if (relativeResource.exists()) {
					importCount = getReaderContext().getReader().loadBeanDefinitions(relativeResource);
					actualResources.add(relativeResource); // TODO 这个不太明白
				} else {
					String baseLocation = getReaderContext().getResource().getURL().toString();
					importCount = getReaderContext().getReader().loadBeanDefinitions(
							StringUtils.applyRelativePath(baseLocation, location), actualResources);
				}
				if (logger.isDebugEnabled()) {
					logger.debug(
							"Imported " + importCount + " bean definitions from relative location [" + location + "]");
				}
			} catch (IOException ex) {
				getReaderContext().error("Failed to resolve current resource location", ele, ex);
			} catch (BeanDefinitionStoreException ex) {
				getReaderContext().error("Failed to import bean definitions from relative location [" + location + "]",
						ele, ex);
			}
		}
		Resource[] actResArray = actualResources.toArray(new Resource[actualResources.size()]);
		getReaderContext().fireImportProcessed(location, actResArray, extractSource(ele));// TODO
																							// 不太明白
	}

	/**
	 * 处理alias标签
	 * 
	 * @param ele
	 */
	protected void processAliasRegistration(Element ele) {
		String name = ele.getAttribute(NAME_ATTRIBUTE);
		String alias = ele.getAttribute(ALIAS_ATTRIBUTE);
		boolean valid = true;
		if (!StringUtils.hasText(name)) {
			getReaderContext().error("Name must not be empty", ele);
			valid = false;
		}
		if (!StringUtils.hasText(alias)) {
			getReaderContext().error("Alias must not be empty", ele);
			valid = false;
		}
		if (valid) {
			try {
				getReaderContext().getRegistry().registerAlias(name, alias);// 注册别名
			} catch (Exception ex) {
				getReaderContext().error("Failed to register alias '" + alias + "' for bean with name '" + name + "'",
						ele, ex);
			}
			getReaderContext().fireAliasRegistered(name, alias, extractSource(ele));
		}
	}

	/**
	 * 处理bean标签
	 * 
	 * @param ele
	 */
	protected void processBeanDefinition(Element ele, BeanDefinitionParserDelegate delegate) {
		BeanDefinitionHolder bdHolder = delegate.parseBeanDefinitionElement(ele);
		if (bdHolder != null) {
			bdHolder = delegate.decorateBeanDefinitionIfRequired(ele, bdHolder);
		}
		try {
			BeanDefinitionReaderUtils.registerBeanDefinition(bdHolder, getReaderContext().getRegistry());
		} catch (BeanDefinitionStoreException ex) {
			getReaderContext().error("Failed to register bean definition with name '" + bdHolder.getBeanName() + "'",
					ele, ex);
		}

		// getReaderContext().fireComponentRegistered(new
		// BeanComponentDefinition(bdHolder));
		// TODO 作用？？？
	}

	/**
	 * 这两个方法允许使用者使用自定义的xml标签,分别在解析默认标签之前和之后解析
	 * 
	 * @param root
	 */
	protected void preProcessXml(Element root) {
	}

	protected void postProcessXml(Element root) {
	}

}
