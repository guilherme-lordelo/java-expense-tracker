package com.example.expensetracker;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExpenseManager {
	private final List<Expense> expenses = new ArrayList<>();

	public void addExpense(Expense expense) {
		expenses.add(expense);
	}

	public boolean deleteExpense(int index) {
		if (index >= 0 && index < expenses.size()) {
			expenses.remove(index);
			return true;
		}
		return false;
	}

	public List<Expense> getAllExpenses() {
		return new ArrayList<>(expenses);
	}

	public List<Expense> filterByCategory(String category) {
		return expenses.stream()
				.filter(e -> e.getCategory().equalsIgnoreCase(category))
				.collect(Collectors.toList());
	}

	public double getTotalByCategory(String category) {
		return filterByCategory(category).stream()
				.mapToDouble(Expense::getAmount)
				.sum();
	}
}
