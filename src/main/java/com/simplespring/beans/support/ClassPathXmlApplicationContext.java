package com.simplespring.beans.support;

import static com.simplespring.beans.support.Constants.BEAN;
import static com.simplespring.beans.support.Constants.BEAN_CLASS;
import static com.simplespring.beans.support.Constants.BEAN_ID;
import static com.simplespring.beans.support.Constants.PROPERTY;
import static com.simplespring.beans.support.Constants.PROPERTY_NAME;
import static com.simplespring.beans.support.Constants.PROPERTY_REF;
import static com.simplespring.beans.support.Constants.PROPERTY_VALUE;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
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
import com.simplespring.test.Dog;
import com.simplespring.test.Student;

/**
 * 类路径下的应用上下文
 * 
 * @author yizhi.zb
 *
 */
public class ClassPathXmlApplicationContext implements BeanFactory {
	/** 存储Bean元信息，即为从xml中直接解析得到的信息 */
	private ConcurrentMap<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<String, BeanDefinition>();
	/** 存储依赖注入后的Bean实例 */
	private ConcurrentMap<String, Object> beanObject = new ConcurrentHashMap<String, Object>();
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
			beanDefinitions.putIfAbsent(bean.getId(), bean);
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

	/**
	 * 获取依赖注入之后的bean实例，依赖注入过程发生在获取实例时
	 */
	public Object getBean(String name) {
		if (beanObject.containsKey(name)) {
			return beanObject.get(name);
		}
		BeanDefinition definition = beanDefinitions.get(name);
		if (definition == null) {
			throw new IllegalArgumentException("bean " + name + " doesn't exist");
		}
		String className = definition.getClassName();
		try {
			Class<?> clazz = Class.forName(className);
			Object beanIns = clazz.newInstance();
			for (int i = 0; i < definition.getProperties().size(); i++) {
				Property property = definition.getProperties().get(i);
				Field field = clazz.getDeclaredField(property.getName());
				field.setAccessible(true);
				Class<?> fieldType = field.getType();
				if (property.getValue() != null && property.getValue().length() > 0) {// 基本类型，目前支持int和String
					if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
						int value = Integer.parseInt(property.getValue());
						field.set(beanIns, value);
					} else if (fieldType.equals(String.class)) {
						field.set(beanIns, property.getValue());
					} else {
						throw new IllegalArgumentException(
								String.format("doesn't support such type %s", fieldType.toString()));
					}
				} else if (property.getRef() != null && property.getRef().length() > 0) {// 引用类型
					Object propertyIns = this.getBean(property.getRef());
					field.set(beanIns, propertyIns);
				} else {
					throw new IllegalArgumentException(String.format("beanDefinition %s is wrong at property %s",
							definition.getId(), property.toString()));
				}
			}
			beanObject.putIfAbsent(definition.getId(), beanIns);
			return beanIns;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static void main(String[] args) throws Throwable {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("applicationContext.xml");
		Student stu = (Student) beanFactory.getBean("xiaoming");
		Dog dog = (Dog) beanFactory.getBean("wangcai");
		System.out.println(stu);
		System.out.println(dog);
	}
}
