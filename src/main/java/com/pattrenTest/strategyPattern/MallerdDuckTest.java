package com.pattrenTest.strategyPattern;

/**
 * 策略模式
 * @author fyj
 *
 */
public class MallerdDuckTest {

	public static void main(String[] args) {

		Duck duck = new MallerdDuck();
		duck.performFly();
		duck.setFlyBehavior(new FlyNoWay());
		duck.performFly();
	}

}
