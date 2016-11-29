package assignment13;

import java.util.LinkedList;

/**
 * A node on the graph
 * @author Nickolas Lee
 *
 */
public class Airport {
	
	private LinkedList<Flight> departFlights;// contains the list of departure flight objects
	private boolean visited;
	private double totalWeight;
	private String name;
	
	public Airport(String name) {
		this.name = name;
		this.departFlights = new LinkedList<Flight>();
		this.visited = false;
		this.totalWeight = Integer.MAX_VALUE;
	}
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param departFlights the departFlights to set
	 */
	public void addDepartFlight(Flight route) {
		this.departFlights.add(route);
	}


	/**
	 * @return the visited
	 */
	public boolean isVisited() {
		return visited;
	}
	/**
	 * @param visited the visited to set
	 */
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	/**
	 * @return the totalWeight
	 */
	public double getTotalWeight() {
		return totalWeight;
	}
	/**
	 * @param totalWeight the totalWeight to set
	 */
	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}
	/**
	 * @return the departFlights
	 */
	public LinkedList<Flight> getDepartFlights() {
		return departFlights;
	}
	 // TODO need to setDepartFlights? 
	
	/**
	 * Finds if there are any routes leaving this airport with same destination
	 * @param destination - the destination of the desired flight
	 * @return the flight with this destination, null if not
	 */
	public Flight getRoute(String destination) {
		for(Flight currFlight : departFlights) {
			if(currFlight.getDestination().getName().equals(destination)) {
				return currFlight;
			}
		}
		return null;
	}
	
}
