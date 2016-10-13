package com.simplespring.test;

public class Dog {
	private String weight;
	private String color;

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Dog [weight=" + weight + ", color=" + color + "]";
	}

}
