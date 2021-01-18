package com.instana;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MainTest {
	
	@BeforeAll
	public void beforeAllInit() {
		System.out.println("Running the tests");
	}
	
	@BeforeEach
	void initGraph() {
		Main.createGraph();
	}
	
	@Test
	void testTrace() {
		assertAll(() -> assertEquals(9, Main.trace(Constant.PATH_1), "Number of trace:"),
				() -> assertEquals(5, Main.trace(Constant.PATH_2), "Number of trace:"),
				() -> assertEquals(13, Main.trace(Constant.PATH_3), "Number of trace:"),
				() -> assertEquals(22, Main.trace(Constant.PATH_4), "Number of trace:"),
				() -> assertEquals(0, Main.trace(Constant.PATH_5), "Number of trace:"));
	}
	
	@Test
	void testGetTraceWithMax3Hops() {
		int actual = Main.getTraceWithMax3Hops('C', 'C');
		assertEquals(2, actual, "Number of trace from C to C with max 3 hops:");
	}

	@Test
	void testGetTraceWith4Hops() {
		int actual = Main.getTraceWith4Hops('A', 'C');
		assertEquals(3, actual, "Number of trace from A to C with exactly 4 hops:");
	}
	
	@Test
	void testFindShortestTrace() {
		assertAll(() -> assertEquals(9, Main.findShortestTrace('A', 'C'), "Number of shortest trace from A to C:"),
				() -> assertEquals(9, Main.findShortestTrace('B', 'B'), "Number of shortest trace from A to C:"));
	}
	
	@Test
	void testAllPathFromSourceToDest() {
		int actual = Main.allPathFromSourceToDest('C', 'C');
		assertEquals(7, actual, "Number of all trace from C to C:");
	}
	
	@AfterAll
	void testAfterAll() {
		System.out.println("Completed all tests.");
	}
	
}
