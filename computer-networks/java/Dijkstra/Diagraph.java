import java.awt.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/**
 * This is the user-interface class that allows the user to interact with the
 * program.
 * 
 * @author Emily Le
 * 
 */
class Diagraph<E> {
	// edges[i][j] is true if there is a vertex from i to j
	private boolean edges[][];

	// holds the weights of all of the edges
	private int[][] edgeWeights;

	// holds all of the distance weights
	private int[] distances;

	// labels[i] contains the label for vertex i
	private E[] labels;

	private String[] cityCodes;

	// contains the population of each of the cities
	private int[] populations;

	// contains elevation of each of the cities
	private int[] elevations;

	/**
	 * This is the constructor that will initialize all of the respective data
	 * structures: edges[][], edgeWeights[][], labels[], cityCodes[].
	 * 
	 * @param n
	 */
	public Diagraph(int n) {
		edges = new boolean[n][n]; // All values initially false
		edgeWeights = new int[n][n];
		labels = (E[]) new Object[n];
		cityCodes = new String[n];
	}

	/**
	 * This is a getter method to get the label of a vertex of this graph data
	 * structure.
	 * 
	 * @param vertex
	 * @return
	 */
	public E getLables(int vertex) {
		return labels[vertex];
	}

	/**
	 * This method will return a distance given a particular target.
	 * 
	 * @param target
	 * @return
	 */
	public int getDistance(int target) {
		return distances[target];
	}

	/**
	 * This is a setter method that will set the label of a vertex of this graph
	 * data structure.
	 * 
	 * @param vertex
	 * @param newLabel
	 */
	public void setLabel(int vertex, E newLabel) {
		labels[vertex] = newLabel;
	}

	/**
	 * This method will test whether an edge exists of not. This method will
	 * return true if the edge exists and false if the edge does not exist.
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	public boolean isEdge(int source, int target) {
		return edges[source][target];
	}

	/**
	 * This method will store the given data of a particular city. This data
	 * includes, the city code, name, population, and elevation.
	 * 
	 * @param cityCode
	 * @param cityName
	 * @param population
	 * @param elevation
	 */
	public void addCity(int index, String cityCode, String cityMisc) {
		cityCodes[index] = cityCode;
		labels[index] = (E) cityMisc;

	}

	/**
	 * This method will add a path between 2 points given a source, target, and
	 * a distance.
	 * 
	 * @param source
	 * @param target
	 * @param distance
	 */
	public void addRoad(int source, int target, int distance) {
		edges[source][target] = true;
		edgeWeights[source][target] = distance;
	}

	/**
	 * This method will remove an edge from the graph data structure, given a
	 * source and a target.
	 * 
	 * @param source
	 * @param target
	 */
	public void removeEdge(int source, int target) {
		edges[source][target] = false;
	}

	/**
	 * THis method will return a array of all the neighbors given a specific
	 * vertex
	 * 
	 * @param vertex
	 * @return
	 */
	public int[] neighbors(int vertex) {
		int i;
		int count = 0;
		int[] answer;

		for (i = 0; i < labels.length; i++) {
			if (edges[vertex][i])
				count++;
		}
		answer = new int[count];
		count = 0;
		for (i = 0; i < labels.length; i++) {
			if (edges[vertex][i])
				answer[count++] = i;
		}
		return answer;
	}

	/**
	 * This method will determine and return the number of vertexes in a graph.
	 * 
	 * @return
	 */
	public int size() {
		return labels.length;
	}

	/**
	 * This method will implement the Dijkstra algorithm to calculate the
	 * shortest distances from a given source. This method will return a
	 * previous[] which will hold the shortest distances from various targets.
	 * 
	 * @return
	 */
	public int[] shortestDistance(int source) {
		distances = new int[20];
		MinHeap priorityDistance = new MinHeap();
		int[] previous = new int[20];

		// initializing array indexes
		for (int i = 0; i < distances.length; i++) {
			distances[i] = -1;
		}
		distances[source] = 0;
		// add source into priority queue
		priorityDistance.addInsertionWay(distances[source], source);
		while (!priorityDistance.isEmpty()) {
			if (priorityDistance.peek() == -1) {
				return null;
			} else {
				// remove from priority queue
				int removedDistance = priorityDistance.remove();
				int distanceIndex = priorityDistance.getIndex();
				int[] neighbors = neighbors(distanceIndex);
				if (neighbors.length == 0 && distanceIndex == source) {
					return null;
				}
				for (int i = 0; i < neighbors.length; i++) {
					// relax distances
					if (distances[neighbors[i]] == -1
							|| distances[neighbors[i]] > distances[distanceIndex]
									+ edgeWeights[distanceIndex][neighbors[i]]) {
						distances[neighbors[i]] = distances[distanceIndex]
								+ edgeWeights[distanceIndex][neighbors[i]];
						// update arrays
						distances[distanceIndex] = removedDistance;
						previous[neighbors[i]] = distanceIndex;
						// add distances to heap
						priorityDistance.addInsertionWay(
								distances[neighbors[i]], neighbors[i]);
					}
				}
			}
		}
		return previous;
	}

	/**
	 * This is the binary search algorithm that will search through the tree and
	 * will return the index of the specified target. If the target does not
	 * exist, the method will return a -1.
	 * 
	 * @param target
	 * @return
	 */
	public int binarySearch(String target) {
		int first = 0;
		int last = cityCodes.length - 1;

		while (first <= last) {
			int mid = (last + first) / 2;
			if (cityCodes[mid].compareTo(target) == 0) {
				return mid;
			} else if (cityCodes[mid].compareTo(target) > 0) {
				last = mid - 1;
			} else {
				first = mid + 1;
			}
		}
		return -1;
	}

	/**
	 * This method will print out a single line containing a given city's
	 * information.
	 * 
	 * @param index
	 */
	public void printcityinfo(int index) {
		System.out.println(index + 1 + " " + cityCodes[index] + " "
				+ labels[index]);
	}

	/**
	 * This method will print out the previous[] which holds the shortest path
	 * to get from one city to another city.
	 * 
	 * @param previous
	 * @param source
	 * @param target
	 * @return
	 */
	public void printprevious(int[] previous, int source, int target) {
		Stack<String> previousStack = new Stack();
		System.out.print(cityCodes[source] + " ");
		while (target != source) {
			previousStack.push(cityCodes[target]);
			target = previous[target];
		}
		while (!previousStack.isEmpty()) {
			System.out.print(previousStack.pop() + " ");
		}
		System.out.println();
	}
}
