package labb6.CarWash.event;

import labb6.CarWash.CarWashState;
import labb6.Generic.SimState;

/**
 * Event run when 
 */
public class CarLeaveEvent extends CarEvent {

	final private CarWashState.WashType type;
	
	public CarLeaveEvent(double time, int id, CarWashState.WashType type) {
		super(time, id);
		this.type = type;
	}

	@Override
	protected void runEvent(SimState state) {
		if (state instanceof CarWashState washState) {
			washState.carLeft(type, getTime());
		}
	}

	@Override
	public String toString() {
		return "Leave";
	}
}
