package labb6;

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
