package com.pattrenTest.strategyPattern;

public class MallerdDuck extends Duck {

	
	
	public MallerdDuck() {
		flyBehavior = new FlyWithWings();
	}

	@Override
	public void display() {

		System.out.println("i`m really mallerdDuck");
		
	}

}
