package assignment13;

import java.util.LinkedList;

/**
 * Class represents an Airport. Thought of as a node on the NetworkGraph.
 * @author Nickolas Lee (U0860626)
 * @author Thomas Osimitz (U0970671)
 *
 */
public class Airport implements Comparable<Airport> {

	private LinkedList<Flight> departFlights;// contains the list of departure flight objects
	private Airport cameFrom;
	private String name;
	private boolean visited;
	private double totalWeight;

	public Airport(String name) {
		this.name = name;
		this.cameFrom = null;
		this.departFlights = new LinkedList<Flight>();
		this.visited = false;
		this.totalWeight = Integer.MAX_VALUE;
	}

	/**
	 * @return the cameFrom
	 */
	public Airport getCameFrom() {
		return cameFrom;
	}

	/**
	 * @param cameFrom the cameFrom to set
	 */
	public void setCameFrom(Airport cameFrom) {
		this.cameFrom = cameFrom;
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

	/**
	 * Finds if there are any routes leaving this airport with same destination
	 * @param destination - the destination of the desired flight
	 * @return the flight with this destination, null if not
	 */
	public Flight getRoute(Airport destination) {
		for(Flight currFlight : departFlights) {
			if(currFlight.getDestination().equals(destination)) {
				return currFlight;
			}
		}
		return null;
	}

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

	@Override
	public int compareTo(Airport otherAirport) {
		if(!(otherAirport instanceof Airport)) {
			throw new IllegalArgumentException();
		}
		return (int) (this.totalWeight - (otherAirport).getTotalWeight());
	}
}
