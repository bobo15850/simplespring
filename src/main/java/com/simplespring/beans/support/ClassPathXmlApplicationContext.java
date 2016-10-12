package com.simplespring.beans.support;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.simplespring.beans.BeanDefinition;
import com.simplespring.beans.Property;
import com.simplespring.beans.factory.BeanFactory;
import static com.simplespring.beans.support.Constants.*;

/**
 * 类路径下的应用上下文
 * 
 * @author yizhi.zb
 *
 */
public class ClassPathXmlApplicationContext implements BeanFactory {
	private ConcurrentMap<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<String, BeanDefinition>();
	private String filename;

	public ClassPathXmlApplicationContext(String filename) {
		this.filename = filename;
		try {
			this.loadBeanDefinition();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		this.print();
	}

	/**
	 * 从Xml中加载bean定义 文件加载异常何时抛出??? TODO
	 * 
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	private void loadBeanDefinition() throws IOException, ParserConfigurationException, SAXException {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document dom = db.parse(is);
		Element root = dom.getDocumentElement();
		NodeList beans = root.getElementsByTagName(BEAN);
		for (int i = 0; i < beans.getLength(); i++) {
			Element ele = (Element) beans.item(i);
			BeanDefinition bean = parseBean(ele);
			beanDefinitions.putIfAbsent(bean.getClassName(), bean);
		}
	}

	/**
	 * 处理一个bean定义
	 * 
	 * @return
	 */
	private BeanDefinition parseBean(Element ele) {
		String id = ele.getAttribute(BEAN_ID);
		String className = ele.getAttribute(BEAN_CLASS);
		BeanDefinition bean = new BeanDefinition();
		bean.setId(id);
		bean.setClassName(className);
		NodeList properties = ele.getElementsByTagName(PROPERTY);
		for (int i = 0; i < properties.getLength(); i++) {
			Element prop = (Element) properties.item(i);
			Property property = new Property();
			property.setName(prop.getAttribute(PROPERTY_NAME));
			property.setValue(prop.getAttribute(PROPERTY_VALUE));
			property.setRef(prop.getAttribute(PROPERTY_REF));
			bean.addProperty(property);
		}
		return bean;
	}

	private void print() {
		System.out.println(beanDefinitions);
	}

	@Override
	public Object getBean(String name) {
		return null;
	}

	public static void main(String[] args) {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
}
