package labb6;

import java.util.Formatter;
import java.util.Observable;

public class CarWashView extends SimView{
	
	public CarWashView(CarWashState carState) {
		super(carState);
		
		printStartSummary();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
		Event event = super.getSimState().getEventQueue().peek();
		CarWashState carState = (CarWashState) super.getSimState();
		StringBuilder sb = super.getStringBuilder();
		Formatter formatter = super.getFormatter();
		if (event == null) {
			return;
		}
		
		sb.replace(0, sb.length(), "");
		
		String formattedTime = String.format("%1$10s", String.format("%.2f", event.getTime()));
		String formattedEvent = String.format("%1$-10s", event);
		
		if(event instanceof StartEvent) {
			sb.append("----------------------------------------\n");
			formatter.format("%1$10s %2$10s %3$10s %4$10s %5$10s %6$10s %7$10s %8$10s %9$10s", "Time", "Event", "Id", "Fast", "Slow", "IdleTime", "QueueTime", "QueueTime", "QueueSize", "Rejected");
			String formattedId = String.format("%1$10s", "");
			String formattedFast = String.format("%1$10s", "");
			String formattedSlow = String.format("%1$10s", "");
			String formattedIdleTime = String.format("%1$10s", "");
			String formattedQueueTime = String.format("%1$10s", "");
			String formattedQueueSize = String.format("%1$10s", "");
			String formattedRejected = String.format("%1$10s", "");
			
			formatter.format("%1$s %2$s %3$s %4$s %5$s %6$s %7$s %8$s %9$s", formattedTime, formattedEvent, formattedId, formattedFast, formattedSlow, formattedIdleTime, formattedQueueTime, formattedQueueSize, formattedRejected);
		}
		
		
		if(!(event instanceof StartEvent) && !(event instanceof StopEvent)) {
			
			String formattedId = String.format("%1$10s", "");
			String formattedFast = String.format("%1$10s", carState.getFastFree());
			String formattedSlow = String.format("%1$10s", carState.getSlowFree());
			String formattedIdleTime = String.format("%1$10s", carState.getIdleTime());
			String formattedQueueTime = String.format("%1$10s", carState.getQueueTime());
			String formattedQueueSize = String.format("%1$10s", carState.getCarQueueSize());
			String formattedRejected = String.format("%1$10s", carState.getRejected());
			
			formatter.format("%1$s %2$s %3$s %4$s %5$s %6$s %7$s %8$s %9$s", formattedTime, formattedEvent, formattedId, formattedFast, formattedSlow, formattedIdleTime, formattedQueueTime, formattedQueueSize, formattedRejected);
			
		}
		
		if(event instanceof StopEvent) {
			String formattedId = String.format("%1$10s", "");
			String formattedFast = String.format("%1$10s", carState.getFastFree());
			String formattedSlow = String.format("%1$10s", carState.getSlowFree());
			String formattedIdleTime = String.format("%1$10s", carState.getIdleTime());
			String formattedQueueTime = String.format("%1$10s", carState.getQueueTime());
			String formattedQueueSize = String.format("%1$10s", carState.getCarQueueSize());
			String formattedRejected = String.format("%1$10s", carState.getRejected());
			
			formatter.format("%1$s %2$s %3$s %4$s %5$s %6$s %7$s %8$s %9$s", formattedTime, formattedEvent, formattedId, formattedFast, formattedSlow, formattedIdleTime, formattedQueueTime, formattedQueueSize, formattedRejected);
			sb.append("\n----------------------------------------\n");
			printEndSummary();
		}
		
		System.out.println(sb.toString());
	}
	
	private void printStartSummary() {
		StringBuilder sb = super.getStringBuilder();
		Formatter formatter = super.getFormatter();
		CarWashState state = (CarWashState) super.getSimState();
		
		formatter.format("Fast machines: %1$d\n" + 
		"Slow machines: %2$d\n" + 
		"Fast distribution: (%3$.1f, %4$.1f)\n" + 
		"Slow distribution: (%5$.1f, %6$.1f)\n" + 
		"Exponential distribution with lambda = %7$.1f\n" + 
		"seed = %8$d\n" + 
		"Max Queue size: %9$d\n", state.getFastFree(), state.getSlowFree(), null, null, null, null, null, null);
		
		System.out.println(sb.toString());
	}
	
	private void printEndSummary() {
		StringBuilder sb = super.getStringBuilder();
		Formatter formatter = super.getFormatter();
		CarWashState state = (CarWashState) super.getSimState();
		
		int lastcar = 1;
		
		formatter.format("Total idle machine time: %1$.2f\n" + 
		"Total queueing time: %2$.2f\n" + 
		"Mean queueing time: %3$.2f\n" + 
		"Rejected cars: %4$d\n", state.getIdleTime(), state.getQueueTime(), state.getQueueTime()/(lastcar-state.getRejected()), state.getRejected());
		
	}

}
