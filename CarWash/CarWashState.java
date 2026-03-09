package labb6.CarWash;

import java.util.LinkedList;

import labb6.CarWash.event.CarArriveEvent;
import labb6.CarWash.event.CarLeaveEvent;
import labb6.Generic.EventQueue;
import labb6.Generic.SimState;
import labb6.Generic.random.UniformRandomStream;

public class CarWashState extends SimState {

	public enum WashType {
		FAST, SLOW
	}

	private int fastFree, slowFree;
	private int rejected = 0;
	private final int maxQueueSize;
	private final UniformRandomStream fastRandom, slowRandom;
	private double idleTime = 0;
	private double queueTime = 0;
	private final LinkedList<Integer> carQueue = new LinkedList<>();

	/**
	 * Creates a CarWashState
	 * 
	 * @param queue        a {@link EventQueue} for the simulation events
	 * @param factory      {@link CarFactory} to spawn cars ({@link CarArriveEvent})
	 * @param numFast      number of fast machines available
	 * @param numSlow      number of slow machines available
	 * @param maxQueueSize the maximum number of cars that can queue
	 * @param fastWashTime {@link UniformRandomStream} for distribution of fast wash
	 *                     times
	 * @param slowWashTime {@link UniformRandomStream} for distribution of slow wash
	 *                     times
	 * @param maxRunTime   maximum time to simulate (creates stop event for given
	 *                     time)
	 */
	public CarWashState(EventQueue queue, CarFactory factory, int numFast, int numSlow, int maxQueueSize,
			UniformRandomStream fastWashTime, UniformRandomStream slowWashTime, double maxRunTime) {
		super(queue, maxRunTime);

		fastFree = numFast;
		slowFree = numSlow;

		this.maxQueueSize = maxQueueSize;
		fastRandom = fastWashTime;
		slowRandom = slowWashTime;

		factory.spawnCars(queue);
	}

	/**
	 * Updates idle and queue time and triggers super.eventStarted(double)
	 * 
	 * @param time
	 */
	public void eventStarted(double time) {
		if (getCarQueueSize() > 0) {
			addQueueTime((time - getTime()) * getCarQueueSize());
		}

		if (fastFree > 0 || slowFree > 0) {
			addIdleTime((time - getTime()) * (getFastFree() + getSlowFree()));
		}

		super.eventStarted(time);
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
	 * @param time
	 * 
	 * @return false if washers where full
	 */
	private boolean enterWasher(double time) {

		double leaveTime;
		WashType type;

		if (getFastFree() > 0) {
			leaveTime = getFastTime();
			type = WashType.FAST;
		} else if (getSlowFree() > 0) {
			leaveTime = getSlowTime();
			type = WashType.SLOW;
		} else {
			return false;
		}

		leaveTime += time;

		int id = carQueue.poll();
		reduceFree(type);

		CarLeaveEvent leave = new CarLeaveEvent(leaveTime, id, type);
		getEventQueue().add(leave);
		return true;
	}

	/**
	 * Washes a car if there's any free washers else adds a car with id to carQueue
	 * if there is space. Triggered by {@link CarArriveEvent}.
	 * 
	 * @param id The cars id
	 */
	public void carArrived(int id, double time) {
		if (getCarQueueSize() >= maxQueueSize) {
			addRejected();
			return;
		}
		carQueue.add(id);

		enterWasher(time); // Try putting first car in washer (does nothing if no free washer)
	}

	/**
	 * Triggered by CarLeaveEvent to mark wash as free for next car. Starts washing
	 * car if one is cueing.
	 * 
	 * @param type Type of the wash ({@link WashType.FAST}/{@link WashType.SLOW})
	 */
	public void carLeft(WashType type, double time) {
		if (type == WashType.FAST) {
			fastFree++;
		} else if (type == WashType.SLOW) {
			slowFree++;
		}

		if (carQueue.size() > 0) {
			enterWasher(time);
		}
	}

	/**
	 * Reduces number of available washers by one based on {@link WashType}
	 * 
	 * @param type Type of the wash ({@link WashType.FAST}/{@link WashType.SLOW})
	 */
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

	/*
	 * GETTERS
	 */

	protected int getCarQueueSize() {
		return carQueue.size();
	}

	protected int getFastFree() {
		return fastFree;
	}

	protected int getSlowFree() {
		return slowFree;
	}

	protected double getIdleTime() {
		return idleTime;
	}

	protected double getQueueTime() {
		return queueTime;
	}

	protected int getRejected() {
		return rejected;
	}

	protected String getSlowDistribution() {
		return slowRandom.toString();
	}

	protected String getFastDistribution() {
		return fastRandom.toString();
	}

	protected int getMaxQueueSize() {
		return maxQueueSize;
	}
}
