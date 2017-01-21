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
	
	public Metrics(){
		records = new Hashtable<String, String>();
	}
	
	public void set(String name, int i) {
		records.put(name, Integer.toString(i));
	}

	public void set(String name, double d) {
		records.put(name, Double.toString(d));
	}

	public void incrementInt(String name) {
		set(name, getInt(name) + 1);
	}

	public void set(String name, long l) {
		records.put(name, Long.toString(l));
	}

	public int getInt(String name) {
		String value = records.get(name);
		return value != null ? Integer.parseInt(value) : 0;
	}

	public double getDouble(String name) {
		String value = records.get(name);
		return value != null ? Double.parseDouble(value) : Double.NaN;
	}

	public long getLong(String name) {
		String value = records.get(name);
		return value != null ? Long.parseLong(value) : 0l;
	}

	public String get(String name) {
		return records.get(name);
	}

	public Set<String> keySet() {
		return records.keySet();
	}

	/** Sorts the key-value pairs by key names and formats them as equations. */
	public String toString() {
		TreeMap<String, String> map = new TreeMap<String, String>(records);
		return map.toString();
	}
}
