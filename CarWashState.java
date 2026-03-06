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

	private void createLeave(int id) {

		double time;
		WashType type;

		if (getFastFree() > 0) {
			time = getFastTime();
			type = WashType.FAST;
		} else if (getSlowFree() > 0) {
			time = getSlowTime();
			type = WashType.SLOW;
		} else {
			return;
		}
		
		reduceFree(type);

		CarLeaveEvent leave = new CarLeaveEvent(time, id, type);
		getEventQueue().add(leave);
	}

	/**
	 * Adds a car with id to carQueue if there is space
	 * 
	 * @param id The cars id
	 * @return Returns false if queue was full (> maxQueueSize)
	 */
	private boolean addToCarQueue(int id) {
		if (getCarQueueSize() >= maxQueueSize) {
			addRejected();
			return false;
		}
		carQueue.add(id);
		return true;
	}

	public int getFastFree() {
		return fastFree;
	}

	public void addFree(WashType type) {
		if (type == WashType.FAST) {
			fastFree++;
		} else if(type == WashType.SLOW) {
			slowFree--;
		}
		
		if (carQueue.size() > 0) {
			createLeave(carQueue.poll());
		}
	}

	private void reduceFree(WashType type) {
		if (type == WashType.FAST) {
			fastFree--;
		} else if (type == WashType.SLOW) {
			slowFree--;
		}
	}

	public int getSlowFree() {
		return slowFree;
	}

	public double getIdleTime() {
		return idleTime;
	}

	private void addIdleTime(double time) {
		idleTime += time;
	}

	public double getQueueTime() {
		return queueTime;
	}

	private void addQueueTime(double time) {
		queueTime += time;
	}

	public int getRejected() {
		return rejected;
	}

	private void addRejected() {
		rejected++;
	}

	public int getCarQueueSize() {
		return carQueue.size();
	}
}
