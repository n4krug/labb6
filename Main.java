package labb6;

import labb6.CarWash.CarFactory;
import labb6.CarWash.CarWashState;
import labb6.CarWash.CarWashView;
import labb6.Generic.EventQueue;
import labb6.Generic.Simulator;
import labb6.Generic.random.ExponentialRandomStream;
import labb6.Generic.random.UniformRandomStream;

/**
 * Simple class for testing stuff during development
 */
public class Main {
	
	public static void main(String[] args) {
		EventQueue queue = new EventQueue();

		CarFactory factory = new CarFactory(15, new ExponentialRandomStream(2, 1234));

		final int seed = 1234;
		
		CarWashState state = new CarWashState(queue, factory, 2, 2, 5, new UniformRandomStream(2.8, 4.6, seed),
				new UniformRandomStream(3.5, 6.7, seed), 15.0);

		CarWashView view = new CarWashView(seed, factory);

		Simulator test = new Simulator(state, view);

		test.run();
	}

}
