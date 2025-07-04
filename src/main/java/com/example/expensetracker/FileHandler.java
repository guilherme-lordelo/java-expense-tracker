package com.example.expensetracker;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class FileHandler {

	private static final String CSV_SEPARATOR = ",";

	public static void saveToCSV(String filename, List<Expense> expenses) throws IOException {
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename))) {
			for (Expense e : expenses) {
				String line = String.join(CSV_SEPARATOR,
					e.getDate().toString(),
					String.valueOf(e.getAmount()),
					e.getCategory(),
					e.getDescription());
				writer.write(line);
				writer.newLine();
			}
		}
	}

	public static List<Expense> loadFromCSV(String filename) throws IOException {
		List<Expense> expenses = new ArrayList<>();

		try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(CSV_SEPARATOR, 4);
				if (parts.length == 4) {
					try {
						LocalDate date = LocalDate.parse(parts[0]);
						double amount = Double.parseDouble(parts[1]);
						String category = parts[2];
						String description = parts[3];
						expenses.add(new Expense(category, amount, date, description));
					} catch (Exception e) {
						// Skip malformed line
						System.err.println("Skipping malformed line: " + line);
					}
				}
			}

		}

		return expenses;
	}
}
