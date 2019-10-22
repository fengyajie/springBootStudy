package com.strategyPattern;

public class MallerdDuckTest {

	public static void main(String[] args) {

		Duck duck = new MallerdDuck();
		duck.performFly();
		duck.setFlyBehavior(new FlyNoWay());
		duck.performFly();
	}

}
