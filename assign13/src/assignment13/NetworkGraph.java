package assignment13;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * <p>This class represents a graph of flights and airports along with specific
 * data about those flights. It is recommended to create an airport class and a
 * flight class to represent nodes and edges respectively. There are other ways
 * to accomplish this and you are free to explore those.</p>
 * 
 * <p>Testing will be done with different criteria for the "best" path so be
 * sure to keep all information from the given file. Also, before implementing
 * this class (or any other) draw a diagram of how each class will work in
 * relation to the others. Creating such a diagram will help avoid headache and
 * confusion later.</p>
 * 
 * <p>Be aware also that you are free to add any member variables or methods
 * needed to completed the task at hand</p>
 * 
 * @author CS2420 Teaching Staff - Spring 2016
 */
public class NetworkGraph {

	HashMap<String, Airport> airportSet;
	Airport origin;
	Airport destination;

	/**
	 * <p>Constructs a NetworkGraph object and populates it with the information
	 * contained in the given file. See the sample files or a randomly generated
	 * one for the proper file format.</p>
	 * 
	 * <p>You will notice that in the given files there are duplicate flights with
	 * some differing information. That information should be averaged and stored
	 * properly. For example some times flights are canceled and that is
	 * represented with a 1 if it is and a 0 if it is not. When several of the same
	 * flights are reported totals should be added up and then reported as an
	 * average or a probability (value between 0-1 inclusive).</p>
	 * 
	 * @param flightInfoPath - The path to the file containing flight data. This
	 * should be a *.csv(comma separated value) file
	 * 
	 * @throws FileNotFoundException The only exception that can be thrown in
	 * this assignment and only if a file is not found.
	 */
	public NetworkGraph(String flightInfoPath) throws FileNotFoundException {
		//TODO: Implement a constructor that reads in the file and stores the information
		// 		appropriately in this object.
		BufferedReader br = new BufferedReader(new FileReader(flightInfoPath));
		double cost, time, delay, distance, fractionCanceled;
		String currentLine, originName, destinationName, carrier;
		String[] currentData;
		airportSet = new HashMap<String, Airport>();

		try {
			currentLine = br.readLine(); //read in and ignore header
			while((currentLine = br.readLine()) != null) {
				currentData = currentLine.split(",");
				originName = currentData[0];
				destinationName = currentData[1];
				carrier = currentData[2];
				delay = Double.parseDouble(currentData[3]);
				fractionCanceled = Double.parseDouble(currentData[4]);
				time = Double.parseDouble(currentData[5]);
				distance = Double.parseDouble(currentData[6]);
				cost = Double.parseDouble(currentData[7]);

				if(!airportSet.containsKey(originName)) {
					Airport newOrigin = new Airport(originName);
					airportSet.put(originName, newOrigin);
				}

				if(!airportSet.containsKey(destinationName)) {
					Airport newDestination = new Airport(destinationName);
					airportSet.put(destinationName, newDestination);
				}

				this.origin = airportSet.get(originName);
				this.destination = airportSet.get(destinationName);

				//TODO: IF RUNTIME RIDICULOUS, POSSIBLE TIME SAVER TO PUT HERE
				Flight preExistingFlight = origin.getRoute(this.destination);
				if(preExistingFlight != null) {
					preExistingFlight.addFlight(cost, time, delay, distance, fractionCanceled, carrier);
				}
				else {
					Flight route = new Flight(origin, destination, cost, time, delay, distance, fractionCanceled, carrier);
					origin.addDepartFlight(route);
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method returns a BestPath object containing information about the best
	 * way to fly to the destination from the origin. "Best" is defined by the
	 * FlightCriteria parameter <code>enum</code>. This method should throw no
	 * exceptions and simply return a BestPath object with information dictating
	 * the result. If a destination or origin is not contained in
	 * this instance of NetworkGraph simply return a BestPath object with no path (not a
	 * <code>null</code> path). If origin or destination are <code>null</code>, also
	 * return a BestPath object with no path.
	 * 
	 * @param origin - The starting location to find a path from. This should be a
	 * 3 character long string denoting an airport.
	 * 
	 * @param destination - The destination location from the starting airport.
	 * Again, this should be a 3 character long string denoting an airport.
	 * 
	 * @param criteria - This enum dictates the definition of "best". Based on this
	 * value a path should be generated and return.
	 * 
	 * @return - An object containing path information including origin, destination,
	 * and everything in between.
	 */
	public BestPath getBestPath(String origin, String destination, FlightCriteria criteria) {
		//TODO: First figure out what kind of path you need to get (HINT: Use a switch!) then
		//		Search for the shortest path using Dijkstra's algorithm.
		BestPath answer = new BestPath();
		answer.path = new ArrayList<String>();

		if(origin.length() == 3 && destination.length() == 3 && criteria != null){
			this.origin = airportSet.get(origin);
			this.destination = airportSet.get(destination);

			answer = DSearch(null, criteria);
		}
		return answer;
		// create the BestPath object from DSearch helper method

	}

	/**
	 * <p>This overloaded method should do the same as the one above only when looking for paths
	 * skip the ones that don't match the given airliner.</p>
	 * 
	 * @param origin - The starting location to find a path from. This should be a
	 * 3 character long string denoting an airport.
	 * 
	 * @param destination - The destination location from the starting airport.
	 * Again, this should be a 3 character long string denoting an airport.
	 * 
	 * @param criteria - This enum dictates the definition of "best". Based on this
	 * value a path should be generated and return.
	 * 
	 * @param airliner - a string dictating the airliner the user wants to use exclusively. Meaning
	 * no flights from other airliners will be considered.
	 * 
	 * @return - An object containing path information including origin, destination,
	 * and everything in between.
	 */
	public BestPath getBestPath(String origin, String destination, FlightCriteria criteria, String airliner) {
		//Find origin and destination airport objects and set equal to global memebers
		//TODO: call other getBestPath and set airliner to null
		this.origin = airportSet.get(origin);
		this.destination = airportSet.get(destination);

		return DSearch(airliner, criteria);
	}

	private BestPath DSearch(String carrier, FlightCriteria criteria){
		// check if flight contains the carrier
		BestPath bestPath = new BestPath();
		bestPath.path = new ArrayList<String>();
		PriorityQueue<Airport> pq = new PriorityQueue<Airport>();
		pq.add(origin);

		while(!pq.isEmpty()){
			Airport currentAir = pq.poll();	

			if(this.destination.equals(currentAir)){
				ArrayList<String> temp = new ArrayList<String>();
				bestPath.pathLength = currentAir.getTotalWeight();

				while(currentAir.getCameFrom() != null) {
					temp.add(currentAir.getName());
					currentAir = currentAir.getCameFrom();
				}
				temp.add(currentAir.getName());

				for(int current = temp.size() - 1; current >= 0; current--) {
					String airCode = temp.get(current);
					bestPath.path.add(airCode);
				}

				return bestPath;
			}

			currentAir.setVisited(true);

			for(Flight nextFlight : currentAir.getDepartFlights()){
				Airport neighbor = nextFlight.getDestination();
				if((carrier == null) || (carrier != null && nextFlight.isCarrier(carrier))) {
					if(!neighbor.isVisited()){
						if(neighbor.getTotalWeight() > currentAir.getTotalWeight() + nextFlight.getWeight(criteria)){
							neighbor.setCameFrom(currentAir);
							neighbor.setTotalWeight(currentAir.getTotalWeight() + nextFlight.getWeight(criteria));
							pq.add(neighbor);
						}
					}
				}
			}
		}

		return bestPath;
	}

	/**
	 * Generates a DOT file for visualizing the binary heap.
	 */
	public void generateDotFile(String filename) {
		try(PrintWriter out = new PrintWriter(filename)) {
			out.println("digraph Heap {\n\tnode [shape=record]\n");

			for(Airport airp : airportSet.values()) {
				out.println("\tnode" + airp.getName() + " [label = \"<f0> |<f1> " + airp.getName() + "|<f2> \"]");
				for(Flight flight : airp.getDepartFlights()) {
					out.println("\tnode" + airp.getName() + ":f0 -> node" + flight.getDestination().getName() + ":f1");
				}
			}
			out.println("}");
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
