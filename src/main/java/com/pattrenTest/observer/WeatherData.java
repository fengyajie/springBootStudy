package com.pattrenTest.observer;

import java.util.ArrayList;
import java.util.List;

public class WeatherData implements Subject {
	
	private List<Observer> observers;
	
	private float temperature;
	
	private float humidity;
	
	private float pressure;
	
	public WeatherData() {
		observers = new ArrayList<>();
	}

	@Override
	public void registerObserver(Observer observer) {

		observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {

		int i = observers.indexOf(observer);
		if(i>=0) {
			observers.remove(observer);
		}
	}

	@Override
	public void notifyObservers() {

		if(observers.size() == 0) {
			return;
		}
		
		for(Observer observer:observers) {
			observer.update(temperature, humidity, pressure);
		}
	}
	
	public void measurementsChanged() {
		notifyObservers();
	}
	
	public void setMeasurement(float tempereature,float humidity,float pressure) {
		this.humidity = humidity;
		this.temperature = temperature;
		this.pressure = pressure;
		measurementsChanged();
		
	}

}
