package com.pattrenTest.jdkObserver;

import java.util.Observable;

public class WeatherData extends Observable {
	
	private float temperature;
	
	private float humidity;
	
	private float pressure;

	public WeatherData() {
		
	}
	
	public void meatherChange() {
		setChanged();
		notifyObservers();
	}
	
	public void setMeather(float temperature,float humidity,float pressure) {
		this.temperature=temperature;
		this.humidity=humidity;
		this.pressure=pressure;
		meatherChange();
	}

	public float getTemperature() {
		return temperature;
	}

	public float getHumidity() {
		return humidity;
	}

	public float getPressure() {
		return pressure;
	}
}
