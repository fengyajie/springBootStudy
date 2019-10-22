package com.strategyPattern;

public abstract class Duck {
	FlyBehavior flyBehavior;

	public Duck() {
	}
	
	public abstract void display();
	
	public void performFly() {
		flyBehavior.fly();
	}
	
	public void swim() {
		System.out.println("all duck float");
	}

	public void setFlyBehavior(FlyBehavior flyBehavior) {
		this.flyBehavior = flyBehavior;
	}
}
