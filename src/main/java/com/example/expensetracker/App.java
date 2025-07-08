package com.example.expensetracker;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class App {
	private static final Scanner scanner = new Scanner(System.in);
	private static final ExpenseManager manager = new ExpenseManager();
	private static final String CSV_FILE = "expenses.csv";

	public static void main(String[] args) {
		loadExpensesFromFile();

		while (true) {
			printMenu();
			String choice = scanner.nextLine().trim();

			switch (choice) {
				case "1" -> addExpense();
				case "2" -> listExpenses();
				case "3" -> deleteExpense();
				case "4" -> filterByCategory();
				case "5" -> showTotalByCategory();
				case "6" -> saveExpensesToFile();
				case "0" -> {
					System.out.println("Exiting. Goodbye!");
					return;
				}
				default -> System.out.println("Invalid option. Try again.");
			}
		}
	}

	private static void printMenu() {
		System.out.println("\n== Personal Expense Tracker ==");
		System.out.println("1. Add expense");
		System.out.println("2. List all expenses");
		System.out.println("3. Delete expense by index");
		System.out.println("4. Filter expenses by category");
		System.out.println("5. Show total by category");
		System.out.println("6. Save expenses to file");
		System.out.println("0. Exit");
		System.out.print("Choose an option: ");
	}

	private static void addExpense() {
		try {
			System.out.print("Category: ");
			String category = scanner.nextLine();

			System.out.print("Amount: ");
			double amount = Double.parseDouble(scanner.nextLine());

			System.out.print("Date (YYYY-MM-DD): ");
			LocalDate date = LocalDate.parse(scanner.nextLine());

			System.out.print("Description: ");
			String description = scanner.nextLine();

			Expense expense = new Expense(category, amount, date, description);
			manager.addExpense(expense);

			System.out.println("✅ Expense added.");
		} catch (NumberFormatException | DateTimeParseException e) {
			System.out.println("❌ Invalid input format.");
		}
	}

	private static void listExpenses() {
		List<Expense> expenses = manager.getAllExpenses();
		if (expenses.isEmpty()) {
			System.out.println("No expenses recorded.");
			return;
		}

		System.out.println("\n== Expenses ==");
		int i = 0;
		for (Expense e : expenses) {
			System.out.printf("[%d] %s%n", i++, e);
		}
	}

	private static void deleteExpense() {
		System.out.print("Enter index to delete: ");
		try {
			int index = Integer.parseInt(scanner.nextLine());
			boolean success = manager.deleteExpense(index);
			if (success) {
				System.out.println("✅ Expense deleted.");
			} else {
				System.out.println("❌ Invalid index.");
			}
		} catch (NumberFormatException e) {
			System.out.println("❌ Please enter a valid number.");
		}
	}

	private static void filterByCategory() {
		System.out.print("Enter category to filter: ");
		String category = scanner.nextLine();
		List<Expense> filtered = manager.filterByCategory(category);

		if (filtered.isEmpty()) {
			System.out.println("No expenses found in this category.");
		} else {
			System.out.println("\n== Filtered Expenses ==");
			for (Expense e : filtered) {
				System.out.println(e);
			}
		}
	}

	private static void showTotalByCategory() {
		System.out.print("Enter category: ");
		String category = scanner.nextLine();
		double total = manager.getTotalByCategory(category);
		System.out.printf("Total in '%s': %.2f%n", category, total);
	}

	private static void saveExpensesToFile() {
		try {
			FileHandler.saveToCSV(CSV_FILE, manager.getAllExpenses());
			System.out.println("Expenses saved to file.");
		} catch (IOException e) {
			System.out.println("Error saving to file: " + e.getMessage());
		}
	}

	private static void loadExpensesFromFile() {
		try {
			List<Expense> loaded = FileHandler.loadFromCSV(CSV_FILE);
			for (Expense e : loaded) {
				manager.addExpense(e);
			}
			if (!loaded.isEmpty()) {
				System.out.println("Loaded " + loaded.size() + " expenses from file.");
			}
		} catch (IOException e) {
			System.out.println("Could not load expenses: " + e.getMessage());
		}
	}
}
