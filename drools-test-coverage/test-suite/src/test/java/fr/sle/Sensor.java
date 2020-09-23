package fr.sle;

import java.io.Serializable;
import java.util.Objects;

public class Sensor implements Serializable {

	private String ref;

	public Sensor(String ref) {
		this.ref = ref;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Sensor sensor = (Sensor) o;
		return Objects.equals(ref, sensor.ref);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ref);
	}

	@Override
	public String toString() {
		return "Sensor{" + "ref='" + ref + '\'' + '}';
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}
}
