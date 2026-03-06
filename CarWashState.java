package labb6;

import java.util.LinkedList;

public class CarWashState extends SimState {

	private int fastFree, slowFree;
	private int rejected = 0;
	private final int maxQueueSize;
	private final UniformRandomStream fastRandom, slowRandom;
	private double idleTime = 0;
	private double queueTime = 0;
	private final LinkedList<Integer> carQueue = new LinkedList<>();

	public CarWashState(EventQueue queue, CarFactory factory, int numFast, int numSlow, int maxQueueSize,
			UniformRandomStream fastWashTime, UniformRandomStream slowWashTime, double maxRunTime) {
		super(queue, maxRunTime);

		this.maxQueueSize = maxQueueSize;
		fastRandom = fastWashTime;
		slowRandom = slowWashTime;

		factory.spawnCars(queue);
	}

	/**
	 * Updates idle and queue time and triggers super.eventComplete(double)
	 */
	public void eventComplete(double time) {
		if (carQueue.size() > 0) {
			addQueueTime(time - getTime());
		}

		if (fastFree > 0 || slowFree > 0) {
			addIdleTime(time - getTime());
		}

		super.eventComplete(time);
	}

	private double getFastTime() {
		return fastRandom.next();
	}

	private double getSlowTime() {
		return slowRandom.next();
	}

	/**
	 * Creates a leave event for next car in queue if space exists in a washer
	 * 
	 * @return false if washers where full
	 */
	private boolean createLeave() {

		double time;
		WashType type;

		if (getFastFree() > 0) {
			time = getFastTime();
			type = WashType.FAST;
		} else if (getSlowFree() > 0) {
			time = getSlowTime();
			type = WashType.SLOW;
		} else {
			return false;
		}

		int id = carQueue.poll();
		reduceFree(type);

		CarLeaveEvent leave = new CarLeaveEvent(time, id, type);
		getEventQueue().add(leave);
		return true;
	}

	/**
	 * Washes a car if there's any free washers else adds a car with id to carQueue
	 * if there is space. Triggered by {@link CarArriveEvent}.
	 * 
	 * @param id The cars id
	 */
	public void carArrived(int id) {
		// try adding car to wash directly, if successful don't add to queue
		if (createLeave()) {
			return;
		}

		if (getCarQueueSize() >= maxQueueSize) {
			addRejected();
			return;
		}
		carQueue.add(id);
	}

	/**
	 * Triggered by CarLeaveEvent to mark wash as free for next car. Starts washing
	 * car if one is cueing.
	 * 
	 * @param type Type of the wash (FAST/SLOW)
	 */
	public void addFree(WashType type) {
		if (type == WashType.FAST) {
			fastFree++;
		} else if (type == WashType.SLOW) {
			slowFree--;
		}

		if (carQueue.size() > 0) {
			createLeave();
		}
	}

	private void reduceFree(WashType type) {
		if (type == WashType.FAST) {
			fastFree--;
		} else if (type == WashType.SLOW) {
			slowFree--;
		}
	}

	private void addIdleTime(double time) {
		idleTime += time;
	}

	private void addQueueTime(double time) {
		queueTime += time;
	}

	private void addRejected() {
		rejected++;
	}

	// Getters
	public int getCarQueueSize() {
		return carQueue.size();
	}

	public int getFastFree() {
		return fastFree;
	}

	public int getSlowFree() {
		return slowFree;
	}

	public double getIdleTime() {
		return idleTime;
	}

	public double getQueueTime() {
		return queueTime;
	}

	public int getRejected() {
		return rejected;
	}
	
	// Test main
	public static void main(String[] args) {
		
	}
}
