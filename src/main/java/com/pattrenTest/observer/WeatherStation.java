package com.pattrenTest.observer;


/**
 * 观察者模式
 * @author fyj
 *
 */
public class WeatherStation {

	public static void main(String[] args) {

		WeatherData weatherData = new WeatherData();
		CurrentConditionDisplay currentConditionDisplay = new CurrentConditionDisplay(weatherData);
		
		weatherData.setMeasurement(10,20,30);
	}

}
