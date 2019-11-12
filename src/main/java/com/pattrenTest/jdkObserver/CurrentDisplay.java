package com.pattrenTest.jdkObserver;

import java.util.Observable;
import java.util.Observer;

import com.pattrenTest.observer.DisplayElement;

public class CurrentDisplay implements Observer, DisplayElement {
	
	Observable observable;
	
    private float temperature;
	
	private float humidity;
	
	

	public CurrentDisplay(Observable observable) {
		this.observable = observable;
		observable.addObserver(this);
	}

	@Override
	public void display() {

		System.out.println("temperature:"+temperature+",humidity"+humidity);
	}

	@Override
	public void update(Observable observable, Object obj) {

		if(observable instanceof WeatherData) {
			WeatherData weatherData = (WeatherData) observable;
			this.temperature = weatherData.getTemperature();
			this.humidity = weatherData.getHumidity();
			display();
		}
	}

}
