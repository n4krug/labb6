package labb6;

import java.util.PriorityQueue;

public class EventQueue extends PriorityQueue<Event> {

	public String toString() {
		String str = "[";
		
		for (Event event : this) {
			str += "(" + event.getTime() + ": " + event + "), ";
		}
		str = str.substring(0, str.length() - 2) + "]";
		
		return str;
	}
}
