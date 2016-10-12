package com.simplespring.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础bean的定义
 * 
 * @author yizhi.zb
 *
 */
public class BeanDefinition {
	/** Bean唯一标识 */
	private String id;
	/** 类名 */
	private String className;
	/** 属性列表 */
	private List<Property> properties = new ArrayList<Property>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	public void addProperty(Property property) {
		this.properties.add(property);
	}

	@Override
	public String toString() {
		return "BeanDefinition [id=" + id + ", className=" + className + ", properties=" + properties + "]";
	}

}
