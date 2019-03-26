package edu.uwb.nemolib;


import java.io.*;
import java.util.*;

/**
 * The GraphParser class parses a text file into a Graph object. Each row of
 * input text file represents an edge in the graph. Each row should consist of
 * two integers separated by a single space, with each integer representing a
 * vertex. Vertices are created automatically based on the edge information.
 * Self edges and unconnected vertices are not allowed.
 */
public class GraphParser {

	// prevent instantiation of default constructor
	private GraphParser() {throw new AssertionError();}

	/**
	 * Parses a text file into a Graph object.
	 * @param filename the file containing the edge data
	 * @return a Graph object with the correct mapping
	 * @throws IOException if input file cannot be found
	 */
	public static Graph parse(String filename) throws IOException {
		return parse(new BufferedReader(new FileReader(filename)));
	}

	/**
	 * Parses an input stream into a Graph object.
	 * @param is input stream containing the edge data
	 * @return a Graph object with the correct mapping
	 * @throws IOException if an I/O error occurred while reading the input stream
	 */
	public static Graph parse(InputStream is) throws IOException {
		return parse(new BufferedReader(new InputStreamReader(is)));
	}

	/**
	 * Private helper method for reading an input stream into a Graph object.
	 * @param reader BufferedReader object containing the edge data
	 * @return a Graph object with the correct mapping
	 * @throws IOException if an I/O error occurred while reading the input stream
	 */
	private static Graph parse(BufferedReader reader) throws IOException {
		// we read in all the data at once only so we can easily randomize it
		// with Collections.shuffle()
		List<String> lines = new ArrayList<>();
		String currentLine = reader.readLine();
		while (currentLine != null) {
			lines.add(currentLine);
			currentLine = reader.readLine();
		}
		reader.close();

		return parse(lines);
	}

	public static Graph parse(List<String> lines) {
		if (lines.isEmpty()) {
			return null;
		}

		Map<String, Integer> nameToIndex = new HashMap<>();
		Graph output = new Graph();

		// avoid clustering (data collection bias) by randomly parsing the
		// input lines of data
		Collections.shuffle(lines);

		String delimiters = "\\s+"; // one or more whitespace characters
		for (String line:lines) {
			String[] edge = line.split(delimiters);
			if (edge.length != 2) {
				return null; // invalid graph format
			}
			int fromIndex = output.getOrCreateIndex(edge[0], nameToIndex);
			int toIndex   = output.getOrCreateIndex(edge[1], nameToIndex);

			// don't addSubgraph self edges
			if (fromIndex != toIndex) {
				output.getAdjacencyList(fromIndex).add(toIndex);
				output.getAdjacencyList(toIndex).add(fromIndex);
			}
		}
		return output;

	}
}
