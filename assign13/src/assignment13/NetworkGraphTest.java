package assignment13;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

public class NetworkGraphTest {
	NetworkGraph fiveUniqueRoutes;
	NetworkGraph tenDuplicateFlights;
	@Before
	public void setUp() throws Exception {
		fiveUniqueRoutes = new NetworkGraph("fiveOfBoth.csv");
		tenDuplicateFlights = new NetworkGraph("tenDuplicateFlights.csv");
		
	}

	@Test
	public void testFiveUniqueRoutes() {
		assertEquals(5, fiveUniqueRoutes.airportSet.size());
		assertTrue(fiveUniqueRoutes.airportSet.containsKey("MZF"));
	}
	
	@Test
	public void testTenDuplicateRoutes() {
		String actual = tenDuplicateFlights.airportSet.get("MZF").getRoute("AHH").toString();
		String expected = "Flight [origin=MZF, destination=AHH, cost=87.5, time=400.0, delay=50.5, distance=37.5, fractionCanceled=0.5, count=2, carriers=[AA, OO]]";
		assertEquals(5, tenDuplicateFlights.airportSet.size());
		tenDuplicateFlights.generateDotFile("tenFlightDot.dot");
		assertEquals(expected, actual);
	}
	
	@Test
	public void testDOT() throws FileNotFoundException{
		String input = "Flight50_50";
		NetworkGraph newFlight = new NetworkGraph(input + ".csv");
		newFlight.generateDotFile(input + ".dot");
		
	}
}
