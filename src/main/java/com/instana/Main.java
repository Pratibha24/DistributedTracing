package com.instana;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {

	static HashMap<Character, HashMap<Character, Integer>> nodes = new HashMap<Character, HashMap<Character, Integer>>();
	static Stack<Character> stack = new Stack<Character>();
	static LogManager logMgr = LogManager.getLogManager();
	static Logger log = logMgr.getLogger(Logger.GLOBAL_LOGGER_NAME);
	static int latency;
	static Character sourceNode;
	static int maxLatency;
	static int allRoutes;

	public static void main(String[] args) {
		// Read from file and create graph
		createGraph();

		// Executing test-1 to test-10
		if (nodes != null && nodes.size() > 0) {
			trace(Constant.PATH_1);
			trace(Constant.PATH_2);
			trace(Constant.PATH_3);
			trace(Constant.PATH_4);
			trace(Constant.PATH_5);
			getTraceWithMax3Hops('C', 'C');
			getTraceWith4Hops('A', 'C');
			findShortestTrace('A', 'C');
			findShortestTrace('B', 'B');
			allPathFromSourceToDest('C', 'C');
		}
	}

	protected static void createGraph() {
		String inputString = readInputFromFile();

		if (inputString.length() > 0) {

			String ipString[] = inputString.split(",");
			for (int i = 0; i < ipString.length; i++) {
				String ip = ipString[i].trim();

				Character node = ip.charAt(0);
				int weight = Integer.parseInt(String.valueOf(ip.charAt(2)));
				if (nodes.containsKey(node)) {
					HashMap<Character, Integer> edge = (HashMap<Character, Integer>) nodes.get(node);
					edge.put(ip.charAt(1), weight);
				} else {
					HashMap<Character, Integer> edges = new HashMap<Character, Integer>();
					edges.put(ip.charAt(1), weight);
					nodes.put(node, edges);
				}
			}
		}
	}

	private static String readInputFromFile() {
		Scanner sc = null;
		String inputString = "";
		URL resource = Main.class.getClassLoader().getResource(Constant.FILE_NAME);

		if (resource == null) {
			throw new IllegalArgumentException(Constant.FILE_MISSING_MSG);
		}
		File file = new File(resource.getFile());

		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			log.log(Level.SEVERE, Constant.FILE_MISSING_MSG, e);
		}

		if (sc.hasNextLine())
			inputString = sc.nextLine();
		else
			log.log(Level.WARNING, Constant.NO_INPUT_PRESENT_MSG);

		return inputString;
	}

	protected static int trace(Character[] traceNodes) {
		int latency = 0;
		for (int i = 0; i < traceNodes.length - 1; i++) {
			HashMap<Character, Integer> edges = (HashMap<Character, Integer>) nodes.get(traceNodes[i]);
			if (edges.containsKey(traceNodes[i + 1])) {
				latency += edges.get(traceNodes[i + 1]);
			} else {
				System.out.println(Constant.NO_SUCH_TRACE_MSG);
				return 0;
			}
		}
		System.out.println(latency);
		return latency;
	}

	protected static int getTraceWithMax3Hops(char source, char dest) {
		int traces = 0;
		Queue<Character> q = new LinkedList<>();

		HashMap<Character, Integer> edges = (HashMap<Character, Integer>) nodes.get(source);
		// dump to Q
		for (Map.Entry<Character, Integer> edge : edges.entrySet()) {
			q.add(edge.getKey());
		}
		int hops = 1;
		while (hops <= 3) {
			int qLength = q.size();
			for (int i = 0; i < qLength; i++) {
				Character node = q.remove();
				if (node == dest) {
					traces++;
				}

				HashMap<Character, Integer> edgeList = (HashMap<Character, Integer>) nodes.get(node);
				for (Map.Entry<Character, Integer> edge : edgeList.entrySet()) {
					q.add(edge.getKey());
				}
			}
			hops++;
		}
		// print number of traces
		System.out.println(traces);
		return traces;
	}

	protected static int getTraceWith4Hops(char source, char dest) {
		int traces = 0;
		Queue<Character> q = new LinkedList<>();

		HashMap<Character, Integer> edges = (HashMap<Character, Integer>) nodes.get(source);
		// dump to Q
		for (Map.Entry<Character, Integer> edge : edges.entrySet()) {
			q.add(edge.getKey());
		}
		int hops = 1;
		while (hops <= 4) {
			int qLength = q.size();
			for (int i = 0; i < qLength; i++) {
				Character node = q.remove();

				HashMap<Character, Integer> edgeList = (HashMap<Character, Integer>) nodes.get(node);
				for (Map.Entry<Character, Integer> edge : edgeList.entrySet()) {
					q.add(edge.getKey());
				}
			}
			hops++;
		}

		while (!q.isEmpty()) {
			Character tNode = q.remove();
			if (tNode == dest) {
				traces++;
			}
		}
		// print number of traces
		System.out.println(traces);
		return traces;
	}

	protected static int findShortestTrace(char source, char dest) {
		sourceNode = source;
		latency = Integer.MAX_VALUE;
		stack.push(sourceNode);
		shortestPath(dest, nodes, 0);

		if (latency == Integer.MAX_VALUE)
			log.log(Level.INFO, Constant.NO_TRACE_FOUND_MSG);
		else
			System.out.println(latency);

		return latency;
	}

	protected static int allPathFromSourceToDest(char source, char dest) {
		maxLatency = 30;
		allRoutes = 0;
		stack.push(source);
		findAllPaths(dest, nodes, 0);
		System.out.println(allRoutes);
		return allRoutes;
	}

	private static void shortestPath(Character dest, HashMap<Character, HashMap<Character, Integer>> nodes, int lat) {
		while (!stack.isEmpty()) {
			Character node = stack.peek();

			HashMap<Character, Integer> edges = (HashMap<Character, Integer>) nodes.get(node);
			for (Map.Entry<Character, Integer> edge : edges.entrySet()) {
				if (!stack.contains(edge.getKey()) || edge.getKey() == sourceNode) {
					if (edge.getKey() == dest) {
						if (lat + edge.getValue() < latency)
							latency = lat + edge.getValue();
					} else {
						stack.push(edge.getKey());
						shortestPath(dest, nodes, lat + edge.getValue());
					}
				}
			}
			stack.pop();
			return;
		}
	}

	private static void findAllPaths(Character dest, HashMap<Character, HashMap<Character, Integer>> nodes, int lat) {
		if (lat > maxLatency) {
			stack.pop();
			return;
		}

		while (!stack.isEmpty()) {
			Character node = stack.peek();

			HashMap<Character, Integer> edges = (HashMap<Character, Integer>) nodes.get(node);
			for (Map.Entry<Character, Integer> edge : edges.entrySet()) {
				if (edge.getKey() == dest && lat + edge.getValue() < maxLatency)
					allRoutes++;
				stack.push(edge.getKey());
				findAllPaths(dest, nodes, lat + edge.getValue());

			}
			stack.pop();
			return;
		}
	}
}
