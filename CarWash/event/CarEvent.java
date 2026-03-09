package labb6.CarWash.event;

import labb6.CarWash.CarWashView;
import labb6.Generic.event.Event;

/**
 * Wrapper around CarEvents to simplify {@link CarWashView}
 */
public abstract class CarEvent extends Event {

	private final int id;
	
	public CarEvent(double time, int id) {
		super(time);
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
