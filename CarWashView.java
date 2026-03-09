package labb6;

import java.util.Formatter;
import java.util.Observable;

public class CarWashView extends SimView {

	private final int seed;
	private final CarFactory carFactory;
	
	private int lastCar;

	/**
	 * Crates a view that monitors and logs events and status for Car Wash simulation
	 * 
	 * @param carState		{@link CarWashState} for the viewer to observe
	 * @param seed			the seed used in random streams
	 * @param carFactory	{@link CarFactory} used to get lambda for output
	 */
	public CarWashView(int seed, CarFactory carFactory) {
		super();

		this.seed = seed;
		this.carFactory = carFactory;
	}

	@Override
	public void update(Observable o, Object arg) {

		if (! (o instanceof CarWashState)) {
			return;
		}
		CarWashState carState = (CarWashState) o;
		
		Event event = carState.getEventQueue().peek();
		StringBuilder sb = super.getStringBuilder();
		Formatter formatter = super.getFormatter();
		if (event == null) {
			return;
		}

		sb.replace(0, sb.length(), "");

		String formattedTime = String.format("%.2f", event.getTime());
		String formattedEvent = event.toString();
		String formattedId = "";
		String formattedFast = "";
		String formattedSlow = "";
		String formattedIdleTime = "";
		String formattedQueueTime = "";
		String formattedQueueSize = "";
		String formattedRejected = "";

		if (event instanceof StartEvent) {
			printStartSummary(carState);
			sb.append("----------------------------------------\n");
			formatter.format("%1$10s %2$10s %3$10s %4$10s %5$10s %6$10s %7$10s %8$10s %9$10s\n", "Time", "Event", "Id",
					"Fast", "Slow", "IdleTime", "QueueTime", "QueueTime", "QueueSize", "Rejected");
		} else {
			if (event instanceof CarEvent carEvent) {
				formattedId = String.format("%d", carEvent.getId());
				lastCar = carEvent.getId();
			}

			formattedFast = String.format("%d", carState.getFastFree());
			formattedSlow = String.format("%d", carState.getSlowFree());
			formattedIdleTime = String.format("%.2f", carState.getIdleTime());
			formattedQueueTime = String.format("%.2f", carState.getQueueTime());
			formattedQueueSize = String.format("%d", carState.getCarQueueSize());
			formattedRejected = String.format("%d", carState.getRejected());

		}

		formatter.format("%1$10s %2$10s %3$10s %4$10s %5$10s %6$10s %7$10s %8$10s %9$10s", formattedTime, formattedEvent, formattedId,
				formattedFast, formattedSlow, formattedIdleTime, formattedQueueTime, formattedQueueSize,
				formattedRejected);

		if (event instanceof StopEvent) {
			sb.append("\n----------------------------------------\n");
			printEndSummary(carState);
		}

		System.out.println(sb.toString());
	}

	private void printStartSummary(CarWashState state) {
		StringBuilder sb = super.getStringBuilder();
		Formatter formatter = super.getFormatter();

		formatter.format(
				"Fast machines: %1$d\n" + "Slow machines: %2$d\n" + "Fast distribution: %3$s\n"
						+ "Slow distribution: %4$s\n" + "Exponential distribution with lambda = %5$s\n"
						+ "seed = %6$d\n" + "Max Queue size: %7$d\n",
				state.getFastFree(), state.getSlowFree(), state.getFastDistribution(), state.getSlowDistribution(),
				carFactory.getLambda(), seed, state.getMaxQueueSize());
	}

	private void printEndSummary(CarWashState state) {
		StringBuilder sb = super.getStringBuilder();
		Formatter formatter = super.getFormatter();

		formatter.format(
				"Total idle machine time: %1$.2f\n" + "Total queueing time: %2$.2f\n" + "Mean queueing time: %3$.2f\n"
						+ "Rejected cars: %4$d\n",
				state.getIdleTime(), state.getQueueTime(), state.getQueueTime() / (lastCar + 1 - state.getRejected()),
				state.getRejected());

	}

}
