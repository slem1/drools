package fr.sle;

import java.io.Serializable;
import java.util.Objects;

public class Reading implements Serializable {

	private Sensor sensor;

	private int temperature;

	public Reading(Sensor sensor, int temperature) {
		this.sensor = sensor;
		this.temperature = temperature;
	}

	public Reading(Sensor sensor) {
		this.sensor = sensor;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Reading reading = (Reading) o;
		return temperature == reading.temperature && Objects.equals(sensor, reading.sensor);
	}

	@Override
	public int hashCode() {
		return Objects.hash(sensor, temperature);
	}

	@Override
	public String toString() {
		return "Reading{" + "sensor=" + sensor + ", temperature=" + temperature + '}';
	}
}
