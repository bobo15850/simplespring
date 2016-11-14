package zb.springframework.beans.factory.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.Assert;
import org.springframework.util.xml.SimpleSaxErrorHandler;
import org.springframework.util.xml.XmlValidationModeDetector;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;

import zb.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import zb.springframework.beans.factory.support.BeanDefinitionRegistry;
import zb.springframework.core.NamedThreadLocal;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

	public static final int VALIDATION_AUTO = XmlValidationModeDetector.VALIDATION_AUTO;

	private boolean namespaceAware = false;

	private DocumentLoader documentLoader = new DefaultDocumentLoader();

	private EntityResolver entityResolver;

	private ErrorHandler errorHandler = new SimpleSaxErrorHandler(logger);

	// 用来记录当前正在用于加载的资源文件，防止bean定义文件中循环import
	private final ThreadLocal<Set<EncodedResource>> resourcesCurrentlyBeingLoaded = new NamedThreadLocal<Set<EncodedResource>>(
			"XML bean definition resources currently being loaded");

	public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
		super(registry);
	}

	protected EntityResolver getEntityResolver() {
		// TODO
		return null;
	}

	public boolean isNamespaceAware() {
		return namespaceAware;
	}

	public void setNamespaceAware(boolean namespaceAware) {
		this.namespaceAware = namespaceAware;
	}

	public int loadBeanDefinitions(Resource resource) throws BeanDefinitionStoreException {
		return loadBeanDefinitions(new EncodedResource(resource));
	}

	/**
	 * 该过程针对特定的的bean配置文件进行处理，考虑了循环import的情况
	 * 
	 * @param encodedResource
	 * @return
	 * @throws BeanDefinitionStoreException
	 */
	public int loadBeanDefinitions(EncodedResource encodedResource) throws BeanDefinitionStoreException {
		Assert.notNull(encodedResource, "EncodedResource must not be null");
		if (logger.isInfoEnabled()) {
			logger.info("Loading XML bean definitions from " + encodedResource.getResource());
		}

		Set<EncodedResource> currentResources = this.resourcesCurrentlyBeingLoaded.get();
		if (currentResources == null) {
			currentResources = new HashSet<EncodedResource>(4);
			this.resourcesCurrentlyBeingLoaded.set(currentResources);
		}
		if (!currentResources.add(encodedResource)) {// 已经存在该resource
			throw new BeanDefinitionStoreException(
					"Detected cyclic loading of " + encodedResource + " - check your import definitions!");
		}
		try {
			InputStream inputStream = encodedResource.getResource().getInputStream();
			try {
				InputSource inputSource = new InputSource(inputStream);
				if (encodedResource.getEncoding() != null) {
					inputSource.setEncoding(encodedResource.getEncoding());
				}
				return doLoadBeanDefinitions(inputSource, encodedResource.getResource());
			} finally {
				inputStream.close();
			}
		} catch (IOException ex) {
			throw new BeanDefinitionStoreException(
					"IOException parsing XML document from " + encodedResource.getResource(), ex);
		} finally {
			currentResources.remove(encodedResource);
			if (currentResources.isEmpty()) {
				this.resourcesCurrentlyBeingLoaded.remove();
			}
		}
	}

	protected int doLoadBeanDefinitions(InputSource inputSource, Resource resource)
			throws BeanDefinitionStoreException {
		int validationMode = getValidationModeForResource(resource);
		try {
			Document doc = this.documentLoader.loadDocument(inputSource, getEntityResolver(), errorHandler,
					validationMode, isNamespaceAware());
			return this.registerBeanDefinitions(doc, resource);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;

	}

	/**
	 * 
	 * @param doc
	 * @param resource
	 * @return 表示从该doc中解析和注册的bean数量
	 * @throws BeanDefinitionStoreException
	 */
	public int registerBeanDefinitions(Document doc, Resource resource) throws BeanDefinitionStoreException {
		BeanDefinitionDocumentReader documentReader = this.createBeanDefinitionDocumentReader();
		// TODO 计算数量
		documentReader.registerBeanDefinitions(doc, this.createReaderContext(resource));
		return 0;
	}

	protected BeanDefinitionDocumentReader createBeanDefinitionDocumentReader() {
		return null;
	}

	protected XmlReaderContext createReaderContext(Resource resource) {
		// TODO
		return null;
	}

	protected int getValidationModeForResource(Resource resource) {
		// TODO
		return 0;
	}
}
