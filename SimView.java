package labb6;

import java.util.Formatter;
import java.util.Observable;
import java.util.Observer;

public class SimView implements Observer{
	
	private SimState simState;
	private StringBuilder sb;
	private Formatter formatter;
	
	public SimView(SimState simState) {
		
		this.simState = simState;
		
		this.simState.addObserver(this);
		
		sb = new StringBuilder("");
		formatter = new Formatter(sb);
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
		Event event = simState.getEventQueue().peek();
	
		if (event == null) {
			return;
		}
		
		sb.replace(0, sb.length(), "");
		
		if(event instanceof StartEvent) {
			sb.append("----------------------------------------\n Time    Event\n");
		}
		
		String formattedTime = String.format("%1$6s", String.format("%.2f", event.getTime()));
		String formattedEvent = String.format("%1$-10s", event);
		
		formatter.format("%s   %s", formattedTime, formattedEvent);
		
		if(event instanceof StopEvent) {
			sb.append("\n----------------------------------------");
		}
		
		System.out.println(sb.toString());
		
	}

	
}
