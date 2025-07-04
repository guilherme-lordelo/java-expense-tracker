package com.example.expensetracker;

import org.junit.jupiter.api.*;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class FileHandlerTest {

	private static final String TEST_FILE = "test_expenses.csv";

	private final List<Expense> testData = List.of(
		new Expense("Food", 12.5, LocalDate.of(2024, 6, 1), "Lunch"),
		new Expense("Transport", 3.75, LocalDate.of(2024, 6, 2), "Bus fare")
	);

	@AfterEach
	public void cleanUp() throws IOException {
		Files.deleteIfExists(Path.of(TEST_FILE));
	}

	@Test
	public void testSaveAndLoadCSV() throws IOException {
		FileHandler.saveToCSV(TEST_FILE, testData);
		assertTrue(Files.exists(Path.of(TEST_FILE)));

		List<Expense> loaded = FileHandler.loadFromCSV(TEST_FILE);
		assertEquals(testData.size(), loaded.size());
		assertEquals(testData.get(0).toString(), loaded.get(0).toString());
		assertEquals(testData.get(1).toString(), loaded.get(1).toString());
	}

	@Test
	public void testLoadEmptyFile() throws IOException {
		Files.createFile(Path.of(TEST_FILE));
		List<Expense> loaded = FileHandler.loadFromCSV(TEST_FILE);
		assertTrue(loaded.isEmpty());
	}

	@Test
	public void testMalformedLineIgnored() throws IOException {
		List<String> lines = Arrays.asList(
			"2024-06-01,12.5,Food,Lunch",
			"malformed,line,missing,fields,extra"
		);
		Files.write(Path.of(TEST_FILE), lines);
		List<Expense> loaded = FileHandler.loadFromCSV(TEST_FILE);
		assertEquals(1, loaded.size());
		assertEquals("Lunch", loaded.get(0).getDescription());
	}
}
