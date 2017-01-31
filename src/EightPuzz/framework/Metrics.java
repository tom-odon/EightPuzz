package EightPuzz.framework;


import java.util.Hashtable;
import java.util.Set;
import java.util.TreeMap;

/*
 * Stores several data points about BFS in a Hashset.
 * This implementation borrowed from AIMA's github,
 * https://github.com/aimacode/aima-java/blob/AIMA3e/aima-core/
 * src/main/java/aima/core/search/framework/Metrics.java
 * @author Ravi Mohan
 * @author Ruediger Lunde
 */
public class Metrics {
	private Hashtable<String, String> records;
	
	//Constructor
	public Metrics(){
		records = new Hashtable<String, String>();
	}
	
	//Set an integer metric
	public void set(String name, int i) {
		records.put(name, Integer.toString(i));
	}

	//Set a double metric
	public void set(String name, double d) {
		records.put(name, Double.toString(d));
	}

	//Increment a given string metric.
	public void incrementInt(String name) {
		set(name, getInt(name) + 1);
	}

	//Set a long-number metric
	public void set(String name, long l) {
		records.put(name, Long.toString(l));
	}

	//Return an int metric given it's name.
	public int getInt(String name) {
		String value = records.get(name);
		return value != null ? Integer.parseInt(value) : 0;
	}

	//Return a double metric given it's name.
	public double getDouble(String name) {
		String value = records.get(name);
		return value != null ? Double.parseDouble(value) : Double.NaN;
	}

	//Return a long metric given its name.
	public long getLong(String name) {
		String value = records.get(name);
		return value != null ? Long.parseLong(value) : 0l;
	}

	//Get a given metric.
	public String get(String name) {
		return records.get(name);
	}

	//Return the names of all metrics.
	public Set<String> keySet() {
		return records.keySet();
	}

	/** Sorts the key-value pairs by key names and formats them as equations. */
	public String toString() {
		TreeMap<String, String> map = new TreeMap<String, String>(records);
		return map.toString();
	}
}
